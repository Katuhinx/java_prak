package com.pismennaya.shop.interfaces.impl;

import com.pismennaya.shop.interfaces.OrderDAO;
import com.pismennaya.shop.models.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOImpl extends CommonDAOImpl<Order, Long> implements OrderDAO {
    public OrderDAOImpl(){
        super(Order.class);
    }

}
