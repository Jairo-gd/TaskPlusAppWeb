package org.esfe.controladores;

import org.esfe.modelos.Tareas;
import org.esfe.modelos.Categorias;
import org.esfe.repositorios.ICategoriaRepository;
import org.esfe.repositorios.ITareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tarea")
public class TareaController {

    @Autowired
    private ITareaRepository tareaRepository;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    // LISTAR
    @GetMapping
    public String index(Model model) {
        List<Tareas> tareas = tareaRepository.findAll();
        model.addAttribute("tareas", tareas);
        return "tarea/index";
    }

    // CREAR - GET
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("tarea", new Tareas());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "tarea/create";
    }

    // CREAR - POST
    @PostMapping("/create")
    public String createPost(@ModelAttribute Tareas tarea, @RequestParam("categoria") Integer categoriaId) {
        Categorias categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        tarea.setCategoria(categoria);

        // Por defecto la tarea nueva es PENDIENTE (2)
        tarea.setStatus((byte) 2);

        // Guardar tarea (ID autogenerado por BD)
        tareaRepository.save(tarea);
        return "redirect:/tarea";
    }

    // EDITAR - GET
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Tareas tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
        model.addAttribute("tarea", tarea);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "tarea/edit";
    }

    // EDITAR - POST
    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Integer id, @ModelAttribute Tareas tarea, @RequestParam("categoria") Integer categoriaId) {
        Categorias categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        tarea.setId(id);
        tarea.setCategoria(categoria);

        tareaRepository.save(tarea);
        return "redirect:/tarea";
    }

    // ELIMINAR - GET (formulario)
    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Integer id, Model model) {
        Tareas tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
        model.addAttribute("tarea", tarea);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "tarea/delete";
    }

    // ELIMINAR - POST
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Integer id) {
        tareaRepository.deleteById(id);

        // Si no quedan tareas, reiniciar AUTO_INCREMENT
        if (tareaRepository.count() == 0) {
            tareaRepository.resetAutoIncrement();
        }

        return "redirect:/tarea";
    }
    // DETALLES - GET
    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model) {
        Tareas tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
        model.addAttribute("tarea", tarea);
        return "tarea/details"; // asegúrate de crear esta vista
    }


    // CAMBIAR ESTADO DE LA TAREA
    @GetMapping("/toggleStatus/{id}")
    public String toggleStatus(@PathVariable Integer id) {
        Tareas tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));

        // Cambiar entre PENDIENTE (2) y COMPLETADA (1)
        tarea.setStatus(tarea.getStatus() == 1 ? (byte) 2 : (byte) 1);

        tareaRepository.save(tarea);
        return "redirect:/tarea";
    }
}
