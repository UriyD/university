package com.degtyaruk.university.dao.implhibernate;

import com.degtyaruk.university.dao.LectureDao;
import com.degtyaruk.university.model.Lecture;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("hibernate")
public class LectureDaoHibernateImpl extends AbstractCrudHibernateImpl<Lecture> implements LectureDao {

    public LectureDaoHibernateImpl() {
        super(Lecture.class);
    }
}

