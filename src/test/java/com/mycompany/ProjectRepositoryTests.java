package com.mycompany;

import com.mycompany.project.ProjectRepository;
import com.mycompany.project.Project;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectRepositoryTests {
    @Autowired
    private ProjectRepository repo;

    @Test
    public void testAddNew() {
        Project project = new Project();
        project.setProjectName("QMS");

        Project savedProject = repo.save(project);

        Assertions.assertThat(savedProject).isNotNull();
        Assertions.assertThat(savedProject.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        //Arrange
        for (int i = 0; i < 3; i++) {
            Project project = new Project();
            project.setProjectName("QMS : " + i);

            repo.save(project);
        }
        //Act
        Iterable<Project> projects = repo.findAll();

        //Assertion
        Assertions.assertThat(projects).hasSize(3);
    }

    @Test
    public void testUpdate() {
        //Arrange
        Project project = new Project();
        project.setProjectName("QMS for testing");

        Project savedProject = repo.save(project);

        //Act
        Optional<Project> optionalUser = repo.findById(savedProject.getId());
        Project pp= optionalUser.get();

        pp.setProjectName("new QMS");
        repo.save(pp);

        //Assertion
        Project updatedUser = repo.findById(savedProject.getId()).get();
        Assertions.assertThat(updatedUser.getProjectName()).isEqualTo("new QMS");
    }

    @Test
    public void testGet() {
        //Arrange
        Project project = new Project();
        project.setProjectName("QMS for testing");

        Project savedProject = repo.save(project);

        //Act
        Optional<Project> optionalproject = repo.findById(savedProject.getId());

        //Assertion
        Assertions.assertThat(optionalproject).isPresent();
    }

    @Test
    public void testDelete() {
        //Arrange
        Project project = new Project();
        project.setProjectName("QMS for testing");

        Project savedProject = repo.save(project);

        //Act
        repo.deleteById(savedProject.getId());

        //Assertion
        Optional<Project> optionalUser = repo.findById(savedProject.getId());
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}