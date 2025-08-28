package org.esfe.controladores;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String index(HttpSession session) {
        if (session.getAttribute("usuarioActivo") == null) {
            return "redirect:/usuario/login";
        }
        return "home/index";
    }
}
