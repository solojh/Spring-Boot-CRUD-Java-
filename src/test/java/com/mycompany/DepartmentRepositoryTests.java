package com.mycompany;

import com.mycompany.department.Department;
import com.mycompany.department.DepartmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class DepartmentRepositoryTests {
    @Autowired private DepartmentRepository repo;

    @Test
    public void testAddNew() {
        Department department = new Department();
        department.setEmail("jiehao@gmail.com");
        department.setPassword("jie123456");
        department.setFirstName("hao");
        department.setLastName("Stevenson");

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
        Integer departmentId = 1;
        Optional<Department> optionaldepartment = repo.findById(departmentId);
        Department department = optionaldepartment.get();
        department.setPassword("hello2000");
        repo.save(department);

        Department updatedDepartment = repo.findById(departmentId).get();
        Assertions.assertThat(updatedDepartment.getPassword()).isEqualTo("hello2000");
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