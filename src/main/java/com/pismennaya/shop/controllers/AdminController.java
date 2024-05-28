package com.pismennaya.shop.controllers;

import com.pismennaya.shop.interfaces.ClientDAO;
import com.pismennaya.shop.interfaces.ManagerDAO;
import com.pismennaya.shop.interfaces.ProductDAO;
import com.pismennaya.shop.interfaces.impl.ClientDAOImpl;
import com.pismennaya.shop.interfaces.impl.ManagerDAOImpl;
import com.pismennaya.shop.models.Manager;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private final ManagerDAO managerDAO = new ManagerDAOImpl();
    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();

    @GetMapping("/auth")
    public String auth(@RequestParam(value = "error", defaultValue = "0") int error, Model model) {
        if (error == 1) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        model.addAttribute("title", "Вход");
        return "auth";
    }

    @PostMapping("/authManager")
    public String authManager(@RequestParam("login") String login, @RequestParam("password") String password, Model model, HttpSession session) {
        Manager manager = managerDAO.getByLogin(login, password);

        if (manager != null) {
            session.setAttribute("auth", manager.getId());
            return "redirect:/";
        }

        return "redirect:/admin/auth?error=1";
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
