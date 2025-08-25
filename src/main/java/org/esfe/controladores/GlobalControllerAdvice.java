package org.esfe.controladores;

import jakarta.servlet.http.HttpSession;
import org.esfe.modelos.Usuario;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void agregarUsuarioAlModelo(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            model.addAttribute("usuarioSesion", usuario);
        }
    }
}
