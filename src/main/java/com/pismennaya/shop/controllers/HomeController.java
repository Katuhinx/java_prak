package com.pismennaya.shop.controllers;

import com.pismennaya.shop.interfaces.ClientDAO;
import com.pismennaya.shop.interfaces.ProductDAO;
import com.pismennaya.shop.interfaces.impl.ClientDAOImpl;
import com.pismennaya.shop.interfaces.impl.ProductDAOImpl;
import com.pismennaya.shop.models.Product;
import com.pismennaya.shop.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();

    @GetMapping("/")
    public String home(Model model) {
        List<Product> products = (List<Product>) productDAO.getAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) {
        Product product = productDAO.getById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("title", "Корзина");
        return "cart";
    }

    //List<Client> clients = (List<Client>) clientDAO.getAll();

            /*for(int i = 2; i <= 10; i++) {
            Product product = new Product(i, "Продукт " + i, "Производитель " + i, "Россия", 1000 * i, 100, "Описание Продукт " + i);
            productDAO.save(product);
        }*/
}