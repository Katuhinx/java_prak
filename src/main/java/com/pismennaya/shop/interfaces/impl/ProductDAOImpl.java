package com.pismennaya.shop.interfaces.impl;

import com.pismennaya.shop.models.Manager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.pismennaya.shop.interfaces.ProductDAO;
import com.pismennaya.shop.models.Product;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOImpl extends CommonDAOImpl<Product, Long> implements ProductDAO {
    public ProductDAOImpl() {
        super(Product.class);
    }

    @Override
    public List<Product> getByFilters(String name, String category) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            List<Predicate> criteria = new ArrayList<>();

            if (!name.equals("null")) {
                criteria.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            if (!category.equals("null")) {
                criteria.add(criteriaBuilder.equal(root.get("category").get("id"), category));
            }

            criteriaQuery.select(root).where(criteria.toArray(new Predicate[0]));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
