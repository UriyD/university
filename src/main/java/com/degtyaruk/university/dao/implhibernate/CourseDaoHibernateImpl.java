package com.degtyaruk.university.dao.implhibernate;

import com.degtyaruk.university.dao.CourseDao;
import com.degtyaruk.university.model.Course;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("hibernate")
public class CourseDaoHibernateImpl extends AbstractCrudHibernateImpl<Course> implements CourseDao {

    public CourseDaoHibernateImpl() {
        super(Course.class);
    }
}

