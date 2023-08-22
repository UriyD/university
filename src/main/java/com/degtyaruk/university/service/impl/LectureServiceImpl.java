package com.degtyaruk.university.service.impl;

import com.degtyaruk.university.dao.LectureDao;
import com.degtyaruk.university.dao.Page;
import com.degtyaruk.university.dao.ProfessorDao;
import com.degtyaruk.university.dao.TimetableDao;
import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.exception.InvalidEntityException;
import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.Group;
import com.degtyaruk.university.model.Lecture;
import com.degtyaruk.university.model.Professor;
import com.degtyaruk.university.model.Timetable;
import com.degtyaruk.university.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureDao lectureDao;
    private final ProfessorDao professorDao;

    private final TimetableDao timetableDao;

    @Autowired
    public LectureServiceImpl(LectureDao lectureDao, ProfessorDao professorDao, TimetableDao timetableDao) {
        this.lectureDao = lectureDao;
        this.professorDao = professorDao;
        this.timetableDao = timetableDao;
    }

    @Override
    public Lecture add(Lecture lecture) {
        validateAccordanceFaculty(lecture);
        validateProfessor(lecture);
        validateTimetable(lecture);
        return lectureDao.add(lecture);
    }

    @Override
    public void update(Lecture lecture) {
        lectureDao.update(lecture);
    }

    @Override
    public void removeById(Integer id) {
        lectureDao.removeById(id);
    }

    @Override
    public Lecture getById(Integer id) {
        return lectureDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Can't find Lecture. "));
    }

    @Override
    public List<Lecture> getAll() {
        return lectureDao.getAll();
    }

    @Override
    public List<Lecture> getAll(Page page) {
        return lectureDao.getAll(page);
    }

    private void validateAccordanceFaculty(Lecture lecture) {
        Group group = lecture.getGroup();
        Course course = lecture.getCourse();
        if (!group.getFaculty().getId().equals(course.getFaculty().getId())) {
            throw new InvalidEntityException("Invalid Lecture: mismatch faculty between course ang group");
        }
    }

    private void validateProfessor(Lecture lecture) {
        Optional<Professor> optionalProfessor = professorDao.getById(lecture.getProfessor().getId());
        Professor professor = optionalProfessor.get();
        List<Course> professorCourses = professor.getCourses();
        Integer courseId = lecture.getCourse().getId();
        if (professorCourses.stream()
                .map(Course::getId)
                .noneMatch(id -> id.equals(courseId))) {
            throw new InvalidEntityException("Invalid Lecture: professor does not teach such course");
        }
    }

    private void validateTimetable(Lecture lecture) {
        Timetable timetable = lecture.getTimetable();
        Integer timetableId = timetable.getId();
        Optional<Timetable> optionalTimetable = timetableDao.getById(timetableId);
        if (optionalTimetable.isEmpty()) {
            throw new InvalidEntityException("Invalid Lecture: timetable with passed id absent");
        }
    }
}

