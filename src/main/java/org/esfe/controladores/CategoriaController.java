package org.esfe.controladores;

import org.esfe.modelos.Categorias;
import org.esfe.modelos.Usuario;
import org.esfe.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping
    public String index(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            return "redirect:/usuario/login"; // ⚡ usar redirect para que se cargue bien el login
        }

        List<Categorias> categorias = categoriaService.obtenerPorUsuario(usuario);
        model.addAttribute("categorias", categorias);
        return "categoria/index";
    }

    @GetMapping("/create")
    public String create(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/usuario/login";
        }
        model.addAttribute("categoria", new Categorias());
        return "categoria/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("categoria") Categorias categoria,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/usuario/login";
        }

        categoria.setUsuario(usuario);
        categoriaService.crearOEditar(categoria);

        redirectAttributes.addFlashAttribute("msg", "Categoría guardada con éxito");
        return "redirect:/categoria";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/usuario/login";
        }

        Categorias categoria = categoriaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con id: " + id));

        if (!categoria.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/categoria";
        }

        model.addAttribute("categoria", categoria);
        return "categoria/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("categoria") Categorias categoria,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/usuario/login";
        }

        categoria.setUsuario(usuario);
        categoriaService.crearOEditar(categoria);

        redirectAttributes.addFlashAttribute("msg", "Categoría actualizada con éxito");
        return "redirect:/categoria";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/usuario/login";
        }

        Categorias categoria = categoriaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con id: " + id));

        if (!categoria.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/categoria";
        }

        model.addAttribute("categoria", categoria);
        return "categoria/details";
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Integer id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/usuario/login";
        }

        Categorias categoria = categoriaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con id: " + id));

        if (!categoria.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/categoria";
        }

        model.addAttribute("categoria", categoria);
        return "categoria/delete";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/usuario/login";
        }

        Categorias categoria = categoriaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con id: " + id));

        if (!categoria.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/categoria";
        }

        categoriaService.eliminarPorId(id);
        redirectAttributes.addFlashAttribute("msg", "Categoría eliminada con éxito");
        return "redirect:/categoria";
    }
}
