package com.degtyaruk.university.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "groups")
@Getter
@SuperBuilder(setterPrefix = "set")
@NoArgsConstructor(force = true)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @Column(name = "group_name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE,
            mappedBy = "group")
    private List<Student> students;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Group)) {
            return false;
        }
        Group that = (Group) o;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.faculty, that.faculty) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, faculty, name, students);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

