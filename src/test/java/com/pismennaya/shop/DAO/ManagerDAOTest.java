package com.pismennaya.shop.DAO;

import com.pismennaya.shop.interfaces.ManagerDAO;
import com.pismennaya.shop.interfaces.impl.ManagerDAOImpl;
import com.pismennaya.shop.models.Manager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ManagerDAOTest {
    @Autowired
    private ManagerDAO managerDAO = new ManagerDAOImpl();

    @Test
    public void testGetByLogin() {
        Manager manager = managerDAO.getByLogin("testmanager", "54321");
        Assert.assertNotNull(manager);
        Assert.assertEquals("testmanager", manager.getLogin());
        Assert.assertEquals("password", manager.getPassword());
    }
}
