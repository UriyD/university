package com.degtyaruk.university.service.impl;


import com.degtyaruk.university.dao.GroupDao;
import com.degtyaruk.university.dao.Page;
import com.degtyaruk.university.dao.StudentDao;
import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.exception.InvalidEntityException;
import com.degtyaruk.university.model.Group;
import com.degtyaruk.university.model.Student;
import com.degtyaruk.university.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final GroupDao groupDao;

    public StudentServiceImpl(StudentDao studentDao, GroupDao groupDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
    }

    @Override
    public Student add(Student student) {
        validateGroup(student);
        return studentDao.add(student);
    }

    @Override
    public void update(Student student) {
        studentDao.update(student);
    }

    @Override
    public void removeById(Integer id) {
        studentDao.removeById(id);
    }

    @Override
    public Student getById(Integer id) {
        return studentDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Can't find Student. "));
    }

    @Override
    public List<Student> getAll() {
        return studentDao.getAll();
    }

    @Override
    public List<Student> getAll(Page page) {
        return studentDao.getAll(page);
    }

    private void validateGroup(Student student) {
        Group group = student.getGroup();
        Integer groupId = group.getId();
        Optional<Group> optionalGroup = groupDao.getById(groupId);
        if (optionalGroup.isEmpty()) {
            throw new InvalidEntityException("Invalid Student: group with passed id absent");
        }
    }
}

