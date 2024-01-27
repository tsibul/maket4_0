package ru.maket.maket4_0.controller.pageController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    public MainPageController() {
            }

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("activePage", "order");
        return "index";
    }

}
