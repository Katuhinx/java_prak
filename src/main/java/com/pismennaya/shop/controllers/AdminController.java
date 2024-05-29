package com.pismennaya.shop.controllers;

import com.pismennaya.shop.interfaces.*;
import com.pismennaya.shop.interfaces.impl.*;
import com.pismennaya.shop.models.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private final OrderProductDAO orderProductDAO = new OrderProductDAOImpl();

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
    public String product(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            Product product = productDAO.getById(id);
            List<Category> categories = (List<Category>) categoryDAO.getAll();

            model.addAttribute("title", product.getName());
            model.addAttribute("product", product);
            model.addAttribute("categories", categories);
            return "admin_product";
        } else {
            return "redirect:/admin/auth";
        }
    }

    @GetMapping("/product/new")
    public String productNew(Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            List<Category> categories = (List<Category>) categoryDAO.getAll();

            model.addAttribute("title", "Новый товар");
            model.addAttribute("categories", categories);
            return "admin_new_product";
        } else {
            return "redirect:/admin/auth";
        }
    }

    @GetMapping("/category/{id}")
    public String category(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            Category category = categoryDAO.getById(id);

            model.addAttribute("title", category.getName());
            model.addAttribute("category", category);
            return "admin_category";
        } else {
            return "redirect:/admin/auth";
        }
    }

    @GetMapping("/category/new")
    public String categoryNew(Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            model.addAttribute("title", "Новая категория");

            return "admin_new_category";
        } else {
            return "redirect:/admin/auth";
        }
    }

    @GetMapping("/client/{id}")
    public String client(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            Client client = clientDAO.getById(id);
            List<Order> orders = (List<Order>) orderDAO.getByClient(id);
            List<String> addresses = new ArrayList<String>();

            for (Order order : orders) {
                addresses.add(order.getAddress());
            }

            model.addAttribute("title", client.getName());
            model.addAttribute("client", client);
            model.addAttribute("addresses", addresses);
            return "admin_client";
        } else {
            return "redirect:/admin/auth";
        }
    }

    @GetMapping("/order/{id}")
    public String order(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            Order order = orderDAO.getById(id);
            List<OrderProduct> order_products = orderProductDAO.getByOrderId(id);
            int total_sum = 0;

            for (OrderProduct order_product : order_products) {
                total_sum += order_product.getProduct().getPrice();
            }

            model.addAttribute("title", "Заказ " + id);
            model.addAttribute("order", order);
            model.addAttribute("order_products", order_products);
            model.addAttribute("total_sum", total_sum);
            return "admin_order";
        } else {
            return "redirect:/admin/auth";
        }
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
        @RequestParam(value = "id", defaultValue = "null") String id,
        @RequestParam("name") String name,
        @RequestParam("price") int price,
        @RequestParam("category") Long category,
        @RequestParam("description") String description,
        @RequestParam("quantity") int quantity,
        @RequestParam("country") String country,
        @RequestParam("production") String production,
        @RequestParam("color") String color,
        @RequestParam("material") String material,
        @RequestParam("warranty") int warranty,
        @RequestParam("weight") float weight,
        @RequestParam("volume") float volume,
        @RequestParam("size") String size,
        @RequestParam("power") int power,
        @RequestParam("diagonal") float diagonal,
        @RequestParam("resolution") String resolution,
        @RequestParam("chamber") int chamber,
        @RequestParam("steam_suply") int steam_suply,
        Model model, 
        HttpSession session
    ) {
        if (session.getAttribute("manager") != null) {
            Product product = new Product();

            product.setId(id.equals("null") ? null : Long.parseLong(id));
            product.setName(name);
            product.setPrice(price);
            product.setCategory(categoryDAO.getById(category));
            product.setDescription(description);
            product.setQuantity(quantity);
            product.setCountry(country.isEmpty() ? null : country);
            product.setProduction(production.isEmpty() ? null : production);
            product.setColor(color.isEmpty() ? null : color);
            product.setMaterial(material.isEmpty() ? null : material);
            product.setWarranty(warranty);
            product.setWeight(weight);
            product.setVolume(volume);
            product.setSize(size.isEmpty() ? null : size);
            product.setPower(power);
            product.setDiagonal(diagonal);
            product.setResolution(resolution.isEmpty() ? null : resolution);
            product.setChamber(chamber);
            product.setSteam_suply(steam_suply);

            productDAO.save(product);

            if (id.equals("null")) {
                return "redirect:/admin/products";
            } else {
                return "redirect:/admin/product/" + id;
            }
        } else {
            return "redirect:/admin/auth";
        }
    }

    @GetMapping("/removeProduct/{id}")
    public String removeProduct(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            productDAO.deleteById(id);
            return "redirect:/admin/products";
        } else {
            return "redirect:/admin/auth";
        }
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@RequestParam(value = "id", defaultValue = "null") String id, @RequestParam("name") String name, Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            Category category = new Category();

            category.setId(id.equals("null") ? null : Long.parseLong(id));
            category.setName(name);

            categoryDAO.save(category);

            if (id.equals("null")) {
                return "redirect:/admin/categories";
            } else {
                return "redirect:/admin/category/" + id;
            }
        } else {
            return "redirect:/admin/auth";
        }
    }

    @GetMapping("/removeCategory/{id}")
    public String removeCategory(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("manager") != null) {
            categoryDAO.deleteById(id);
            return "redirect:/admin/categories";
        } else {
            return "redirect:/admin/auth";
        }
    }

    @PostMapping("/saveClient")
    public String saveClient(
        @RequestParam("id") Long id,
        @RequestParam("name") String name,
        @RequestParam("surname") String surname,
        @RequestParam("phone") String phone,
        @RequestParam("email") String email,
        Model model,
        HttpSession session
    ) {
        if (session.getAttribute("manager") != null) {
            Client client = new Client();

            client.setId(id);
            client.setName(name);
            client.setSurname(surname);
            client.setPhone(phone);
            client.setEmail(email);

            clientDAO.save(client);
            return "redirect:/admin/client/" + id;
        } else {
            return "redirect:/admin/auth";
        }
    }

    @PostMapping("/saveOrder")
    public String saveClient(
        @RequestParam("id") Long id,
        @RequestParam("client_id") Long client_id,
        @RequestParam("delivery_date") String delivery_date,
        @RequestParam("address") String address,
        @RequestParam("status") String status,
        Model model,
        HttpSession session
    ) {
        if (session.getAttribute("manager") != null) {
            Order order = new Order();
            Client client = clientDAO.getById(client_id);

            switch (status) {
                case "1":
                    status = "В сборке";
                    break;
                case "2":
                    status = "Доставляется";
                    break;
                case "3":
                    status = "Доставлен";
                    break;
            }

            order.setId(id);
            order.setDelivery_date(Date.valueOf(delivery_date));
            order.setAddress(address);
            order.setStatus(status);
            order.setClient(client);

            orderDAO.save(order);
            return "redirect:/admin/order/" + id;
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
