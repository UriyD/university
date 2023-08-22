package com.degtyaruk.university.dao.implhibernate;


import com.degtyaruk.university.dao.TimetableDao;
import com.degtyaruk.university.model.Professor;
import com.degtyaruk.university.model.Student;
import com.degtyaruk.university.model.Timetable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Profile("hibernate")
public class TimetableDaoHibernateImpl extends AbstractCrudHibernateImpl<Timetable> implements TimetableDao {

    public TimetableDaoHibernateImpl() {
        super(Timetable.class);
    }

    @Override
    public List<Timetable> selectStudentTimetableToDateFromPeriod(LocalDate startPeriod, LocalDate finishPeriod, Student student) {
        return null;
    }

    @Override
    public List<Timetable> selectProfessorTimetableToDateFromPeriod(LocalDate startPeriod, LocalDate finishPeriod, Professor professor) {
        return null;
    }
}

