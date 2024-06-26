package com.pismennaya.shop.controllers;

import com.pismennaya.shop.interfaces.ClientDAO;
import com.pismennaya.shop.interfaces.OrderDAO;
import com.pismennaya.shop.interfaces.OrderProductDAO;
import com.pismennaya.shop.interfaces.ProductDAO;
import com.pismennaya.shop.interfaces.impl.ClientDAOImpl;
import com.pismennaya.shop.interfaces.impl.OrderDAOImpl;
import com.pismennaya.shop.interfaces.impl.OrderProductDAOImpl;
import com.pismennaya.shop.interfaces.impl.ProductDAOImpl;
import com.pismennaya.shop.models.Client;
import com.pismennaya.shop.models.Order;
import com.pismennaya.shop.models.OrderProduct;
import com.pismennaya.shop.models.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class HomeController {

    @Autowired
    private final ProductDAO productDAO = new ProductDAOImpl();
    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();
    @Autowired
    private final OrderDAO orderDAO = new OrderDAOImpl();
    @Autowired
    private final OrderProductDAO orderProductDAO = new OrderProductDAOImpl();

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

    @GetMapping("/success-order")
    public String successOrder(Model model, HttpSession session) {
        model.addAttribute("title", "Заказ успешно оформлен!");
        return "success_order";
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

    @PostMapping("/createOrder")
    public String createOrder(
        @RequestParam("name") String name,
        @RequestParam("surname") String surname,
        @RequestParam("phone") String phone,
        @RequestParam("email") String email,
        @RequestParam("address") String address,
        @RequestParam("delivery_date") String delivery_date,
        Model model, 
        HttpSession session
    ) {
        List<Long> cart = (List<Long>) session.getAttribute("cart");

        if (cart != null) {
            Client client = clientDAO.getByPhone(phone);

            if (client == null) {
                client = new Client();
                client.setName(name);
                client.setSurname(surname);
                client.setPhone(phone);
                client.setEmail(email);
                clientDAO.save(client);

                client = clientDAO.getByPhone(phone);
            }

            Order order = new Order();
            Set<OrderProduct> order_products = new HashSet<>();

            for(Long id : cart) {
                Product product = productDAO.getById(id);
                OrderProduct order_product = new OrderProduct();
                order_product.setOrder(order);
                order_product.setProduct(product);
                order_product.setQuantity(1);
                order_products.add(order_product);
            }

            order.setClient(client);
            order.setOrder_date(Date.valueOf(LocalDate.now()));
            order.setDelivery_date(Date.valueOf(delivery_date));
            order.setAddress(address);
            order.setStatus("В сборке");
            order.setOrderProducts(order_products);

            orderDAO.save(order);
            orderProductDAO.saveCollection(order_products);

            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        return "redirect:/success-order";
    }
}