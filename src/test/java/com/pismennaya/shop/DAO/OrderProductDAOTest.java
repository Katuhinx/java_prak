package com.pismennaya.shop.DAO;

import com.pismennaya.shop.interfaces.OrderProductDAO;
import com.pismennaya.shop.interfaces.impl.OrderProductDAOImpl;
import com.pismennaya.shop.models.OrderProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderProductDAOTest {
    @Autowired
    private OrderProductDAO orderProductDAO = new OrderProductDAOImpl(); 

    @Test
    public void testGetByOrderProduct() {
        List<OrderProduct> orderProducts = orderProductDAO.getByOrderId(1L);
        assertNotNull(orderProducts);
        assertTrue(orderProducts.size() > 0);
    }
}