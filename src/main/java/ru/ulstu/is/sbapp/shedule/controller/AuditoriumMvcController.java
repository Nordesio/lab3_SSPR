package ru.ulstu.is.sbapp.shedule.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.shedule.service.SheduleService;
import ru.ulstu.is.sbapp.shedule.service.AuditoriumService;
import javax.validation.Valid;
@Controller
@RequestMapping("/auditorium")
public class AuditoriumMvcController {
    private final AuditoriumService auditoriumService;
    private final SheduleService sheduleService;

    public AuditoriumMvcController(AuditoriumService auditoriumService, SheduleService sheduleService) {
        this.auditoriumService = auditoriumService;
        this.sheduleService = sheduleService;
    }

    @GetMapping
    public String getAuditoriums(Model model) {
        model.addAttribute("auditoriums",
                auditoriumService.findAllAuditoriums().stream()
                        .map(AuditoriumDto::new)
                        .toList());
        return "auditorium";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editAuditorium(@PathVariable(required = false) Long id,
                              Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("auditoriumDto", new AuditoriumDto());
        } else {
            model.addAttribute("auditoriumId", id);
            model.addAttribute("auditoriumDto", new AuditoriumDto(auditoriumService.findAuditorium(id)));
                    model.addAttribute("shedules",
                            sheduleService.findShedulesByCabinetContaining(id));
        }
        return "auditorium-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveAuditorium(@PathVariable(required = false) Long id,
                             @ModelAttribute @Valid AuditoriumDto AuditoriumDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "auditorium-edit";
        }
        if (id == null || id <= 0) {
            auditoriumService.addAuditorium(AuditoriumDto.getCabinet(), AuditoriumDto.getType());
        } else {
            auditoriumService.updateAuditorium(AuditoriumDto);
        }
        return "redirect:/auditorium";
    }

    @PostMapping("/delete/{id}")
    public String deleteAuditorium(@PathVariable Long id) {
        auditoriumService.deleteAuditorium(id);
        return "redirect:/auditorium";
    }
}
