package com.pismennaya.shop.interfaces.impl;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.pismennaya.shop.interfaces.ProductDAO;
import com.pismennaya.shop.models.Product;
import java.util.List;

@Repository
public class ProductDAOImpl extends CommonDAOImpl<Product, Long> implements ProductDAO {
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
