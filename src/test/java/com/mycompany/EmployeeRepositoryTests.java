package com.mycompany;

import com.mycompany.department.Department;
import com.mycompany.department.DepartmentRepository;
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
    @Autowired
    private DepartmentRepository departmentRepository;

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
            employee.setEmail("NJH" + i + "@gmail.com");
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

    @Test
    public void testAddWithDepartment() {
        //Arrange
        Employee employee = new Employee();
        employee.setName("ABC");
        employee.setPosition("manager");
        employee.setEmail("ABC@gmail.com");

        Employee savedEmployee = repo.save(employee);

        Department department = new Department();
        department.setDepartmentName("hr");
        department.setNumberofEmployees("3");
        Department savedDepartment = departmentRepository.save(department);
        //Act
        savedEmployee.setDepartment(savedDepartment);
        repo.save(savedEmployee);

        //Assert
        Optional<Employee> optionalEmployee = repo.findById(savedEmployee.getId());
        Assertions.assertThat(optionalEmployee).isPresent();
        Assertions.assertThat(optionalEmployee.get().getDepartment()).isNotNull();
    }


    @Test
    public void testEmployeeCanChangeDepartment() {
        //Arrange
        Employee employee = new Employee();
        employee.setName("ABC");
        employee.setPosition("manager");
        employee.setEmail("ABC@gmail.com");

        Employee savedEmployee = repo.save(employee);

        Department department = new Department();
        department.setDepartmentName("hr");
        department.setNumberofEmployees("3");
        Department oldDepartment = departmentRepository.save(department);
        savedEmployee.setDepartment(oldDepartment);
        repo.save(savedEmployee);
        //Act
        Department newDepartment = new Department();
        newDepartment.setDepartmentName("it");
        newDepartment.setNumberofEmployees("3");
        Department newSavedDepartment = departmentRepository.save(newDepartment);
        savedEmployee.setDepartment(newSavedDepartment);
        repo.save(savedEmployee);

        //Assert
        Optional<Employee> updatedEmployee = repo.findById(savedEmployee.getId());
        Assertions.assertThat(updatedEmployee).isPresent();
        Assertions.assertThat(updatedEmployee.get().getDepartment()).isNotNull();
        Assertions.assertThat(updatedEmployee.get().getDepartment().getDepartmentName()).isEqualTo("it");

    }

    @Test
    public void testEmployeeCanBeNoDepartment() {
        //Arrange
        Employee employee = new Employee();
        employee.setName("ABC");
        employee.setPosition("manager");
        employee.setEmail("ABC@gmail.com");

        Employee savedEmployee = repo.save(employee);

        Department department = new Department();
        department.setDepartmentName("hr");
        department.setNumberofEmployees("3");
        Department savedDepartment = departmentRepository.save(department);
        savedEmployee.setDepartment(savedDepartment);
        repo.save(savedEmployee);
        //Act
        savedEmployee.setDepartment(null);
        repo.save(employee);

        //Assert
        Optional<Employee> updatedEmployee = repo.findById(savedEmployee.getId());
        Assertions.assertThat(updatedEmployee).isPresent();
        Assertions.assertThat(updatedEmployee.get().getDepartment()).isNull();
    }
}