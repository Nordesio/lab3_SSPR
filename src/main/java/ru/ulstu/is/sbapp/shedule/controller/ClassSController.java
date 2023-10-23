package ru.ulstu.is.sbapp.shedule.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.WebConfiguration;
import ru.ulstu.is.sbapp.shedule.service.ClassSService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/classS")
public class ClassSController {
    private final ClassSService classSService;

    public ClassSController(ClassSService classSService) {
        this.classSService = classSService;
    }

    @GetMapping("/{id}")
    public ClassSDto getClassS(@PathVariable Long id) {
        return new ClassSDto(classSService.findClassS(id));
    }

    @GetMapping("/")
    public List<ClassSDto> getClassS()  {
        return classSService.findAllClassSs().stream()
                .map(ClassSDto::new)
                .toList();
    }

    @PostMapping("/")
    public ClassSDto createClassS(@RequestParam("name") String name) {
        return new ClassSDto(classSService.addClassS(name));
    }

    @PatchMapping("/{id}")
    public ClassSDto updateClassS(@RequestBody @Valid ClassSDto classSDto) {
        return classSService.updateClassS(classSDto);
    }

    @DeleteMapping("/{id}")
    public ClassSDto deleteClassS(@PathVariable Long id) {
        return new ClassSDto(classSService.deleteClassS(id));
    }
    @GetMapping("/like")
    public List<ClassSDto> getClassS(@RequestParam("name") String name) {
        return classSService.findClassSsByNameContaining(name);
    }

}