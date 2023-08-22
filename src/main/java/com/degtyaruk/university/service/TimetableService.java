package com.degtyaruk.university.service;

import com.degtyaruk.university.model.Professor;
import com.degtyaruk.university.model.Student;
import com.degtyaruk.university.model.Timetable;

import java.time.LocalDate;
import java.util.List;

public interface TimetableService extends UniversityService<Timetable, Integer> {

    List<Timetable> getTimetableStudent(LocalDate startPeriod, LocalDate finishPeriod, Student student);

    List<Timetable> getTimetableProfessor(LocalDate startPeriod, LocalDate finishPeriod, Professor professor);
}

