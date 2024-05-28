package com.pismennaya.shop.interfaces;

import com.pismennaya.shop.models.Client;
import java.util.List;

public interface ClientDAO extends CommonDAO<Client, Long> {
    List<Client> getByFilters(String data);
}
