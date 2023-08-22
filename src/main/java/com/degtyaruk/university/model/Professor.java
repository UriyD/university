package com.degtyaruk.university.model;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import java.util.List;


@Entity
@Table(name = "professors")
@AssociationOverride(name = "courses",
        joinTable = @JoinTable(name = "professors_courses",
                joinColumns = @JoinColumn(name = "professor_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id")))
public class Professor extends User {

    private Professor(ProfessorBuilder userBuilder) {
        super(userBuilder);
    }

    public Professor() {
    }

    @Override
    public String toString() {
        return super.toString() + '}';
    }

    public static class ProfessorBuilder extends UserBuilder<ProfessorBuilder> {

        public ProfessorBuilder() {
        }

        @Override
        public ProfessorBuilder self() {
            return this;
        }

        public Professor build() {
            return new Professor(self());
        }
    }
}

