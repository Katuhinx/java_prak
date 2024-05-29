package com.pismennaya.shop.DAO;

import com.pismennaya.shop.interfaces.ClientDAO;
import com.pismennaya.shop.models.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientDAOTest {
    private ClientDAO clientDAO;

    @Test
    public void testGetByFilters() {
        List<Client> clients = clientDAO.getByFilters("testlogin");

    }
}
