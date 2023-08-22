package com.degtyaruk.university.dao.implhibernate;

import com.degtyaruk.university.dao.StudentDao;
import com.degtyaruk.university.model.Student;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("hibernate")
public class StudentDaoHibernateImpl extends AbstractCrudHibernateImpl<Student> implements StudentDao {

    public StudentDaoHibernateImpl() {
        super(Student.class);
    }
}

