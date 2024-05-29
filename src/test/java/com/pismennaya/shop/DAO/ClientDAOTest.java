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
    public void testGetById() {
        Client client = clientDAO.getById(1);
        Assert.assertNotNull(client);
        Assert.assertEquals(1, client.getId().longValue());
    }

    @Test
    public void testGetAll() {
        List<Client> clients = clientDAO.getAll();
        Assert.assertNotNull(clients);
        Assert.assertTrue(clients.size() > 0);
    }

    @Test
    public void testSave() {
        Client newClient = new Client();
        newClient.setName("Test1");
        newClient.setPhone("89123456701");
        clientDAO.save(newClient);

        Client savedClient = clientDAO.getById(newClient.getId());
        Assert.assertNotNull(savedClient);
        Assert.assertEquals(newClient.getName(), savedClient.getName());
        Assert.assertEquals(newClient.getPhone(), savedClient.getPhone());
    }

    @Test
    public void testUpdate() {
        Client client = clientDAO.getById(1);
        client.setName("Test2");
        clientDAO.update(client);
        Client updatedClient = clientDAO.getById(1);
        Assert.assertEquals("Test2", updatedClient.getName());
    }

    @Test
    public void testDelete() {
        Client client = clientDAO.getById(1);
        clientDAO.delete(client);
        Client deletedClient = clientDAO.getById(1);
        Assert.assertNull(deletedClient);
    }

    @Test
    public void testGetByFilters() {
        List<Client> clients = clientDAO.getByFilters("Test1");
        Assert.assertNotNull(clients);
        Assert.assertTrue(clients.size() > 0);

    }

    @Test
    public void testGetByPhone() {
        Client client = clientDAO.getByPhone("89123456701");
        Assert.assertNotNull(client);
        Assert.assertEquals("89123456701", client.getPhone());
    }
}
