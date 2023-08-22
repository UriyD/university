package com.degtyaruk.university.dao.implhibernate;

import com.degtyaruk.university.dao.ClassroomDao;
import com.degtyaruk.university.model.Classroom;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


@Repository
@Profile("hibernate")
public class ClassroomDaoHibernateImpl extends AbstractCrudHibernateImpl<Classroom> implements ClassroomDao {


    public ClassroomDaoHibernateImpl() {
        super(Classroom.class);
    }

}

