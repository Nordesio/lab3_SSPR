package ru.ulstu.is.sbapp.shedule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.shedule.controller.ClassSDto;
import ru.ulstu.is.sbapp.shedule.controller.SheduleDto;
import ru.ulstu.is.sbapp.shedule.model.Auditorium;
import ru.ulstu.is.sbapp.shedule.model.ClassS;
import ru.ulstu.is.sbapp.shedule.model.Shedule;
import ru.ulstu.is.sbapp.shedule.repository.SheduleRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SheduleService {
    private final SheduleRepository sheduleRepository;
    private final AuditoriumService auditoriumService;
    private final ValidatorUtil validatorUtil;
    private final ClassSService classSService;

    public SheduleService(SheduleRepository sheduleRepository,
                          ValidatorUtil validatorUtil,
                          ClassSService classSService,
                          AuditoriumService auditoriumService) {
        this.sheduleRepository = sheduleRepository;
        this.validatorUtil = validatorUtil;
        this.classSService = classSService;
        this.auditoriumService = auditoriumService;
    }

    @Transactional
    public Shedule addShedule(String dayS, int number,int aud, Long cm) {

        if (!StringUtils.hasText(dayS)  || (number == 0) || (aud == 0)|| (cm == 0)) {
            throw new IllegalArgumentException("Shedule data is null or empty");
        }
        ClassS classS = classSService.findClassS(cm);
        Auditorium auditorium = auditoriumService.findAuditoriumByCabinet(aud);
        Shedule shedule = new Shedule(dayS, number, auditorium, classS);
        classS.addShedule(shedule);
        validatorUtil.validate(shedule);
        return sheduleRepository.save(shedule);
    }

    public SheduleDto addShedule(SheduleDto sheduleDto){
        return new SheduleDto(addShedule(sheduleDto.getDayS(),sheduleDto.getNumber(),sheduleDto.getAuditorium(),sheduleDto.getClassS()));
    }

    @Transactional(readOnly = true)
    public Shedule findShedule(Long id) {
        final Optional<Shedule> shedule = sheduleRepository.findById(id);
        return shedule.orElseThrow(() -> new SheduleNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Shedule> findAllShedules() {
        return sheduleRepository.findAll();
    }
    @Transactional
    public Shedule updateShedule(Long id, String dayS, int number,int aud, Long cm) {

        if ((number == 0) || !StringUtils.hasText(dayS) || (cm==0) ||
                (aud == 0)) {

                throw new IllegalArgumentException("Shedule data* is null or empty");
            }
            final Shedule currentShedule = findShedule(id);

            Auditorium auditorium = auditoriumService.findAuditoriumByCabinet(aud);
            ClassS classS = classSService.findClassS(cm);
            currentShedule.setDayS(dayS);
            currentShedule.setNumber(number);
            currentShedule.setAuditorium(auditorium);

        if(currentShedule.getClassS().getName().equals(cm)){
            currentShedule.getClassS().updateShedule(id, currentShedule);
        }
        else {
            currentShedule.getClassS().removeShedule(id);
            currentShedule.setClassS(classS);
            classS.addShedule(currentShedule);
        }
        validatorUtil.validate(currentShedule);
        return sheduleRepository.save(currentShedule);
        }
    public SheduleDto updateShedule(SheduleDto sheduleDto) {
        return new SheduleDto(updateShedule(sheduleDto.getId(),sheduleDto.getDayS(),sheduleDto.getNumber(),
                sheduleDto.getAuditorium(),sheduleDto.getClassS()));
    }
    @Transactional
    public Shedule deleteShedule(Long id) {
        final Shedule currentShedule = findShedule(id);
        sheduleRepository.delete(currentShedule);
        return currentShedule;
    }

    @Transactional
    public void deleteAllShedules() {
        sheduleRepository.deleteAll();
    }
    @Transactional
    public List<SheduleDto> findShedulesByClassContaining(Long id){
        return sheduleRepository.findByIdContaining(id).stream()
                .map(SheduleDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public List<SheduleDto> findShedulesByCabinetContaining(Long id){
        Auditorium aud = auditoriumService.findAuditorium(id);
        return sheduleRepository.findByCabinetContaining(aud.getCabinet()).stream()
                .map(SheduleDto::new)
                .collect(Collectors.toList());
    }
}
