package com.pismennaya.shop.interfaces.impl;

import com.pismennaya.shop.interfaces.OrderDAO;
import com.pismennaya.shop.models.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class OrderDAOImpl extends CommonDAOImpl<Order, Long> implements OrderDAO {
    public OrderDAOImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> getByFilters(String id, String order_date, String address) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);

            List<Predicate> criteria = new ArrayList<>();

            if (!id.equals("null")) {
                criteria.add(criteriaBuilder.equal(root.get("id"), id));
            }

            if (!order_date.equals("null")) {
                criteria.add(criteriaBuilder.equal(root.get("order_date"),  Date.valueOf(order_date)));
            }

            if (!address.equals("null")) {
                criteria.add(criteriaBuilder.like(root.get("address"), "%" + address + "%"));
            }

            criteriaQuery.select(root).where(criteria.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
