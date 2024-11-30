package org.example.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Teacher model for storing teacher-specific data.
 */
@Entity
@Table(name = "teachers", catalog = "studentgradingsys")
public class Teacher implements Serializable {

    private int teacherId;
    private Users user; // Reference to the Users table

    public Teacher() {
    }

    public Teacher(int teacherId, Users user) {
        this.teacherId = teacherId;
        this.user = user;
    }

    @Id
    @Column(name = "teacher_id", unique = true, nullable = false)
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
