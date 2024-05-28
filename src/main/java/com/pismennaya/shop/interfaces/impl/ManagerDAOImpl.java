package com.pismennaya.shop.interfaces.impl;

import com.pismennaya.shop.interfaces.ManagerDAO;
import com.pismennaya.shop.models.Manager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerDAOImpl extends CommonDAOImpl<Manager, Long> implements ManagerDAO {
    public ManagerDAOImpl(){
        super(Manager.class);
    }

    @Override
    public Manager getByLogin(String login, String password) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Manager> criteriaQuery = criteriaBuilder.createQuery(Manager.class);
            Root<Manager> root = criteriaQuery.from(Manager.class);
            Predicate criteria = criteriaBuilder.and(criteriaBuilder.equal(root.get("login"), login), criteriaBuilder.equal(root.get("password"), password));
            criteriaQuery.select(root).where(criteria);
            return session.createQuery(criteriaQuery).uniqueResult();
        }
    }
}

