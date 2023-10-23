package ru.ulstu.is.sbapp.shedule.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.WebConfiguration;
import ru.ulstu.is.sbapp.shedule.model.Shedule;
import ru.ulstu.is.sbapp.shedule.service.SheduleService;
import ru.ulstu.is.sbapp.test.model.TestDto;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/shedule")
public class SheduleController {
    private final SheduleService sheduleService;

    public SheduleController(SheduleService sheduleService) {
        this.sheduleService = sheduleService;
    }
/*
    @GetMapping("/{id}")
    public SheduleDto getShedule(@PathVariable Long id) {
        return new SheduleDto(sheduleService.findShedule(id));
    }
*/
    @GetMapping("/")
    public List<SheduleDto> getShedules() {
        return sheduleService.findAllShedules().stream()
                .map(SheduleDto::new)
                .toList();
    }

    @PostMapping("/")
    public SheduleDto createShedule(@RequestBody SheduleDto sheduleDto) {
        return sheduleService.addShedule(sheduleDto);
    }

    @PutMapping("/{id}")
    public SheduleDto updateShedule(@RequestBody @Valid SheduleDto sheduleDto) {
        return sheduleService.updateShedule(sheduleDto);
    }

    @DeleteMapping("/{id}")
    public SheduleDto deleteShedule(@PathVariable Long id) {
        return new SheduleDto(sheduleService.deleteShedule(id));
    }

/*
    @GetMapping("/{classS}")
    public List<SheduleDto> getShedule(@RequestParam("classS") Long classS) {
        return sheduleService.findShedulesByClassContaining(classS);
    }
    */
/*
    @GetMapping("/{cabinet}")
    public List<SheduleDto> getShedule(@RequestParam("cabinet") Long cabinet) {
        return sheduleService.findShedulesByCabinetContaining(cabinet);
    }
    */
}