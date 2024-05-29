package com.pismennaya.shop.interfaces;

import com.pismennaya.shop.models.Order;
import java.util.List;

public interface OrderDAO extends CommonDAO<Order, Long>{
    List<Order> getByFilters(String id, String order_date, String address);
    List<Order> getByClient(Long client_id);
}
