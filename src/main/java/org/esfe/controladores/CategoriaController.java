package org.esfe.controladores;

import org.esfe.modelos.Categorias;
import org.esfe.modelos.Tareas;
import org.esfe.modelos.Usuario;
import org.esfe.repositorios.ICategoriaRepository;
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
    @Autowired
    private ICategoriaRepository categoriaRepository;

    // ✅ Listar categorías del usuario logueado
    @GetMapping
    public String index(Model model, HttpSession session) {
        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/login"; // redirigir si no hay sesión
        }

        List<Categorias> categorias = categoriaRepository.findByUsuario(usuarioActivo);
        model.addAttribute("categorias", categorias);
        return "categoria/index";
    }

    // ✅ Mostrar formulario de creación
    @GetMapping("/create")
    public String create(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/usuario/login";
        }

        model.addAttribute("categoria", new Categorias());
        return "categoria/create";
    }

    // ✅ Guardar categoría nueva
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

        redirectAttributes.addFlashAttribute("msg", "✅ Categoría guardada con éxito");
        return "redirect:/categoria";
    }

    // ✅ Formulario de edición
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
        return "categorias/edit";
    }

    // ✅ Actualizar categoría
    @PostMapping("/update")
    public String update(@ModelAttribute("categoria") Categorias categoria,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/usuario/login";
        }

        // Reasignar usuario antes de guardar
        categoria.setUsuario(usuario);
        categoriaService.crearOEditar(categoria);

        redirectAttributes.addFlashAttribute("msg", "✅ Categoría actualizada con éxito");
        return "redirect:/categoria";
    }

    // ✅ Ver detalles
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
        return "categorias/details";
    }

    // ✅ Confirmar borrado
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
        return "categorias/delete";
    }

    // ✅ Eliminar categoría
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
        redirectAttributes.addFlashAttribute("msg", "🗑️ Categoría eliminada con éxito");
        return "redirect:/categoria";
    }
}
