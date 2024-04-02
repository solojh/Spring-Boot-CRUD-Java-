package com.mycompany.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired private ProjectRepository repo;

    public List<Project> listAll() {
        return (List<Project>) repo.findAll();
    }

    public void save(Project project) {
        repo.save(project);
    }
    public Project get(Integer id) throws ProjectNotFoundException {
        Optional<Project> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ProjectNotFoundException("Could not find any projects with ID " + id);
    }
    public void delete(Integer id) throws ProjectNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new ProjectNotFoundException("Could not find any projects with ID " + id);
        }
        repo.deleteById(id);
    }
}
