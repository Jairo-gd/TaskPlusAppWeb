package org.esfe.controladores;

import org.esfe.modelos.Categorias;
import org.esfe.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "5") int size,
                        Model model) {

        Page<Categorias> pagina = categoriaService.buscarTodosPaginados(PageRequest.of(page, size));

        model.addAttribute("categorias", pagina.getContent()); // 🔑 Aquí mandamos "categorias" (plural)
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagina.getTotalPages());

        if (pagina.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, pagina.getTotalPages() - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "categoria/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("categoria", new Categorias());
        return "categoria/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("categoria") Categorias categoria, Model model) {
        categoriaService.crearOEditar(categoria);
        model.addAttribute("msg", "Categoría guardada con éxito");
        return "redirect:/categoria";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Categorias categoria = categoriaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con id: " + id));
        model.addAttribute("categoria", categoria);
        return "categoria/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("categoria") Categorias categoria, Model model) {
        categoriaService.crearOEditar(categoria);
        model.addAttribute("msg", "Categoría actualizada con éxito");
        return "redirect:/categoria";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Integer id, Model model) {
        Categorias categoria = categoriaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con id: " + id));
        model.addAttribute("categoria", categoria);
        return "categoria/details";
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Integer id, Model model) {
        Categorias categoria = categoriaService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con id: " + id));
        model.addAttribute("categoria", categoria);
        return "categoria/delete"; // Aquí va tu delete.html
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        categoriaService.eliminarPorId(id);
        model.addAttribute("msg", "Categoría eliminada con éxito");
        return "redirect:/categoria";
    }
}