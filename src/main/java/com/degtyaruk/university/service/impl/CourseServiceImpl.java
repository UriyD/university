package com.degtyaruk.university.service.impl;


import com.degtyaruk.university.dao.CourseDao;
import com.degtyaruk.university.dao.FacultyDao;
import com.degtyaruk.university.dao.Page;
import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.exception.InvalidEntityException;
import com.degtyaruk.university.model.Course;
import com.degtyaruk.university.model.Faculty;
import com.degtyaruk.university.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseDao courseDao;
    private final FacultyDao facultyDao;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao, FacultyDao facultyDao) {
        this.courseDao = courseDao;
        this.facultyDao = facultyDao;
    }

    @Override
    public Course add(Course course) {
        validateFaculty(course);
        return courseDao.add(course);
    }

    @Override
    public void update(Course course) {
        courseDao.update(course);
    }

    @Override
    public void removeById(Integer id) {
        courseDao.removeById(id);
    }

    @Override
    public Course getById(Integer id) {
        return courseDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Can't find Course. "));
    }

    @Override
    public List<Course> getAll() {
        return courseDao.getAll();
    }

    @Override
    public List<Course> getAll(Page page) {
        return courseDao.getAll();
    }

    private void validateFaculty(Course course) {
        Faculty faculty = course.getFaculty();
        Integer facultyId = faculty.getId();
        Optional<Faculty> optionalFaculty = facultyDao.getById(facultyId);
        if (optionalFaculty.isEmpty()) {
            throw new InvalidEntityException("Invalid Course: faculty with passed id absent. ");
        }
    }
}

