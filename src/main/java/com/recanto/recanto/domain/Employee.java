package com.recanto.recanto.domain;

import com.recanto.recanto.domain.dtos.EmployeeDTO;
import com.recanto.recanto.enums.Profile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.stream.Collectors;

@EqualsAndHashCode
@Entity
@Getter
@Setter
public class Employee extends Person{


    public Employee(Integer id, String name, String cpf, String email, String password) {
        super(id, name, cpf, email, password);
        addProfiles(Profile.EMPLOYEE);
    }

    public Employee(EmployeeDTO obj) {
        this.id = obj.getId();
        this.name = obj.getName();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.password= obj.getPassword();
        this.profiles = obj.getProfiles().stream().map(Profile::getCode).collect(Collectors.toSet());
        this.dateCriation = obj.getDateCriation();
        addProfiles(Profile.EMPLOYEE);
    }

    public Employee() {
        super();
        addProfiles(Profile.EMPLOYEE);
    }

}
