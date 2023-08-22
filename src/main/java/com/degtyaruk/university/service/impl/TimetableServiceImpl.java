package com.degtyaruk.university.service.impl;

import com.degtyaruk.university.dao.Page;
import com.degtyaruk.university.dao.TimetableDao;
import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.model.Professor;
import com.degtyaruk.university.model.Student;
import com.degtyaruk.university.model.Timetable;
import com.degtyaruk.university.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TimetableServiceImpl implements TimetableService {

    private final TimetableDao timetableDao;

    @Autowired
    public TimetableServiceImpl(TimetableDao timetableDao) {
        this.timetableDao = timetableDao;
    }

    @Override
    public Timetable add(Timetable timetable) {
        return timetableDao.add(timetable);
    }

    @Override
    public void update(Timetable timetable) {
        timetableDao.update(timetable);
    }

    @Override
    public void removeById(Integer id) {
        timetableDao.removeById(id);
    }

    @Override
    public Timetable getById(Integer id) {
        return timetableDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Can't find Timetable. "));
    }

    @Override
    public List<Timetable> getAll() {
        return timetableDao.getAll();
    }

    @Override
    public List<Timetable> getAll(Page page) {
        return timetableDao.getAll(page);
    }

    @Override
    public List<Timetable> getTimetableStudent(LocalDate startPeriod, LocalDate finishPeriod, Student student) {
        return timetableDao.selectStudentTimetableToDateFromPeriod(startPeriod, finishPeriod, student);
    }

    @Override
    public List<Timetable> getTimetableProfessor(LocalDate startPeriod, LocalDate finishPeriod, Professor professor) {
        return timetableDao.selectProfessorTimetableToDateFromPeriod(startPeriod, finishPeriod, professor);
    }
}

