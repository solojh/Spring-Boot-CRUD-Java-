package com.mycompany.employee;

import com.mycompany.department.Department;
import com.mycompany.project.Project;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false, name = "name")
    private String Name;

    @Column(nullable = false, unique = true, length = 45)
    private String Email;

    @Column(length = 45, nullable = false, name = "position")
    private String Position;

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(mappedBy = "employees")
//    @JoinTable(
//            name = "employee_project",
//            joinColumns = @JoinColumn(name = "employee_id"),
//            inverseJoinColumns = @JoinColumn(name = "project_id")
//    )
    private List<Project> projects;

    private boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        this.Position = position;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {this.department = department;}

    public List<Project> getProjects() {return projects;}

    public void setProject(List<Project> project) {this.projects = project;}

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Position='" + Position + '\'' +
                ", department_id'" + department.getId() + '\'' +
        //        ", project_id'" + List<Project> + '\'' +
                '}';
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}