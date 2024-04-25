package com.pismennaya.shop.interfaces;

import com.pismennaya.shop.models.Product;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProductDAO extends CommonDAO<Product, Long>{
    List<Product> getAllProduct();
}
