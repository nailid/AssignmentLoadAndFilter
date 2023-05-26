package com.backend.assignment.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double total_marks;

    public User(Long id, String name, double total_marks) {
        this.id = id;
        this.name = name;
        this.total_marks = total_marks;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(double total_marks) {
        this.total_marks = total_marks;
    }
}
