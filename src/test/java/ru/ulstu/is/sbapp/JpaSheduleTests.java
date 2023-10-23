package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.shedule.model.Auditorium;
import ru.ulstu.is.sbapp.shedule.model.ClassS;
import ru.ulstu.is.sbapp.shedule.model.Shedule;
import ru.ulstu.is.sbapp.shedule.service.SheduleService;
import ru.ulstu.is.sbapp.shedule.service.*;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.ArrayList;

@SpringBootTest
public class JpaSheduleTests {
    private static final Logger log = LoggerFactory.getLogger(JpaSheduleTests.class);

    @Autowired
    private SheduleService sheduleService;
    @Autowired
    private ClassSService classSService;
    @Autowired
    private AuditoriumService auditoriumService;
    @Test
    void testSheduleCreate() {
        sheduleService.deleteAllShedules();
        ClassS classS = classSService.addClassS("num 1");
        auditoriumService.addAuditorium(123, "Computer");
        Shedule shedule = sheduleService.addShedule("Вторник", 3, 123, classS.getId());
        log.info(shedule.toString());
        Assertions.assertNotNull(shedule.getId());
    }

    @Test
    void testSheduleCreate2() {
        sheduleService.deleteAllShedules();
        ClassS classS = classSService.addClassS("num");
        auditoriumService.addAuditorium(123, "Computer");
        Shedule shedule = sheduleService.addShedule("Вторник", 3, 123, classS.getId());
        log.info(shedule.toString());
        Assertions.assertNotNull(shedule.getId());
    }
    @Test
    void testSheduleCreate3() {
        sheduleService.deleteAllShedules();
        ClassS classS = classSService.addClassS("123");
        auditoriumService.addAuditorium(123, "Computer");
        Shedule shedule = sheduleService.addShedule("Вторник", 3, 123, classS.getId());
        log.info(shedule.toString());
        Assertions.assertNotNull(shedule.getId());
    }
    @Test
    void testShedulesCreate(){
        sheduleService.deleteAllShedules();
        ClassS classS = classSService.addClassS("num 1");
        Auditorium aud = auditoriumService.addAuditorium(123, "Computer");
        ArrayList<Shedule> shedules = new ArrayList<>();
        ClassS cm = new ClassS("for 2 class");
        shedules.add(new Shedule("Понедельник", 1, aud, classS));
        shedules.add(new Shedule("Вторник", 1, aud, classS));
        shedules.add(new Shedule("Среда", 1, aud, classS));
        shedules.add(new Shedule("Четверг", 1, aud, classS));
        int count =0;
        for(Shedule shedule: shedules){
            Shedule sl = sheduleService.addShedule(shedule.getDayS(), shedule.getNumber(), shedule.getAuditorium().getCabinet(), shedule.getClassS().getId());
            log.info(sl.toString());
            if(sl!=null) count++;
        }
        Assertions.assertEquals(shedules.size(), count);
    }

    @Test
    void testSheduleRead() {
        sheduleService.deleteAllShedules();
        ClassS classS = classSService.addClassS("123");
        auditoriumService.addAuditorium(123, "Computer");
        Shedule shedule = sheduleService.addShedule("Вторник", 3, 123, classS.getId());
        log.info(shedule.toString());
        final Shedule findShedule = sheduleService.findShedule(shedule.getId());
        log.info(findShedule.toString());
        log.info(sheduleService.findAllShedules().toString());
        Assertions.assertEquals(shedule, findShedule);
    }

    @Test
    void testSheduleReadNotFound() {
        sheduleService.deleteAllShedules();
        Assertions.assertThrows(SheduleNotFoundException.class, () -> sheduleService.findShedule(-1L));
    }


    @Test
    void testSheduleReadAllEmpty() {
        sheduleService.deleteAllShedules();
        final List<Shedule> shedules = sheduleService.findAllShedules();
        log.info(shedules.toString());
        Assertions.assertEquals(shedules.size(), 0);
    }

    @Test
    void testClassSCreate() throws InClassSFoundShedulesException {
        classSService.deleteAllClassS();
        final ClassS classS = classSService.addClassS("for 1 class");
        log.info(classS.toString());
        Assertions.assertNotNull(classS.getId());
    }

    @Test
    void testClassSReadNotFound() throws InClassSFoundShedulesException {
        classSService.deleteAllClassSUnsafe();
        ///
        classSService.deleteAllClassS();
        Assertions.assertThrows(ClassSNotFoundException.class, () -> classSService.findClassS(-1L));
    }

    @Test
    void testClassSReadAllEmpty() throws InClassSFoundShedulesException {
        classSService.deleteAllClassSUnsafe();
        ///
        classSService.deleteAllClassS();
        final List<ClassS> ClassSs = classSService.findAllClassSs();
        log.info(ClassSs.toString());
        Assertions.assertEquals(ClassSs.size(), 0);
    }



}
