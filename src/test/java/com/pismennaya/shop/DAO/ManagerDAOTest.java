package com.pismennaya.shop.DAO;

import com.pismennaya.shop.interfaces.ManagerDAO;
import com.pismennaya.shop.interfaces.impl.ManagerDAOImpl;
import com.pismennaya.shop.models.Manager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ManagerDAOTest {
    @Autowired
    private ManagerDAO managerDAO = new ManagerDAOImpl();

    @Test
    public void testGetByLogin() {
        Manager manager = managerDAO.getByLogin("admin", "12345");
        assertNotNull(manager);
        assertEquals("admin", manager.getLogin());
        assertEquals("12345", manager.getPassword());
    }
}
