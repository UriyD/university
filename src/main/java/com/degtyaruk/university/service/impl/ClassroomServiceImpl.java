package com.degtyaruk.university.service.impl;

import com.degtyaruk.university.dao.ClassroomDao;
import com.degtyaruk.university.dao.Page;
import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.model.Classroom;
import com.degtyaruk.university.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomDao classroomDao;

    @Autowired
    public ClassroomServiceImpl(ClassroomDao classroomDao) {
        this.classroomDao = classroomDao;
    }

    @Override
    public Classroom add(Classroom classroom) {
        return classroomDao.add(classroom);
    }

    @Override
    public void update(Classroom classroom) {
        classroomDao.update(classroom);
    }

    @Override
    public void removeById(Integer id) {
        classroomDao.removeById(id);
    }

    @Override
    public Classroom getById(Integer id) {
        return classroomDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Can't find Classroom. "));
    }

    @Override
    public List<Classroom> getAll() {
        return classroomDao.getAll();
    }

    @Override
    public List<Classroom> getAll(Page page) {
        return classroomDao.getAll(page);
    }
}

