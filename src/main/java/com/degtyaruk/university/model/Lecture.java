package com.degtyaruk.university.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "lectures")
@Getter
@SuperBuilder(setterPrefix = "set")
@NoArgsConstructor(force = true)
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "lecture_time_start")
    private LocalTime timeStartLecture;

    @Column(name = "lecture_time_finish")
    private LocalTime timeEndLecture;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "professor_id")
    private Professor professor;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTimeStartLecture(LocalTime timeStartLecture) {
        this.timeStartLecture = timeStartLecture;
    }

    public void setTimeEndLecture(LocalTime timeEndLecture) {
        this.timeEndLecture = timeEndLecture;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lecture)) {
            return false;
        }
        Lecture that = (Lecture) o;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.timeStartLecture, that.timeStartLecture) &&
                Objects.equals(this.timeEndLecture, that.timeEndLecture) &&
                Objects.equals(this.course, that.course) &&
                Objects.equals(this.group, that.group) &&
                Objects.equals(this.classroom, that.classroom) &&
                Objects.equals(this.professor, that.professor) &&
                Objects.equals(this.timetable, that.timetable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeStartLecture, timeEndLecture, course, group, classroom, professor, timetable);
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + id +
                ", timeStartLecture=" + timeStartLecture +
                ", timeEndLecture=" + timeEndLecture +
                '}';
    }
}

