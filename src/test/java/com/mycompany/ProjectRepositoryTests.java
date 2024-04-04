package com.mycompany;

import com.mycompany.employee.Employee;
import com.mycompany.employee.EmployeeRepository;
import com.mycompany.project.ProjectRepository;
import com.mycompany.project.Project;
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
public class ProjectRepositoryTests {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testAddNew() {
        Project project = new Project();
        project.setProjectName("QMS");

        Project savedProject = projectRepository.save(project);

        Assertions.assertThat(savedProject).isNotNull();
        Assertions.assertThat(savedProject.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        //Arrange
        for (int i = 0; i < 3; i++) {
            Project project = new Project();
            project.setProjectName("QMS : " + i);

            projectRepository.save(project);
        }
        //Act
        Iterable<Project> projects = projectRepository.findAll();

        //Assertion
        Assertions.assertThat(projects).hasSize(3);
    }

    @Test
    public void testUpdate() {
        //Arrange
        Project project = new Project();
        project.setProjectName("QMS for testing");

        Project savedProject = projectRepository.save(project);

        //Act
        Optional<Project> optionalUser = projectRepository.findById(savedProject.getId());
        Project pp= optionalUser.get();

        pp.setProjectName("new QMS");
        projectRepository.save(pp);

        //Assertion
        Project updatedUser = projectRepository.findById(savedProject.getId()).get();
        Assertions.assertThat(updatedUser.getProjectName()).isEqualTo("new QMS");
    }

    @Test
    public void testGet() {
        //Arrange
        Project project = new Project();
        project.setProjectName("QMS for testing");

        Project savedProject = projectRepository.save(project);

        //Act
        Optional<Project> optionalproject = projectRepository.findById(savedProject.getId());

        //Assertion
        Assertions.assertThat(optionalproject).isPresent();
    }

    @Test
    public void testDelete() {
        //Arrange
        Project project = new Project();
        project.setProjectName("QMS for testing");

        Project savedProject = projectRepository.save(project);

        //Act
        projectRepository.deleteById(savedProject.getId());

        //Assertion
        Optional<Project> optionalUser = projectRepository.findById(savedProject.getId());
        Assertions.assertThat(optionalUser).isNotPresent();
    }
    @Test
    public void testProjectNoEmployee() {
        // Arrange
        Project project = new Project();
        project.setProjectName("Project X");

        // Act
        Project savedProject = projectRepository.save(project);

        // Assert
        Optional<Project> retrievedProject = projectRepository.findById(savedProject.getId());
        Assertions.assertThat(retrievedProject).isPresent();
        Assertions.assertThat(retrievedProject.get().getEmployees()).isEmpty();
    }
    @Test
    public void testAddWithEmployee() {
        //Arrange
        Employee employee = new Employee();
        employee.setName("ABC");
        employee.setPosition("manager");
        employee.setEmail("ABCasdasdq@gmail.com");

        Employee savedEmployee = employeeRepository.save(employee);

        Project project = new Project();
        project.setProjectName("EMS");
        Project savedProject = projectRepository.save(project);
        //Act

        //Add Employee to project
        List<Employee> employees = new ArrayList<>();
        employees.add(savedEmployee);
        savedProject.setEmployees(employees);
        projectRepository.save(savedProject);
        //Assert

        Optional<Project> newProject = projectRepository.findById(savedProject.getId());
        Assertions.assertThat(newProject.get().getEmployees()).isNotNull();
        Assertions.assertThat(newProject.get().getEmployees().get(0)).isEqualTo(savedEmployee);
    }
    @Test
    public void testDeleteEmployee() {
        // Arrange
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setPosition("developer");
        employee.setEmail("johndoe@example.com");
        Employee savedEmployee = employeeRepository.save(employee);

        Project project = new Project();
        project.setProjectName("Project X");
        Project savedProject = projectRepository.save(project);

        // Add Employee to project
        List<Employee> employees = new ArrayList<>();
        employees.add(savedEmployee);
        savedProject.setEmployees(employees);
        projectRepository.save(savedProject);

        // Act: Remove the employee from the project
        savedProject.getEmployees().remove(savedEmployee);
        projectRepository.save(savedProject);

        // Delete the employee
        employeeRepository.delete(savedEmployee);

        // Assert
        Optional<Project> updatedProject = projectRepository.findById(savedProject.getId());
        Assertions.assertThat(updatedProject.get().getEmployees()).isEmpty();
    }
    @Test
    public void test2Employeeto1Employee() {
        // Arrange
        Employee employee1 = new Employee();
        employee1.setName("Alice");
        employee1.setPosition("designer");
        employee1.setEmail("alice@example.com");
        Employee savedEmployee1 = employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setName("Bob");
        employee2.setPosition("developer");
        employee2.setEmail("bob@example.com");
        Employee savedEmployee2 = employeeRepository.save(employee2);

        Project project = new Project();
        project.setProjectName("Project Y");
        Project savedProject = projectRepository.save(project);

        // Add Employees to project
        List<Employee> employees = new ArrayList<>();
        employees.add(savedEmployee1);
        employees.add(savedEmployee2);
        savedProject.setEmployees(employees);
        projectRepository.save(savedProject);

        // Act: Change to only one employee
        List<Employee> newEmployees = new ArrayList<>();
        newEmployees.add(savedEmployee1);
        savedProject.setEmployees(newEmployees);
        projectRepository.save(savedProject);

        // Assert
        Optional<Project> updatedProject = projectRepository.findById(savedProject.getId());
        Assertions.assertThat(updatedProject.get().getEmployees()).containsExactly(savedEmployee1);
    }

}