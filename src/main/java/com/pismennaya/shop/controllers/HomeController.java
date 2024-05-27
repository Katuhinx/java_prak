package com.pismennaya.shop.controllers;

import com.pismennaya.shop.interfaces.ClientDAO;
import com.pismennaya.shop.interfaces.OrderDAO;
import com.pismennaya.shop.interfaces.ProductDAO;
import com.pismennaya.shop.interfaces.impl.ClientDAOImpl;
import com.pismennaya.shop.interfaces.impl.OrderDAOImpl;
import com.pismennaya.shop.interfaces.impl.ProductDAOImpl;
import com.pismennaya.shop.models.Client;
import com.pismennaya.shop.models.Order;
import com.pismennaya.shop.models.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private final ProductDAO productDAO = new ProductDAOImpl();
    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();
    @Autowired
    private final OrderDAO orderDAO = new OrderDAOImpl();

    @GetMapping("/")
    public String home(Model model) {
        List<Product> products = (List<Product>) productDAO.getAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) {
        Product product = productDAO.getById(id);
        model.addAttribute("title", product.getName());
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        List<Long> cart = (List<Long>) session.getAttribute("cart");
        List<Product> products = new ArrayList<>();
        int total_price = 0;

        if (cart != null) {
            for(Long id : cart) {
                Product product = productDAO.getById(id);
                products.add(product);
                total_price += product.getPrice();
            }
        }

        model.addAttribute("title", "Корзина");
        model.addAttribute("products", products);
        model.addAttribute("total_price", total_price);

        return "cart";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session) {
        List<Long> cart = (List<Long>) session.getAttribute("cart");
        List<Product> products = new ArrayList<>();
        int total_price = 0;

        if (cart != null) {
            for (Long id : cart) {
                Product product = productDAO.getById(id);
                products.add(product);
                total_price += product.getPrice();
            }
        }

        model.addAttribute("title", "Оформление заказа");
        model.addAttribute("products", products);
        model.addAttribute("total_price", total_price);
        return "order";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("id") Long id, Model model, HttpSession session) {
        List<Long> cart = (List<Long>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        cart.add(id);
        session.setAttribute("cart", cart);
        return "redirect:/product/" + id;
    }

    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("id") Long id, Model model, HttpSession session) {
        List<Long> cart = (List<Long>) session.getAttribute("cart");

        if (cart != null) {
            cart.remove(id);
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/createOrder")
    public void createOrder(Model model, HttpSession session) {
        List<Order> orders = (List<Order>) orderDAO.getAll();
        //List<Long> cart = (List<Long>) session.getAttribute("cart");

        //if (cart != null) {

            //Client client = new Client("Петр", "Иванов", "89123456780", "client2@example.com" );
            //clientDAO.save(client);
            //orderDAO.save(new Order(client, LocalDate.now(), "Адрес 1", "preparation"));
        //}

        //return "redirect:/";
    }
}