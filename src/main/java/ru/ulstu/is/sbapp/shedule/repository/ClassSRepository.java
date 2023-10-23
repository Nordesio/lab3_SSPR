package ru.ulstu.is.sbapp.shedule.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ulstu.is.sbapp.shedule.model.ClassS;

import java.util.List;
public interface ClassSRepository extends JpaRepository<ClassS, Long> {
    @Query("SELECT cl FROM ClassS cl WHERE cl.name LIKE %:classSName%")
    List<ClassS> findByNameContaining(@Param("classSName")String classSName);
}
