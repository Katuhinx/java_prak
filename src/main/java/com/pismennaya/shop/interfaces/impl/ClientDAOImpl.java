package com.pismennaya.shop.interfaces.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.pismennaya.shop.interfaces.ClientDAO;
import com.pismennaya.shop.models.Client;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ClientDAOImpl extends CommonDAOImpl<Client, Long> implements ClientDAO {
    public ClientDAOImpl() {
        super(Client.class);
    }

    @Override
    public List<Client> getByFilters(String data) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);
            List<Predicate> criteria = new ArrayList<>();

            if (!data.equals("null")) {
                criteria.add(
                    criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), "%" + data + "%"),
                        criteriaBuilder.like(root.get("surname"), "%" + data + "%"),
                        criteriaBuilder.like(root.get("phone"), "%" + data + "%"),
                        criteriaBuilder.like(root.get("email"), "%" + data + "%")
                    )
                );
            }

            criteriaQuery.select(root).where(criteria.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
