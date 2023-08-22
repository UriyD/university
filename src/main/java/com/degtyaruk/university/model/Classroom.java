package com.degtyaruk.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
@Entity
@Table(name = "classrooms")
@Getter
@SuperBuilder(setterPrefix = "set")
@NoArgsConstructor(force = true)
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "number")
    private Integer number;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classroom)) {
            return false;
        }
        Classroom that = (Classroom) o;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", number=" + number +
                '}';
    }
}

