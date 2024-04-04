package com.mycompany;

import com.mycompany.department.Department;
import com.mycompany.department.DepartmentRepository;
import com.mycompany.employee.Employee;
import com.mycompany.employee.EmployeeRepository;
import com.mycompany.project.Project;
import com.mycompany.project.ProjectRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testAddNew() {
        Employee employee = new Employee();
        employee.setName("ravi");
        employee.setPosition("manager");
        employee.setEmail("ravi@gmail.com");
        Employee savedEmployee = employeeRepository.save(employee);

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
            employeeRepository.save(employee);
        }
        Iterable<Employee> employees = employeeRepository.findAll();
        Assertions.assertThat(employees).hasSize(3);
    }

    @Test
    public void testUpdate() {
        //Arrange
        Employee employee = new Employee();
        employee.setName("ABC");
        employee.setPosition("manager");
        employee.setEmail("ABC@gmail.com");
        Employee savedEmployee = employeeRepository.save(employee);

        Optional<Employee> optionalEmployee = employeeRepository.findById(savedEmployee.getId());
        Employee ee = optionalEmployee.get();

        ee.setName("CCK");
        employeeRepository.save(ee);

        Employee updatedemployee = employeeRepository.findById(savedEmployee.getId()).get();
        Assertions.assertThat(updatedemployee.getName()).isEqualTo("CCK");
    }

    @Test
    public void testGet() {
        //Arrange
        Employee employee = new Employee();
        employee.setName("ABC");
        employee.setPosition("manager");
        employee.setEmail("ABC@gmail.com");

        Employee savedEmployee = employeeRepository.save(employee);
        //Act
        Optional<Employee> optionalEmployee = employeeRepository.findById(savedEmployee.getId());
        Assertions.assertThat(optionalEmployee).isPresent();
    }

    @Test
    public void testDelete() {
        Integer employeeId = 2;
        employeeRepository.deleteById(employeeId);

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Assertions.assertThat(optionalEmployee).isNotPresent();
    }

    @Test
    public void testAddWithDepartment() {
        //Arrange
        Employee employee = new Employee();
        employee.setName("ABC");
        employee.setPosition("manager");
        employee.setEmail("ABC@gmail.com");

        Employee savedEmployee = employeeRepository.save(employee);

        Department department = new Department();
        department.setDepartmentName("hr");
        Department savedDepartment = departmentRepository.save(department);
        //Act
        savedEmployee.setDepartment(savedDepartment);
        employeeRepository.save(savedEmployee);

        //Assert
        Optional<Employee> optionalEmployee = employeeRepository.findById(savedEmployee.getId());
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

        Employee savedEmployee = employeeRepository.save(employee);

        Department department = new Department();
        department.setDepartmentName("hr");
        Department oldDepartment = departmentRepository.save(department);
        savedEmployee.setDepartment(oldDepartment);
        employeeRepository.save(savedEmployee);
        //Act
        Department newDepartment = new Department();
        newDepartment.setDepartmentName("it");
        Department newSavedDepartment = departmentRepository.save(newDepartment);
        savedEmployee.setDepartment(newSavedDepartment);
        employeeRepository.save(savedEmployee);

        //Assert
        Optional<Employee> updatedEmployee = employeeRepository.findById(savedEmployee.getId());
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

        Employee savedEmployee = employeeRepository.save(employee);

        Department department = new Department();
        department.setDepartmentName("hr");
        Department savedDepartment = departmentRepository.save(department);
        savedEmployee.setDepartment(savedDepartment);
        employeeRepository.save(savedEmployee);
        //Act
        savedEmployee.setDepartment(null);
        employeeRepository.save(employee);

        //Assert
        Optional<Employee> updatedEmployee = employeeRepository.findById(savedEmployee.getId());
        Assertions.assertThat(updatedEmployee).isPresent();
        Assertions.assertThat(updatedEmployee.get().getDepartment()).isNull();
    }
}