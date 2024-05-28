package com.pismennaya.shop.interfaces.impl;

import com.pismennaya.shop.interfaces.CategoryDAO;
import com.pismennaya.shop.models.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CategoryDAOImpl extends CommonDAOImpl<Category, Long> implements CategoryDAO {
    public CategoryDAOImpl() {
        super(Category.class);
    }
}
