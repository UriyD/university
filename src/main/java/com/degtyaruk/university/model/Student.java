package com.degtyaruk.university.model;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;
@Entity
@Table(name = "students")
@AssociationOverride(name = "courses",
        joinTable = @JoinTable(name = "students_courses",
                joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id")))
public class Student extends User {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    private Group group;

    protected Student() {
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    protected Student(StudentBuilder userBuilder) {
        super(userBuilder);
        this.group = userBuilder.group;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Student that = (Student) o;
        return Objects.equals(this.group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), group);
    }

    @Override
    public String toString() {
        return super.toString() + ", group='" + group + '\'' + '}';
    }

    public static class StudentBuilder extends UserBuilder<StudentBuilder> {
        private Group group;

        public StudentBuilder() {
        }

        public StudentBuilder self() {
            return this;
        }

        public Student build() {
            return new Student(self());
        }

        public StudentBuilder withGroup(Group group) {
            this.group = group;
            return self();
        }

    }
}

