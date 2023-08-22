package com.degtyaruk.university.dao.implhibernate;

import com.degtyaruk.university.dao.ProfessorDao;
import com.degtyaruk.university.model.Professor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("hibernate")
public class ProfessorDaoHibernateImpl extends AbstractCrudHibernateImpl<Professor> implements ProfessorDao {

    public ProfessorDaoHibernateImpl() {
        super(Professor.class);
    }
}

