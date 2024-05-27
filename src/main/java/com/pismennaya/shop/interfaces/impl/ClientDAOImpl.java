package com.pismennaya.shop.interfaces.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.pismennaya.shop.interfaces.ClientDAO;
import com.pismennaya.shop.models.Client;

import java.util.List;

@Repository
@Transactional
public class ClientDAOImpl extends CommonDAOImpl<Client, Long> implements ClientDAO{
    public ClientDAOImpl() {
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
