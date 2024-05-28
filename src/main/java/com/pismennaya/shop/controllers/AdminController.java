package com.pismennaya.shop.controllers;

import com.pismennaya.shop.interfaces.ClientDAO;
import com.pismennaya.shop.interfaces.ProductDAO;
import com.pismennaya.shop.interfaces.impl.ClientDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();
    private ProductDAO productDAO;

    @GetMapping("/")
    public String greet() {
        return "Admin";
    }

    @GetMapping("/clients")
    public String greetParam(@PathVariable("name") String name) {

        return "Hello, " + name;
    }

    @GetMapping("/full")
    public String fullGreeting(@RequestParam("name") String name,
                               @RequestParam("surname") String surname) {
        return "Nice to meet you, " + name + " " + surname;
    }
}
