package com.mycompany.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProjectController {
    @Autowired private ProjectService service;

    @GetMapping("/projects")
    public String showProjectList(Model model) {
        List<Project> listProjects = service.listAll();
        model.addAttribute("listProjects", listProjects);

        return "projects";
    }
    @GetMapping("/projects/new")
    public String showNewForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("pageTitle", "Add New Project");
        return "project_form";
    }

    @PostMapping("/projects/save")
    public String saveproject(Project project, RedirectAttributes ra) {
        service.save(project);
        ra.addFlashAttribute("message", "The project has been saved successfully.");
        return "redirect:/projects";
    }
    @GetMapping("/projects/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Project project = service.get(id);
            model.addAttribute("project", project);
            model.addAttribute("pageTitle", "Edit project (ID: " + id + ")");

            return "project_form";
        } catch (ProjectNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/projects";
        }
    }
    @GetMapping("/projects/delete/{id}")
    public String deleteproject(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The project ID " + id + " has been deleted.");
        } catch (ProjectNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/projects";
    }

}
