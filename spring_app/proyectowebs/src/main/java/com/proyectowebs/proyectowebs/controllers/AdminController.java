package com.proyectowebs.proyectowebs.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyectowebs.proyectowebs.models.User;
import com.proyectowebs.proyectowebs.models.UserRepository;
import com.proyectowebs.proyectowebs.services.AppService;
import com.proyectowebs.proyectowebs.models.Log;
import com.proyectowebs.proyectowebs.models.LogRepository;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
// @PreAuthorize("hasAuthority('ROLE_ADMIN')") --> Es lo mismo!
public class AdminController {

    private final UserRepository userRepository;
    private final LogRepository logRepository;
    private final AppService appService;
    

    public AdminController(UserRepository userRepository, LogRepository logRepository, AppService appService) {
        this.userRepository = userRepository;
        this.logRepository = logRepository;
        this.appService = appService;
    }

    @GetMapping("/admin-fotos")
    public String listUsers(Model model) {
        List<Map<String, String>> modelData = appService.getActividadesData(5);
        model.addAttribute("data", modelData);
        return "admin-fotos";
    }

    @GetMapping("/log")
    public String log(Model model) {
        List<Log> logs = logRepository.findAll();
        model.addAttribute("logs", logs);
        return "logs";
    }
}
