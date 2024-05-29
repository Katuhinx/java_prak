package com.pismennaya.shop.controllers;

import com.pismennaya.shop.interfaces.*;
import com.pismennaya.shop.interfaces.impl.*;
import com.pismennaya.shop.models.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private final ManagerDAO managerDAO = new ManagerDAOImpl();
    @Autowired
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    @Autowired
    private final ProductDAO productDAO = new ProductDAOImpl();
    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();
    @Autowired
    private final OrderDAO orderDAO = new OrderDAOImpl();

    @GetMapping("/auth")
    public String auth(@RequestParam(value = "error", defaultValue = "0") int error, Model model) {
        if (error == 1) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        model.addAttribute("title", "Вход");
        return "auth";
    }

    @GetMapping("/products")
    public String products(@RequestParam(value = "name", defaultValue = "null") String name, @RequestParam(value = "category", defaultValue = "null") String category, Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            List<Category> categories = (List<Category>) categoryDAO.getAll();
            List<Product> products = (List<Product>) productDAO.getByFilters(name, category);

            model.addAttribute("title", "Товары");
            model.addAttribute("categories", categories);
            model.addAttribute("products", products);
            return "admin_products";
        } else {
            return "redirect:/admin/auth";
        }
    }

    @GetMapping("/clients")
    public String clients(@RequestParam(value = "data", defaultValue = "null") String data, Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            List<Client> clients = (List<Client>) clientDAO.getByFilters(data);

            model.addAttribute("title", "Клиенты");
            model.addAttribute("clients", clients);
            return "admin_clients";
        } else {
            return "redirect:/admin/auth";
        }
    }

    @GetMapping("/orders")
    public String clients(@RequestParam(value = "id", defaultValue = "null") String id, @RequestParam(value = "order_date", defaultValue = "null") String order_date, @RequestParam(value = "address", defaultValue = "null") String address, Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            List<Order> orders = (List<Order>) orderDAO.getByFilters(id, order_date, address);

            model.addAttribute("title", "Заказы");
            model.addAttribute("orders", orders);
            return "admin_orders";
        } else {
            return "redirect:/admin/auth";
        }
    }

    @GetMapping("/categories")
    public String categories(Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            List<Category> categories = (List<Category>) categoryDAO.getAll();

            model.addAttribute("title", "Категории");
            model.addAttribute("categories", categories);
            return "admin_categories";
        } else {
            return "redirect:/admin/auth";
        }
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) {
        Product product = productDAO.getById(id);
        List<Category> categories = (List<Category>) categoryDAO.getAll();

        model.addAttribute("title", product.getName());
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "admin_product";
    }

    @PostMapping("/authManager")
    public String authManager(@RequestParam("login") String login, @RequestParam("password") String password, Model model, HttpSession session) {
        Manager manager = managerDAO.getByLogin(login, password);

        if (manager != null) {
            session.setAttribute("manager", manager.getId());
            return "redirect:/admin/products";
        }

        return "redirect:/admin/auth?error=1";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(
        @RequestParam("id") int id,
        @RequestParam("name") String name,
        @RequestParam("price") int price,
        @RequestParam("category") int category,
        @RequestParam("description") String description,
        @RequestParam("quantity") int quantity,
        @RequestParam("country") String country,
        @RequestParam("production") String production,
        @RequestParam("color") String color,
        @RequestParam("material") String material,
        @RequestParam("warranty") String warranty,
        @RequestParam("weight") float weight,
        @RequestParam("volume") float volume,
        @RequestParam("size") String size,
        @RequestParam("power") int power,
        @RequestParam("diagonal") float diagonal,
        @RequestParam("resolution") String resolution,
        @RequestParam("chamber") String chamber,
        @RequestParam("steam_suply") String steam_suply,
        Model model, 
        HttpSession session
    ) {
        if (session.getAttribute("manager") != null) {
            productDAO.update(null);

            
            return "redirect:/admin/product/" + id;
        } else {
            return "redirect:/admin/auth";
        }
    }



/*
    @GetMapping("/clients")
    public String greetParam(@PathVariable("name") String name) {

        return "Hello, " + name;
    }

    @GetMapping("/full")
    public String fullGreeting(@RequestParam("name") String name,
                               @RequestParam("surname") String surname) {
        return "Nice to meet you, " + name + " " + surname;
    }
    */

}
