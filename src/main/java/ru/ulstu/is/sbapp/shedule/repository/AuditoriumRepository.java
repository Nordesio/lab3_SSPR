package ru.ulstu.is.sbapp.shedule.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ulstu.is.sbapp.shedule.model.Auditorium;


import java.util.List;
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long>{
    @Query("SELECT aud FROM Auditorium aud WHERE aud.cabinet LIKE %:auditoriumCabinet%")
    List<Auditorium> findByCabinetContaining(@Param("auditoriumCabinet")int auditoriumCabinet);
}
