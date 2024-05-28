package com.pismennaya.shop.interfaces;

import com.pismennaya.shop.models.Product;
import java.util.List;

public interface ProductDAO extends CommonDAO<Product, Long>{
    List<Product> getByFilters(String name, String category);
}
