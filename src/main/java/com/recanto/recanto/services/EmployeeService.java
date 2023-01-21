package com.recanto.recanto.services;

import com.recanto.recanto.domain.Employee;
import com.recanto.recanto.domain.Person;
import com.recanto.recanto.domain.dtos.EmployeeDTO;
import com.recanto.recanto.repository.EmployeeRepository;
import com.recanto.recanto.repository.PersonRepository;
import com.recanto.recanto.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private PersonRepository personRepository;

    public Employee findById(Integer id) {
        Optional<Employee> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Obeject not found: " + id));
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee create(EmployeeDTO obj) {
        obj.setId(null);
        validateByCpfAndEmail(obj);
        Employee newEmployee = new Employee(obj);
        return repository.save(newEmployee);
    }

    public Employee update(Integer id, EmployeeDTO objDto) {
        objDto.setId(id);
        Employee oldObj = findById(id);
        validateByCpfAndEmail(objDto);
        oldObj = new Employee(objDto);
        return repository.save(oldObj);

    }


    public void delete(Integer id) {
        Employee obj = findById(id);
        if (
                obj.getProviders().size() > 0 ||
                obj.getAnnoucements().size() > 0 ||
                obj.getOccurrences().size() > 0 ||
                obj.getReservations().size() > 0
        ) {
            throw new DataIntegrityViolationException("Employee cannot be deleted because there are schedule for him");
        }
        repository.deleteById(id);
    }

    private void validateByCpfAndEmail(EmployeeDTO objDto) {
        Optional<Person> obj = personRepository.findByCpf(objDto.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
            throw new DataIntegrityViolationException("Cpf Already Registered");
        }
        obj = personRepository.findByEmail(objDto.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
            throw new DataIntegrityViolationException("Email Already Registered");
        }
    }


}
