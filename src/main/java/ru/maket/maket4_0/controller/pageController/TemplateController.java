package ru.maket.maket4_0.controller.pageController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    public TemplateController(){
    }

    @GetMapping("/template")
    public String templatePage(Model model) {
        model.addAttribute("activePage", "film");
        return "template";
    }

}
