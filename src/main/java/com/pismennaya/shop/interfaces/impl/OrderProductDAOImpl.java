package com.pismennaya.shop.interfaces.impl;

import com.pismennaya.shop.interfaces.OrderProductDAO;
import com.pismennaya.shop.models.OrderProduct;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderProductDAOImpl extends CommonDAOImpl<OrderProduct, Long> implements OrderProductDAO {
    public OrderProductDAOImpl() {
        super(OrderProduct.class);
    }

    @Override
    public List<OrderProduct> getByOrderId(Long order_id) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<OrderProduct> criteriaQuery = criteriaBuilder.createQuery(OrderProduct.class);
        Root<OrderProduct> root = criteriaQuery.from(OrderProduct.class);
        Predicate criteria = criteriaBuilder.equal(root.get("order"), order_id);
        criteriaQuery.select(root).where(criteria);

        return session.createQuery(criteriaQuery).getResultList();
    }
}