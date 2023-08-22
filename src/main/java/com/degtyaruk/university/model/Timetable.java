package com.degtyaruk.university.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "timetables")
@Getter
@SuperBuilder(setterPrefix = "set")
@NoArgsConstructor(force = true)
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE,
            mappedBy = "timetable")
    private List<Lecture> lectures;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Timetable)) {
            return false;
        }
        Timetable that = (Timetable) o;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.date, that.date) &&
                Objects.equals(this.lectures, that.lectures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, lectures);
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "id=" + id +
                ", day=" + date +
                '}';
    }
}

