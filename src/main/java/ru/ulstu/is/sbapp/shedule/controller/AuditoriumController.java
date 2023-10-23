package ru.ulstu.is.sbapp.shedule.controller;
import ru.ulstu.is.sbapp.WebConfiguration;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.shedule.service.AuditoriumService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/auditorium")
public class AuditoriumController {
    private final AuditoriumService auditoriumService;

    public AuditoriumController(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    @GetMapping("/{id}")
    public AuditoriumDto getAuditorium(@PathVariable Long id) {
        return new AuditoriumDto(auditoriumService.findAuditorium(id));
    }

    @GetMapping("/")
    public List<AuditoriumDto> getAuditorium()  {
        return auditoriumService.findAllAuditoriums().stream()
                .map(AuditoriumDto::new)
                .toList();
    }

    @PostMapping("/")
    public AuditoriumDto createAuditorium(@RequestBody @Valid AuditoriumDto auditoriumDto) {
        return auditoriumService.addAuditorium(auditoriumDto);
    }


    @PatchMapping("/{id}")
    public AuditoriumDto updateAuditorium(@RequestBody @Valid AuditoriumDto auditoriumDto) {
        return auditoriumService.updateAuditorium(auditoriumDto);
    }

    @DeleteMapping("/{id}")
    public AuditoriumDto deleteAuditorium(@PathVariable Long id) {
        return new AuditoriumDto(auditoriumService.deleteAuditorium(id));
    }
}