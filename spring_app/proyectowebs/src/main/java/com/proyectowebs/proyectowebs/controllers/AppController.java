package com.proyectowebs.proyectowebs.controllers;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.proyectowebs.proyectowebs.services.AppService;

@Controller
public class AppController {
    private final AppService appService;
    public AppController(AppService appService) {
        this.appService = appService;
    }
    
    @GetMapping("/")
    public String indexRoute(Model model) {
        List<Map<String, String>> modelData = appService.getActividadesData(5);
        model.addAttribute("data", modelData);
        return "index";
    }

    
    @PostMapping("/add-act")
    public String postActRoute(
        @RequestParam("comuna") int comuna,
        @RequestParam("sector") String sector,
        @RequestParam("nombre") String nombre,
        @RequestParam("mail") String mail,
        @RequestParam("celular") String celular,
        @RequestParam("dia_hora_inicio") LocalDateTime dia_hora_inicio,
        @RequestParam("dia_hora_termino") LocalDateTime dia_hora_termino,
        @RequestParam("descripcion") String descripcion,
        @RequestParam("pic") MultipartFile ActImg) throws Exception {

        appService.handlePostRequest(
            comuna,
            sector,
            nombre,
            mail,
            celular,
            dia_hora_inicio,
            dia_hora_termino,
            descripcion,
            ActImg
        );

        return "redirect:/"; //redirects to indexRoute
    }

    @GetMapping("/stats")
    public String statsRoute() {
        return "stats";
    }

    @GetMapping("/list")
    public String listRoute(Model model) {
        List<Map<String, String>> modelData = appService.getActividadesData(500);
        model.addAttribute("data", modelData);
        return "list";
    }

}
