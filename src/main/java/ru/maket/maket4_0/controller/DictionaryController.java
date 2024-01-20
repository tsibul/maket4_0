package ru.maket.maket4_0.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DictionaryController {

    public DictionaryController(){

    }

    @GetMapping("/dictionary")
    public String dictPage(Model model) {
        model.addAttribute("activePage", "dict");
        return "dictionary";
    }

}
