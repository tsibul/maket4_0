package ru.maket.maket4_0.exceptions;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
public class CustomErrorController {

    @RequestMapping("/customError")
    public String handleError(HttpServletRequest request, Model model) {
        // Получаем информацию об ошибке из запроса
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // Предоставляем информацию об ошибке пользователю
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorMessage", "Страница не найдена");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorMessage", "Внутренняя ошибка сервера");
            } else {
                model.addAttribute("errorMessage", "Произошла ошибка");
            }
        }
        return "error"; // Возвращаем пользовательскую страницу ошибки
    }
}

