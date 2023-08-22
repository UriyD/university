package com.degtyaruk.university.service.impl;

import com.degtyaruk.university.dao.Page;
import com.degtyaruk.university.dao.ProfessorDao;
import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.model.Professor;
import com.degtyaruk.university.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorDao professorDao;

    @Autowired
    public ProfessorServiceImpl(ProfessorDao professorDao) {
        this.professorDao = professorDao;
    }

    @Override
    public Professor add(Professor professor) {
        return professorDao.add(professor);
    }

    @Override
    public void update(Professor professor) {
        professorDao.update(professor);
    }

    @Override
    public void removeById(Integer id) {
        professorDao.removeById(id);
    }

    @Override
    public Professor getById(Integer id) {
        return professorDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Can't find Professor. "));
    }

    @Override
    public List<Professor> getAll() {
        return professorDao.getAll();
    }

    @Override
    public List<Professor> getAll(Page page) {
        return professorDao.getAll(page);
    }
}

