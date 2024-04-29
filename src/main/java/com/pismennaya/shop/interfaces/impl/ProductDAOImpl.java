package com.pismennaya.shop.interfaces.impl;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.pismennaya.shop.interfaces.ProductDAO;
import com.pismennaya.shop.models.Product;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductDAOImpl extends CommonDAOImpl<Product, Long> implements ProductDAO{
    public ProductDAOImpl(){
        super(Product.class);
    }

    @Override
    public List<Product> getAllProducts() {
        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery("FROM Product", Product.class);
            return query.getResultList();
        }
    }
}
