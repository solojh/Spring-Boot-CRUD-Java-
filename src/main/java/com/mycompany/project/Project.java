package com.mycompany.project;

import com.mycompany.employee.Employee;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false, name = "project_name")
    private String ProjectName;

    @ManyToMany
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employees;

    private boolean enabled;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        this.ProjectName = projectName;
    }

    public List<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(List<Employee> employee) {
        this.employees = employee;
    }

    public String getEmployeeAsString() {
        String temp = "";
        for (int i = 0; i < this.employees.size(); i++) {
            temp = temp + this.employees.get(i).getName() + ", ";
        }

        return temp;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + ProjectName + '\'' +
//                ", assignedemployees='" + employees.() + '\'' +
                '}';
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}