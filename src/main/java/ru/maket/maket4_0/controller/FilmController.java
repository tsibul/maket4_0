package ru.maket.maket4_0.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilmController {

    public FilmController(){
    }

    @GetMapping("/film")
    public String filmPage(Model model) {
        model.addAttribute("activePage", "film");
        return "film";
    }

}
