package com.mycompany;

import com.mycompany.department.Department;
import com.mycompany.department.DepartmentRepository;
import com.mycompany.employee.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class DepartmentRepositoryTests {
    @Autowired private DepartmentRepository repo;

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
        Iterable<Department> departments = repo.findAll();
        Assertions.assertThat(departments).hasSizeGreaterThan(0);

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
        Integer departmentId = 1;
        Optional<Department> optionalDepartment= repo.findById(departmentId);
        Assertions.assertThat(optionalDepartment).isPresent();
        System.out.println(optionalDepartment.get());
    }

    @Test
    public void testDelete() {
        Integer departmentId = 2;
        repo.deleteById(departmentId);

        Optional<Department> optionalDepartment = repo.findById(departmentId);
        Assertions.assertThat(optionalDepartment).isNotPresent();
    }
}