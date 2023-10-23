package ru.ulstu.is.sbapp.shedule.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.shedule.controller.AuditoriumDto;
import ru.ulstu.is.sbapp.shedule.controller.ClassSDto;
import ru.ulstu.is.sbapp.shedule.controller.SheduleDto;
import ru.ulstu.is.sbapp.shedule.model.Auditorium;
import ru.ulstu.is.sbapp.shedule.model.ClassS;
import ru.ulstu.is.sbapp.shedule.model.Shedule;
import ru.ulstu.is.sbapp.shedule.repository.AuditoriumRepository;
import ru.ulstu.is.sbapp.shedule.repository.SheduleRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import ru.ulstu.is.sbapp.shedule.model.Auditorium;
@Service
public class AuditoriumService {
    private final ValidatorUtil validatorUtil;
    private  final AuditoriumRepository auditoriumRepository;
    public AuditoriumService(ValidatorUtil validatorUtil, AuditoriumRepository auditoriumRepository) {
        this.validatorUtil = validatorUtil;
        this.auditoriumRepository = auditoriumRepository;


    }

    @Transactional
    public Auditorium addAuditorium(int cabinet, String type) {
        if ((cabinet == 0) || !StringUtils.hasText(type)) {
            throw new IllegalArgumentException("Auditorium data is null or empty");
        }

        Auditorium auditorium = new Auditorium(cabinet, type);
        validatorUtil.validate(auditorium);
        return auditoriumRepository.save(auditorium);
    }
    public AuditoriumDto addAuditorium(AuditoriumDto auditoriumDto){
        return new AuditoriumDto(addAuditorium(auditoriumDto.getCabinet(),auditoriumDto.getType()));
    }
    @Transactional(readOnly = true)
    public Auditorium findAuditoriumByCabinet(int cabinet) {
        List<Auditorium> auditoriums = auditoriumRepository.findAll();
        for (Auditorium auditorium : auditoriums) {
            if (auditorium.getCabinet()==cabinet)
                return auditorium;
        }
        throw new EntityNotFoundException(String.format("Auditorium with cabinet [%s] is not found", cabinet));
    }
    @Transactional
    public List<AuditoriumDto> findAuditoriumByClassContaining(int cabinet){
        return auditoriumRepository.findByCabinetContaining(cabinet).stream()
                .map(AuditoriumDto::new)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<Auditorium> findAllAuditoriums() {
        return auditoriumRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Auditorium findAuditorium(Long id) {
        final Optional<Auditorium> auditorium = auditoriumRepository.findById(id);
        return auditorium.orElseThrow(() -> new AuditoriumNotFoundException(id));
    }
    @Transactional
    public Auditorium updateAuditorium(Long id, int cabinet, String type) {
        if (!StringUtils.hasText(type) || (cabinet == 0)) {
            throw new IllegalArgumentException("Auditorium name is null or empty");
        }
        final Auditorium currentAuditorium = findAuditorium(id);
        currentAuditorium.setCabinet(cabinet);
        currentAuditorium.setType(type);
        validatorUtil.validate(currentAuditorium);
        return auditoriumRepository.save(currentAuditorium);
    }
    public AuditoriumDto updateAuditorium(AuditoriumDto auditoriumDto) {
        return new AuditoriumDto(updateAuditorium(auditoriumDto.getId(), auditoriumDto.getCabinet(), auditoriumDto.getType()));
    }
    @Transactional
    public Auditorium deleteAuditorium(Long id) {
        Auditorium currentAuditorium = findAuditorium(id);

        auditoriumRepository.delete(currentAuditorium);

        return currentAuditorium;
    }
}