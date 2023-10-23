package ru.ulstu.is.sbapp.shedule.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ulstu.is.sbapp.shedule.service.SheduleService;
import javax.validation.Valid;
@Controller
@RequestMapping("/shedule")
public class SheduleMvcController {
    private final SheduleService sheduleService;

    public SheduleMvcController(SheduleService sheduleService) {
        this.sheduleService = sheduleService;
    }

    @GetMapping
    public String getShedules(Model model) {
        model.addAttribute("shedules",
                sheduleService.findAllShedules().stream()
                        .map(SheduleDto::new)
                        .toList());
        return "shedule";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editShedule(@PathVariable(required = false) Long id,
                              Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("sheduleDto", new SheduleDto());
        } else {
            model.addAttribute("sheduleId", id);
            model.addAttribute("sheduleDto", new SheduleDto(sheduleService.findShedule(id)));
        }
        return "shedule-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveShedule(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid SheduleDto SheduleDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "shedule-edit";
        }
        if (id == null || id <= 0) {
            sheduleService.addShedule(SheduleDto);
        } else {
            sheduleService.updateShedule(SheduleDto);
        }
        return "redirect:/shedule";
    }

    @PostMapping("/delete/{id}")
    public String deleteShedule(@PathVariable Long id) {
        sheduleService.deleteShedule(id);
        return "redirect:/shedule";
    }
}
