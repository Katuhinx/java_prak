package com.pismennaya.shop.DAO;

import com.pismennaya.shop.interfaces.ClientDAO;
import com.pismennaya.shop.interfaces.impl.ClientDAOImpl;
import com.pismennaya.shop.models.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientDAOTest {
    @Autowired
    private ClientDAO clientDAO = new ClientDAOImpl();

    @Test
    public void testSave() {
        Client newClient = new Client();
        newClient.setName("Test1");
        newClient.setPhone("89123456701");
        clientDAO.save(newClient);

        Client savedClient = clientDAO.getById(newClient.getId());
        assertNotNull(savedClient);
        assertEquals(newClient.getName(), savedClient.getName());
        assertEquals(newClient.getPhone(), savedClient.getPhone());
    }

    @Test
    public void testGetById() {
        Client client = clientDAO.getById(1L);
        assertNotNull(client);
        assertEquals(1L, client.getId().longValue());
    }

    @Test
    public void testGetAll() {
        List<Client> clients = (List<Client>) clientDAO.getAll();
        assertNotNull(clients);
        assertTrue(clients.size() > 0);
    }

    @Test
    public void testUpdate() {
        Client client = clientDAO.getByPhone("89123456701");
        Long id = client.getId().longValue();
        client.setName("Test2");
        clientDAO.update(client);
        Client updatedClient = clientDAO.getById(id);
        assertEquals("Test2", updatedClient.getName());
    }

    @Test
    public void testDelete() {
        Client client = clientDAO.getByPhone("89123456701");
        Long id = client.getId().longValue();
        clientDAO.delete(client);
        Client deletedClient = clientDAO.getById(id);
        assertNull(deletedClient);
    }

    @Test
    public void testGetByFilters() {
        List<Client> clients = clientDAO.getByFilters("Test2");
        assertNotNull(clients);
        assertTrue(clients.size() > 0);
    }

    @Test
    public void testGetByPhone() {
        Client client = clientDAO.getByPhone("89123456701");
        assertNotNull(client);
        assertEquals("89123456701", client.getPhone());
    }
}
