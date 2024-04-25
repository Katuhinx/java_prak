package com.pismennaya.shop.controllers;

import com.pismennaya.shop.interfaces.ProductDAO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductDAO productDAO;

    @GetMapping
    public String greet() {
        //List<Product> products = productDAO.getAll();
        return "Hello";
    }

    @GetMapping("/{name}")
    public String greetParam(@PathVariable("name") String name) {

        return "Hello, " + name;
    }

    @GetMapping("/full")
    public String fullGreeting(@RequestParam("name") String name,
                               @RequestParam("surname") String surname) {
        return "Nice to meet you, " + name + " " + surname;
    }
}
