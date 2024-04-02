package com.mycompany;

import com.mycompany.employee.Employee;
import com.mycompany.employee.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTests {
    @Autowired private EmployeeRepository repo;

    @Test
    public void testAddNew() {
        Employee employee = new Employee();
        employee.setEmail("ravi@gmail.com");
        employee.setPassword("ravi123456");
        employee.setFirstName("ravi");
        employee.setLastName("kumar");

        Employee savedEmployee = repo.save(employee);

        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<Employee> employees = repo.findAll();
        Assertions.assertThat(employees).hasSizeGreaterThan(0);

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    @Test
    public void testUpdate() {
        Integer employeeId = 1;
        Optional<Employee> optionalEmployee = repo.findById(employeeId);
        Employee employee = optionalEmployee.get();
        employee.setPassword("hello2000");
        repo.save(employee);

        Employee updatedemployee = repo.findById(employeeId).get();
        Assertions.assertThat(updatedemployee.getPassword()).isEqualTo("hello2000");
    }

    @Test
    public void testGet() {
        Integer employeeId = 1;
        Optional<Employee> optionalEmployee = repo.findById(employeeId);
        Assertions.assertThat(optionalEmployee).isPresent();
        System.out.println(optionalEmployee.get());
    }

    @Test
    public void testDelete() {
        Integer employeeId = 2;
        repo.deleteById(employeeId);

        Optional<Employee> optionalemployee = repo.findById(employeeId);
        Assertions.assertThat(optionalemployee).isNotPresent();
    }
}