package com.pismennaya.shop.DAO;

import com.pismennaya.shop.interfaces.CategoryDAO;
import com.pismennaya.shop.interfaces.impl.CategoryDAOImpl;
import com.pismennaya.shop.models.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryDAOTest {
    @Autowired
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Test
    public void testGetById() {
        Category category = categoryDAO.getById(1);
        Assert.assertNotNull(category);
        Assert.assertEquals(1L, category.getId().longValue());

    }

    @Test
    public void testGetAll() {
        List<Category> categories = categoryDAO.getAll();
        Assert.assertNotNull(categories);
        Assert.assertTrue(categories.size() > 0);
    }

    @Test
    public void testSave() {
        Category newCategory = new Category();
        newCategory.setName("Test Category");
        categoryDAO.save(newCategory);
        Category savedCategory = categoryDAO.getById(newCategory.getId());
        Assert.assertNotNull(savedCategory);
        Assert.assertEquals(newCategory.getName(), savedCategory.getName());
    }

    @Test
    public void testUpdate() {
        Category category = categoryDAO.getById(1);
        category.setName("Updated Test Category");
        categoryDAO.update(category);
        Category updatedCategory = categoryDAO.getById(1);
        Assert.assertEquals("Updated Test Category", updatedCategory.getName());
    }

    @Test
    public void testDelete() {
        Category category = categoryDAO.getById(1);
        categoryDAO.delete(category);
        Category deletedCategory = categoryDAO.getById(1);
        Assert.assertNull(deletedCategory);
    }
}
