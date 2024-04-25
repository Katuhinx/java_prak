package com.pismennaya.shop.controllers;

import com.pismennaya.shop.interfaces.ProductDAO;
import com.pismennaya.shop.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeController {

    private ProductDAO productDAO;

    @GetMapping("/")
    public String home(Model model) {
        //List<Product> products = (List<Product>) productDAO.getAll();
        model.addAttribute("title", "Главная страница");
        return "index";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("title", "Корзина");
        return "cart";
    }
}