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


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class DepartmentRepositoryTests {
    @Autowired
    private DepartmentRepository repo;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testAddNew() {
        Department department = new Department();
        department.setDepartmentName("HR");
        department.setNumberofEmployees("3");

        Department savedDepartment = repo.save(department);

        Assertions.assertThat(savedDepartment).isNotNull();
        Assertions.assertThat(savedDepartment.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        for (int i = 0; i < 3; i++) {
            Department department = new Department();
            department.setDepartmentName("hr");
            department.setNumberofEmployees("3");
            repo.save(department);
        }

        Iterable<Department> departments = repo.findAll();
        Assertions.assertThat(departments).hasSize(3);

        for (Department department : departments) {
            System.out.println(department);
        }
    }

    @Test
    public void testUpdate() {
        Department department = new Department();
        department.setDepartmentName("hr");
        department.setNumberofEmployees("3");
        Department savedDepartment = repo.save(department);

        Optional<Department> optionaldepartment = repo.findById(savedDepartment.getId());
        Department dd = optionaldepartment.get();

        dd.setDepartmentName("Human resource");
        repo.save(dd);

        Department updatedDepartment = repo.findById(savedDepartment.getId()).get();
        Assertions.assertThat(updatedDepartment.getDepartmentName()).isEqualTo("Human resource");
    }

    @Test
    public void testGet() {
        Department department = new Department();
        department.setDepartmentName("hr");
        department.setNumberofEmployees("3");
        Department savedDepartment = repo.save(department);

        Optional<Department> optionalDepartment= repo.findById(savedDepartment.getId());
        Assertions.assertThat(optionalDepartment).isPresent();
        System.out.println(optionalDepartment.get());
    }

    @Test
    public void testDelete() {
        Department department = new Department();
        department.setDepartmentName("hr");
        department.setNumberofEmployees("3");
        Department savedDepartment = repo.save(department);

        repo.deleteById(savedDepartment.getId());

        Optional<Department> optionalDepartment = repo.findById(savedDepartment.getId());
        Assertions.assertThat(optionalDepartment).isNotPresent();
    }
    @Test
    public void testDepartmentNoEmployee() {
        Department department = new Department();
        department.setDepartmentName("hr");
        department.setNumberofEmployees("3");

        Department savedDepartment = repo.save(department);

        Employee employee = new Employee();
        employee.setName("ABC");
        employee.setPosition("manager");
        employee.setEmail("ABC@gmail.com");
        Employee savedEmployee = employeeRepository.save(employee);

        //employess= []
        List<Employee> employess= new ArrayList<>();
        //employee[0]=employee
        employess.add(employee);
        savedDepartment.setEmployee(employess);
        repo.save(savedDepartment);
        //Act
        savedDepartment.setEmployee(null);
        repo.save(department);
        //Assert
        Optional<Department> updatedDepartment = repo.findById(savedDepartment.getId());
        Assertions.assertThat(updatedDepartment).isPresent();
        Assertions.assertThat(updatedDepartment.get().getEmployee()).isNull();

    }

    @Test
    public void testAddNewEmployee() {
// Arrange
        Department department = new Department();
        department.setDepartmentName("hr");
        department.setNumberofEmployees("3");

        Department savedDepartment = repo.save(department);

        // Create a new employee
        Employee employee = new Employee();
        employee.setName("XYZ");
        employee.setPosition("staff");
        employee.setEmail("XYZ@gmail.com");

        // Act
        Employee savedEmployee = employeeRepository.save(employee);

        // Add the new employee to the department
        List<Employee> employees = new ArrayList<>();
        employees.add(savedEmployee);
        savedDepartment.setEmployee(employees);
        repo.save(savedDepartment);

        // Assert
        Optional<Department> updatedDepartment = repo.findById(savedDepartment.getId());
        Assertions.assertThat(updatedDepartment).isPresent();
        Assertions.assertThat(updatedDepartment.get().getEmployee()).isNotEmpty();
        Assertions.assertThat(updatedDepartment.get().getEmployee().get(0)).isEqualTo(savedEmployee);
    }
    @Test
    public void testDeleteEmployee() {
        // Arrange
        Department department = new Department();
        department.setDepartmentName("hr");
        department.setNumberofEmployees("3");

        Department savedDepartment = repo.save(department);

        // Create and save an employee
        Employee employee = new Employee();
        employee.setName("XYZ");
        employee.setPosition("staff");
        employee.setEmail("XYZ@gmail.com");
        Employee savedEmployee = employeeRepository.save(employee);

        // Add the employee to the department
        List<Employee> employees = new ArrayList<>();
        employees.add(savedEmployee);
        savedDepartment.setEmployee(employees);
        repo.save(savedDepartment);

        // Act: Remove the employee from the department
        savedDepartment.setEmployee(new ArrayList<>());
        repo.save(savedDepartment);

        // Assert: Verify that the employee has been removed from the department
        Optional<Department> updatedDepartment = repo.findById(savedDepartment.getId());
        Assertions.assertThat(updatedDepartment).isPresent();
        Assertions.assertThat(updatedDepartment.get().getEmployee()).isEmpty();
    }
    @Test
    public void test2Employeeto1Employee() {
        // Arrange: Create and save a department
        Department department = new Department();
        department.setDepartmentName("it");
        department.setNumberofEmployees("3");
        Department savedDepartment = repo.save(department);

        // Create and save two employees
        Employee employee1 = new Employee();
        employee1.setName("Employee1");
        employee1.setPosition("Staff");
        employee1.setEmail("employee1@gmail.com");
        Employee savedEmployee1 = employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Employee2");
        employee2.setPosition("Staff");
        employee2.setEmail("employee2@gmail.com");
        Employee savedEmployee2 = employeeRepository.save(employee2);

        // Add both employees to the department
        List<Employee> employees = new ArrayList<>();
        employees.add(savedEmployee1);
        employees.add(savedEmployee2);
        savedDepartment.setEmployee(employees);
        repo.save(savedDepartment);

        // Act: Remove one employee from the department
        savedDepartment.getEmployee().remove(savedEmployee2);
        repo.save(savedDepartment);

        // Assert: Verify that the department now has only one employee
        Optional<Department> updatedDepartment = repo.findById(savedDepartment.getId());
        Assertions.assertThat(updatedDepartment).isPresent();
        List<Employee> updatedEmployees = updatedDepartment.get().getEmployee();
        Assertions.assertThat(updatedEmployees.size()).isEqualTo(1);
        Assertions.assertThat(updatedEmployees.get(0)).isEqualTo(savedEmployee1);
    }

}