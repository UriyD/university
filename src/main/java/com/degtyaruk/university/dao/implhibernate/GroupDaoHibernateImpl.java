package com.degtyaruk.university.dao.implhibernate;

import com.degtyaruk.university.dao.GroupDao;
import com.degtyaruk.university.model.Group;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("hibernate")
public class GroupDaoHibernateImpl extends AbstractCrudHibernateImpl<Group> implements GroupDao {

    public GroupDaoHibernateImpl() {
        super(Group.class);
    }
}

