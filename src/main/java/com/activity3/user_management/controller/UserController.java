package com.activity3.user_management.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.activity3.user_management.model.User;
import com.activity3.user_management.repository.UserRepository;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/")
    public String home(Model model) {
        log.info("Entering home().");
        log.info("This was an automatic deployment!");
        model.addAttribute("users", userRepository.findAll());
        log.info("Exiting home().");
        return "user-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        log.info("Entering showAddForm().");
        model.addAttribute("user", new User());
        log.info("Exiting showAddForm().");
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        log.info("Entering addUser().");
        userRepository.save(user);
        log.info("Exiting addUser().");
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        log.info("Entering showEditForm().");
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            log.info("Exiting showEditForm().");
            return "edit-user";
        } else {
            log.warn("Unable to find user, redirecting to home and exiting showEditForm().");
            return "redirect:/";
        }
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        log.info("Entering editUser().");
        user.setId(id);
        userRepository.save(user);
        log.info("Exiting editUser().");
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        log.info("Entering deleteUser().");
        userRepository.deleteById(id);
        log.info("Exiting deleteUser().");
        return "redirect:/";
    }
}
