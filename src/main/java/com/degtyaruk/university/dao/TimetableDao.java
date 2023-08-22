package com.degtyaruk.university.dao;

import com.degtyaruk.university.model.Professor;
import com.degtyaruk.university.model.Student;
import com.degtyaruk.university.model.Timetable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimetableDao extends CrudDao<Timetable, Integer> {

    List<Timetable> selectStudentTimetableToDateFromPeriod(LocalDate startPeriod, LocalDate finishPeriod, Student student);

    List<Timetable> selectProfessorTimetableToDateFromPeriod(LocalDate startPeriod, LocalDate finishPeriod, Professor professor);
}

