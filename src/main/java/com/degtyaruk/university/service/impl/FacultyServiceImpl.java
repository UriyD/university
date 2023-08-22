package com.degtyaruk.university.service.impl;

import com.degtyaruk.university.dao.FacultyDao;
import com.degtyaruk.university.dao.Page;
import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.model.Faculty;
import com.degtyaruk.university.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyDao facultyDao;

    @Autowired
    public FacultyServiceImpl(FacultyDao facultyDao) {
        this.facultyDao = facultyDao;
    }

    @Override
    public Faculty add(Faculty faculty) {
        return facultyDao.add(faculty);
    }

    @Override
    public void update(Faculty faculty) {
        facultyDao.update(faculty);
    }

    @Override
    public void removeById(Integer id) {
        facultyDao.removeById(id);
    }

    @Override
    public Faculty getById(Integer id) {
        return facultyDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Can't find Faculty. "));
    }

    @Override
    public List<Faculty> getAll() {
        return facultyDao.getAll();
    }

    @Override
    public List<Faculty> getAll(Page page) {
        return facultyDao.getAll(page);
    }
}

