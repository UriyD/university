package com.degtyaruk.university.dao.implhibernate;

import com.degtyaruk.university.dao.FacultyDao;
import com.degtyaruk.university.model.Faculty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("hibernate")
public class FacultyDaoHibernateImpl extends AbstractCrudHibernateImpl<Faculty> implements FacultyDao {

    public FacultyDaoHibernateImpl() {
        super(Faculty.class);
    }
}

