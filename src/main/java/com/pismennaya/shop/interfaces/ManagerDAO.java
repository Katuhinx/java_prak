package com.pismennaya.shop.interfaces;

import com.pismennaya.shop.models.Manager;

public interface ManagerDAO extends CommonDAO<Manager, Long>{
    Manager getByLogin(String login, String password);
}
