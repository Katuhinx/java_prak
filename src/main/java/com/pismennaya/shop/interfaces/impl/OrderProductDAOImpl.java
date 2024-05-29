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
}