package org.esfe.controladores;

import org.esfe.modelos.Usuario;
import org.esfe.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    // Página de registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro"; // Vista: src/main/resources/templates/auth/registro.html
    }

    @PostMapping("/registro")
    public String registrar(@ModelAttribute Usuario usuario, Model model) {
        Usuario nuevo = usuarioService.registrar(usuario);
        if (nuevo != null) {
            model.addAttribute("mensaje", "Registro exitoso. Ahora puedes iniciar sesión.");
            return "redirect:/auth/login";
        }
        model.addAttribute("error", "Error al registrar el usuario.");
        return "auth/registro";
    }

    // Página de login
    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/login"; // Vista: src/main/resources/templates/auth/login.html
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Usuario usuario, Model model) {
        Usuario u = usuarioService.login(usuario.getEmail(), usuario.getPassword());
        if (u != null) {
            model.addAttribute("usuarioActivo", u);
            return "redirect:/"; // Redirige a la página principal
        }
        model.addAttribute("error", "Correo o contraseña incorrectos.");
        return "auth/login";
    }
}