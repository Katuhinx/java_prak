package com.pismennaya.shop.DAO;

import com.pismennaya.shop.interfaces.CategoryDAO;
import com.pismennaya.shop.interfaces.impl.CategoryDAOImpl;
import com.pismennaya.shop.models.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryDAOTest {
    @Autowired
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Test
    public void testSave() {
        Category newCategory = new Category();
        newCategory.setName("Test Category");
        categoryDAO.save(newCategory);
        Category savedCategory = categoryDAO.getById(newCategory.getId());
        assertNotNull(savedCategory);
        assertEquals(newCategory.getName(), savedCategory.getName());
    }

    @Test
    public void testGetById() {
        Category category = categoryDAO.getById(1L);
        assertNotNull(category);
        assertEquals(1L, category.getId().longValue());
    }

    @Test
    public void testGetAll() {
        List<Category> categories = (List<Category>) categoryDAO.getAll();
        assertNotNull(categories);
        assertTrue(categories.size() > 0);
    }

    @Test
    public void testUpdate() {
        Category category = categoryDAO.getById(1L);
        category.setName("Updated Test Category");
        categoryDAO.update(category);
        Category updatedCategory = categoryDAO.getById(1L);
        assertEquals("Updated Test Category", updatedCategory.getName());
    }

    @Test
    public void testDelete() {
        Category category = categoryDAO.getById(1L);
        categoryDAO.delete(category);
        Category deletedCategory = categoryDAO.getById(1L);
        assertNull(deletedCategory);
    }
}
