package org.esfe.controladores;

import org.esfe.modelos.Tareas;
import org.esfe.modelos.Categorias;
import org.esfe.modelos.Usuario;
import org.esfe.repositorios.ITareaRepository;
import org.esfe.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/tarea")
public class TareaController {

    @Autowired
    private ITareaRepository tareaRepository;

    @Autowired
    private ICategoriaService categoriaService;

    // LISTAR SOLO LAS DEL USUARIO
    @GetMapping
    public String index(Model model, HttpSession session) {
        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/login"; // redirigir si no hay sesión
        }

        List<Tareas> tareas = tareaRepository.findByUsuario(usuarioActivo);
        model.addAttribute("tareas", tareas);
        return "tarea/index";
    }

    // CREAR - GET
    @GetMapping("/create")
    public String crear(Model model, HttpSession session) {
        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/login";
        }

        model.addAttribute("tarea", new Tareas());
        model.addAttribute("categorias", categoriaService.obtenerPorUsuario(usuarioActivo)); // ✅ categorías del usuario
        return "tarea/create";
    }

    // CREAR - POST
    @PostMapping("/create")
    public String createPost(@ModelAttribute Tareas tarea,
                             HttpSession session) {
        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/login";
        }

        // Buscar categoría desde el ID que viene en el form
        if (tarea.getCategoria() != null && tarea.getCategoria().getId() != null) {
            Categorias categoria = categoriaService.buscarPorId(tarea.getCategoria().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
            tarea.setCategoria(categoria);
        }

        // Asignar usuario logueado
        tarea.setUsuario(usuarioActivo);

        // Por defecto la tarea nueva es PENDIENTE (2)
        tarea.setStatus((byte) 2);

        tareaRepository.save(tarea);
        return "redirect:/tarea";
    }


    // EDITAR - GET
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model, HttpSession session) {
        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/login";
        }

        Tareas tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));

        // Validar que la tarea sea del usuario logueado
        if (!tarea.getUsuario().getId().equals(usuarioActivo.getId())) {
            return "redirect:/tarea";
        }

        model.addAttribute("tarea", tarea);
        model.addAttribute("categorias", categoriaService.obtenerPorUsuario(usuarioActivo)); // ✅ categorías del usuario
        return "tarea/edit";
    }

    // EDITAR - POST
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Integer id,
                           @ModelAttribute Tareas tarea,
                           @RequestParam("categoria") Integer categoriaId,
                           HttpSession session) {
        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/login";
        }

        // Buscar tarea original
        Tareas tareaExistente = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));

        Categorias categoria = categoriaService.buscarPorId(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        // Mantener datos
        tareaExistente.setDescripcion(tarea.getDescripcion());
        tareaExistente.setCategoria(categoria);
        tareaExistente.setUsuario(usuarioActivo);
        // 🔑 status no lo tocamos, queda igual
        tareaRepository.save(tareaExistente);

        return "redirect:/tarea";
    }



    // ELIMINAR - GET
    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Integer id, Model model, HttpSession session) {
        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/login";
        }

        Tareas tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));

        if (!tarea.getUsuario().getId().equals(usuarioActivo.getId())) {
            return "redirect:/tarea";
        }

        model.addAttribute("tarea", tarea);
        model.addAttribute("categorias", categoriaService.obtenerPorUsuario(usuarioActivo)); // ✅ categorías del usuario
        return "tarea/delete";
    }

    // ELIMINAR - POST
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Integer id, HttpSession session) {
        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/login";
        }

        Tareas tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));

        if (tarea.getUsuario().getId().equals(usuarioActivo.getId())) {
            tareaRepository.deleteById(id);
        }

        if (tareaRepository.count() == 0) {
            tareaRepository.resetAutoIncrement();
        }

        return "redirect:/tarea";
    }

    // DETALLES - GET
    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model, HttpSession session) {
        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/login";
        }

        Tareas tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));

        if (!tarea.getUsuario().getId().equals(usuarioActivo.getId())) {
            return "redirect:/tarea";
        }

        model.addAttribute("tarea", tarea);
        return "tarea/details";
    }

    // CAMBIAR ESTADO DE LA TAREA
    @GetMapping("/toggleStatus/{id}")
    public String toggleStatus(@PathVariable Integer id, HttpSession session) {
        Usuario usuarioActivo = (Usuario) session.getAttribute("usuarioActivo");
        if (usuarioActivo == null) {
            return "redirect:/login";
        }

        Tareas tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));

        if (tarea.getUsuario().getId().equals(usuarioActivo.getId())) {
            tarea.setStatus(tarea.getStatus() == 1 ? (byte) 2 : (byte) 1);
            tareaRepository.save(tarea);
        }

        return "redirect:/tarea";
    }
}
