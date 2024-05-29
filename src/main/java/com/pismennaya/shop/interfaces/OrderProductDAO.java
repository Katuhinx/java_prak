package com.pismennaya.shop.interfaces;

import com.pismennaya.shop.models.OrderProduct;
import java.util.List;

public interface OrderProductDAO extends CommonDAO<OrderProduct, Long>{
    List<OrderProduct> getByOrderId(Long order_id);
}