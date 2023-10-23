package ru.ulstu.is.sbapp.shedule.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ulstu.is.sbapp.shedule.model.ClassS;
import ru.ulstu.is.sbapp.shedule.model.Shedule;

import java.util.List;

public interface SheduleRepository extends JpaRepository<Shedule, Long> {
    @Query("SELECT sh FROM Shedule sh WHERE sh.classS.id = :classSid")
    List<Shedule> findByIdContaining(@Param("classSid") Long classSid);
    @Query("SELECT sh FROM Shedule sh WHERE sh.auditorium.cabinet = :audcabinet")
    List<Shedule> findByCabinetContaining(@Param("audcabinet") int audcabinet);
}
