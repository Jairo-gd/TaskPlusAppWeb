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
                               @RequestParam(value = "success", required = false) String success,
                               @RequestParam(value = "resetSuccess", required = false) String resetSuccess) {
        model.addAttribute("usuario", new Usuario());

        if (success != null) {
            model.addAttribute("mensaje", "Registro exitoso. Ahora puedes iniciar sesión.");
        }

        if (resetSuccess != null) {
            model.addAttribute("mensaje", "Contraseña restablecida correctamente. Inicia sesión nuevamente.");
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

    // Mostrar formulario de reset password
    @GetMapping("/Reset")
    public String mostrarFormularioReset(HttpSession session, Model model) {
        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/usuario/login"; // corregido: ruta debe ser /usuario/login
        }
        model.addAttribute("usuarioActivo", usuarioActivo);
        return "usuario/Reset"; // corregido: debe estar dentro de carpeta usuario/
    }

    // Procesar reset password
    @PostMapping("/Reset")
    public String procesarResetPassword(@RequestParam("correo") String correo,
                                        @RequestParam("password") String nuevaPassword,
                                        HttpSession session) {

        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/usuario/login";
        }

        usuarioActivo.setPassword(nuevaPassword);
        usuarioService.registrar(usuarioActivo);

        session.setAttribute("usuarioActivo", usuarioActivo);

        return "redirect:/?resetSuccess"; // 👈 param para mostrar mensaje
    }

}
