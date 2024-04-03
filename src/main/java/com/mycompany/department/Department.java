package com.mycompany.department;

import com.mycompany.employee.Employee;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "Departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false, name = "department_name")
    private String departmentName;

    @OneToMany(mappedBy = "department")
    private List<Employee> employee;

    private boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Employee> getEmployee() { return employee; }

    public void setEmployee(List<Employee> employee) { this.employee = employee; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}