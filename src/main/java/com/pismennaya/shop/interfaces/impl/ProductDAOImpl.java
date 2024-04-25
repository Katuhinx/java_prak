package com.pismennaya.shop.interfaces.impl;

import com.pismennaya.shop.interfaces.ProductDAO;
import com.pismennaya.shop.models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOImpl extends CommonDAOImpl<Product, Long> implements ProductDAO{
    protected SessionFactory sessionFactory;

    public ProductDAOImpl(){
        super(Product.class);
    }

    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<Product>();
        products.add(new Product());
        return products;
    }
}
