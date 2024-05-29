package com.pismennaya.shop.DAO;

import com.pismennaya.shop.interfaces.OrderDAO;
import com.pismennaya.shop.interfaces.impl.OrderDAOImpl;
import com.pismennaya.shop.models.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderDAOTest {
    @Autowired
    private OrderDAO orderDAO = new OrderDAOImpl();

    @Test
    public void testGetByFilters() {
        List<Order> orders = orderDAO.getByFilters("1", "null", "null");
        assertNotNull(orders);
        assertTrue(orders.size() > 0);
    }

    @Test
    public void testGetByClient() {
        List<Order> orders = orderDAO.getByClient(1L);
        assertNotNull(orders);
        assertTrue(orders.size() > 0);
    }
}
