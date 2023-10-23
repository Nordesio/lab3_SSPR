package ru.ulstu.is.sbapp.shedule.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.shedule.service.SheduleService;
import ru.ulstu.is.sbapp.shedule.service.ClassSService;
import javax.validation.Valid;

@Controller
@RequestMapping("/classS")
public class ClassSMvcController {
    private final ClassSService classSService;
    private final SheduleService sheduleService;

    public ClassSMvcController(ClassSService classSService, SheduleService sheduleService) {
        this.classSService = classSService;
        this.sheduleService = sheduleService;
    }

    @GetMapping
    public String getClassSs(Model model) {
        model.addAttribute("classSs",
                classSService.findAllClassSs().stream()
                        .map(ClassSDto::new)
                        .toList());
        return "classS";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editClassS(@PathVariable(required = false) Long id,
                             Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("classSDto", new ClassSDto());
        } else {
            model.addAttribute("classSId", id);
            model.addAttribute("classSDto", new ClassSDto(classSService.findClassS(id)));
            model.addAttribute("shedules",
            sheduleService.findShedulesByClassContaining(id));
        }
        return "classS-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveClassS(@PathVariable(required = false) Long id,
                             @ModelAttribute @Valid ClassSDto ClassSDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "classS-edit";
        }
        if (id == null || id <= 0) {
            classSService.addClassS(ClassSDto.getName());
        } else {
            classSService.updateClassS(ClassSDto);
        }
        return "redirect:/classS";
    }

    @PostMapping("/delete/{id}")
    public String deleteClassS(@PathVariable Long id) {
        classSService.deleteClassS(id);
        return "redirect:/classS";
    }
}
