package com.degtyaruk.university.service.impl;


import com.degtyaruk.university.dao.FacultyDao;
import com.degtyaruk.university.dao.GroupDao;
import com.degtyaruk.university.dao.Page;
import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.exception.InvalidEntityException;
import com.degtyaruk.university.model.Faculty;
import com.degtyaruk.university.model.Group;
import com.degtyaruk.university.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupDao groupDao;
    private final FacultyDao facultyDao;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao, FacultyDao facultyDao) {
        this.groupDao = groupDao;
        this.facultyDao = facultyDao;
    }

    @Override
    public Group add(Group group) {
        validateFaculty(group);
        return groupDao.add(group);
    }

    @Override
    public void update(Group group) {
        groupDao.update(group);
    }

    @Override
    public void removeById(Integer id) {
        groupDao.removeById(id);
    }

    @Override
    public Group getById(Integer id) {
        return groupDao.getById(id).orElseThrow(() -> new EntityNotFoundException("Can't find Group. "));
    }

    @Override
    public List<Group> getAll() {
        return groupDao.getAll();
    }

    @Override
    public List<Group> getAll(Page page) {
        return groupDao.getAll(page);
    }

    private void validateFaculty(Group group) {
        Faculty faculty = group.getFaculty();
        Integer facultyId = faculty.getId();
        Optional<Faculty> optionalFaculty = facultyDao.getById(facultyId);
        if (optionalFaculty.isEmpty()) {
            throw new InvalidEntityException("Invalid Group: faculty with passed id absent");
        }
    }
}

