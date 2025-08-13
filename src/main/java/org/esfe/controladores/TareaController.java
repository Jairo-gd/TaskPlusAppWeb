package org.esfe.controladores;

import org.esfe.modelos.Tareas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import org.esfe.servicios.interfaces.ITareaService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tarea")
public class TareaController {
    @Autowired
    private ITareaService tareaService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1; // si no esta seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño dee la pagina, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Tareas> tareas = tareaService.buscarTodosPaginados(pageable);
        model.addAttribute("tareas", tareas);

        int totalPages = tareas.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "tarea/index";
    }
    @GetMapping("/create")
    public String create (Tareas tarea){
        return "tarea/create";
    }

    @PostMapping("/save")
    public String save(Tareas tarea, BindingResult result, Model model, RedirectAttributes attributes){
        if (result.hasErrors()){
            model.addAttribute(tarea);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error");
            return "tareas/create";
        }

        tareaService.crearOEditar(tarea);
        attributes.addFlashAttribute("msg", "Grupo creado correctamente");
        return "redirect:/tareas";
    }
}
