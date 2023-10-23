package ru.ulstu.is.sbapp.shedule.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.shedule.controller.ClassSDto;
import ru.ulstu.is.sbapp.shedule.model.ClassS;
import ru.ulstu.is.sbapp.shedule.repository.ClassSRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassSService {
    private final Logger log = LoggerFactory.getLogger(ClassSService.class);
    private final ClassSRepository classSRepository;
    private final ValidatorUtil validatorUtil;

    public ClassSService(ClassSRepository classSRepository,
                         ValidatorUtil validatorUtil) {
        this.classSRepository = classSRepository;
        this.validatorUtil = validatorUtil;
    }
    @Transactional
    public ClassS addClassS(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("ClassS data is null or empty");
        }
        final ClassS classS = new ClassS(name);
        validatorUtil.validate(classS);
        return classSRepository.save(classS);
    }

    @Transactional(readOnly = true)
    public ClassS findClassS(Long id) {
        final Optional<ClassS> classS = classSRepository.findById(id);
        return classS.orElseThrow(() -> new ClassSNotFoundException(id));
    }
    @Transactional(readOnly = true)
    public ClassS findClassSByName(String name) {
        List<ClassS> classSES = classSRepository.findAll();
        for (ClassS classS : classSES) {
            if (classS.getName().equals(name))
                return classS;
        }
        throw new EntityNotFoundException(String.format("ClassS with name [%s] is not found", name));
    }


    @Transactional(readOnly = true)
    public List<ClassS> findAllClassSs() {
        return classSRepository.findAll();
    }

    @Transactional
    public ClassS updateClassS(Long id, String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("ClassS name is null or empty");
        }
        final ClassS currentClassS = findClassS(id);
        currentClassS.setName(name);
        validatorUtil.validate(currentClassS);
        return classSRepository.save(currentClassS);
    }

    public ClassSDto updateClassS(ClassSDto classSDto) {
        return new ClassSDto(updateClassS(classSDto.getId(), classSDto.getName()));
    }
    @Transactional
    public ClassS deleteClassS(Long id) {
        ClassS currentClassS = findClassS(id);
        classSRepository.delete(currentClassS);
        return currentClassS;
    }
    @Transactional
    public void deleteAllClassSUnsafe() {
        log.warn("Unsafe usage!");
        List<ClassS> classSES = findAllClassSs();
        for(ClassS classS : classSES){
            if(classS.getShedules().size()>0)
                classS.removeAllShedules();
        }
        classSRepository.deleteAll();
    }
    @Transactional
    public void deleteAllClassS() throws InClassSFoundShedulesException {
        List<ClassS> classSES = findAllClassSs();
        for(ClassS classS : classSES){
            if(classS.getShedules().size()>0)
                throw new InClassSFoundShedulesException("у класса " + classS.getName() + " есть расписания");
        }
        classSRepository.deleteAll();
    }
    @Transactional
    public List<ClassSDto> findClassSsByNameContaining(String name){
        return classSRepository.findByNameContaining(name).stream()
                .map(ClassSDto::new)
                .collect(Collectors.toList());
    }
}
