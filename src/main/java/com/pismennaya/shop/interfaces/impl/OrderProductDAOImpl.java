package com.pismennaya.shop.interfaces.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import com.pismennaya.shop.interfaces.OrderProductDAO;
import com.pismennaya.shop.models.OrderProduct;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class OrderProductDAOImpl extends CommonDAOImpl<OrderProduct, Long> implements OrderProductDAO {
    public OrderProductDAOImpl() {
        super(OrderProduct.class);
    }

    @Override
    public List<OrderProduct> getByOrderId(Long order_id) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<OrderProduct> criteriaQuery = criteriaBuilder.createQuery(OrderProduct.class);
            Root<OrderProduct> root = criteriaQuery.from(OrderProduct.class);
            Predicate criteria = criteriaBuilder.equal(root.get("order").get("id"), order_id);
            criteriaQuery.select(root).where(criteria);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}