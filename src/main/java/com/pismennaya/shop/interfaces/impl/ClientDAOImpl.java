package com.pismennaya.shop.interfaces.impl;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.pismennaya.shop.interfaces.ClientDAO;
import com.pismennaya.shop.models.Client;

import java.util.List;

@Repository
public class ClientDAOImpl extends CommonDAOImpl<Client, Long> implements ClientDAO{
    public ClientDAOImpl(){
        super(Client.class);
    }

    @Override
    public List<Client> getAllClients() {
        try (Session session = sessionFactory.openSession()) {
            Query<Client> query = session.createQuery("FROM Client", Client.class);
            return query.getResultList();
        }
    }
}
