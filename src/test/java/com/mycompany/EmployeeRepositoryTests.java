package com.mycompany;

import com.mycompany.employee.Employee;
import com.mycompany.employee.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository repo;

    @Test
    public void testAddNew() {
        Employee employee = new Employee();
        employee.setName("ravi");
        employee.setPosition("manager");
        employee.setEmail("ravi@gmail.com");
        Employee savedEmployee = repo.save(employee);

        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        //Arrange
        for (int i = 0; i < 3; i++) {
            Employee employee = new Employee();
            employee.setName("NJH : " + i);
            employee.setPosition("staff");
            employee.setEmail("NJH"+i+"@gmail.com");
            repo.save(employee);
        }
        Iterable<Employee> employees = repo.findAll();
        Assertions.assertThat(employees).hasSize(3);
    }

    @Test
    public void testUpdate() {
        //Arrange
        Employee employee = new Employee();
        employee.setName("ABC");
        employee.setPosition("manager");
        employee.setEmail("ABC@gmail.com");
        Employee savedEmployee = repo.save(employee);

        Optional<Employee> optionalEmployee = repo.findById(savedEmployee.getId());
        Employee ee = optionalEmployee.get();

        ee.setName("CCK");
        repo.save(ee);

        Employee updatedemployee = repo.findById(savedEmployee.getId()).get();
        Assertions.assertThat(updatedemployee.getName()).isEqualTo("CCK");
    }

    @Test
    public void testGet() {
        //Arrange
        Employee employee = new Employee();
        employee.setName("ABC");
        employee.setPosition("manager");
        employee.setEmail("ABC@gmail.com");

        Employee savedEmployee = repo.save(employee);
        //Act
        Optional<Employee> optionalEmployee = repo.findById(savedEmployee.getId());
        Assertions.assertThat(optionalEmployee).isPresent();
    }

    @Test
    public void testDelete() {
        Integer employeeId = 2;
        repo.deleteById(employeeId);

        Optional<Employee> optionalEmployee = repo.findById(employeeId);
        Assertions.assertThat(optionalEmployee).isNotPresent();
    }
}