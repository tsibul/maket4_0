package ru.maket.maket4_0.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainPageController {

    public MainPageController() {
            }

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("activePage", "order");
        return "index";
    }

    @GetMapping("/maket")
    public String maketPage(Model model) {
        model.addAttribute("activePage", "maket");
        return "maket";
    }
    @GetMapping("/film")
    public String filmPage(Model model) {
        model.addAttribute("activePage", "film");
        return "film";
    }
    @GetMapping("/template")
    public String templatePage(Model model) {
        model.addAttribute("activePage", "film");
        return "template";
    }
    @GetMapping("/dictionary")
    public String dictPage(Model model) {
        model.addAttribute("activePage", "dict");
        return "index";
    }

}
