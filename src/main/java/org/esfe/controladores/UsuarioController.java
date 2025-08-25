package org.esfe.controladores;

import jakarta.servlet.http.HttpSession;
import org.esfe.modelos.Usuario;
import org.esfe.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    // Mostrar formulario de registro
    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/register";
    }

    // Procesar registro
    @PostMapping("/register")
    public String registrar(@ModelAttribute Usuario usuario, Model model) {
        Usuario nuevo = usuarioService.registrar(usuario);
        if (nuevo != null) {
            return "redirect:/usuario/login?success"; // redirige al login con query param
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("error", "Error al registrar el usuario. Verifica los datos.");
        return "usuario/register";
    }

    // Mostrar formulario de login
    @GetMapping("/login")
    public String mostrarLogin(Model model,
                               @RequestParam(value = "success", required = false) String success) {
        model.addAttribute("usuario", new Usuario());

        if (success != null) {
            model.addAttribute("mensaje", "Registro exitoso. Ahora puedes iniciar sesión.");
        }
        return "usuario/login";
    }

    // Procesar login
    @PostMapping("/login")
    public String login(@ModelAttribute Usuario usuario, HttpSession session, Model model) {
        Usuario u = usuarioService.login(usuario.getEmail(), usuario.getPassword());

        if (u != null) {
            // 👇 se guarda como usuarioActivo para que el layout lo pueda leer
            session.setAttribute("usuarioActivo", u);
            return "redirect:/"; // va al HomeController
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("error", "Correo o contraseña incorrectos.");
        return "usuario/login";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuario/login";
    }
}
