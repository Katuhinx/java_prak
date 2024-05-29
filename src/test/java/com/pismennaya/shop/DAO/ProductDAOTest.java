package com.pismennaya.shop.DAO;

import com.pismennaya.shop.interfaces.ProductDAO;
import com.pismennaya.shop.interfaces.impl.ProductDAOImpl;
import com.pismennaya.shop.models.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductDAOTest {
    @Autowired
    private ProductDAO productDAO = new ProductDAOImpl();

    @Test
    public void testGetByFilters() {
        List<Product> products = productDAO.getByFilters("Продукт 1", "1");
        assertNotNull(products);
        assertTrue(products.size() > 0);

    }
}