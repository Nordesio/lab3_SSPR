package ru.ulstu.is.sbapp.shedule.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity

public class Shedule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq2")
    @SequenceGenerator(name="seq2", sequenceName = "seq2", allocationSize = 10)
    private Long id;
    @NotBlank(message = "DayS can't be null or empty")
    private String DayS;
    private int number;
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Auditorium auditorium;
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private ClassS classS;


    public Shedule() {
    }

    public Shedule(String DayS, int number, Auditorium auditorium, ClassS classS) {
        this.DayS = DayS;
        this.number = number;
        this.auditorium = auditorium;
        this.classS = classS;
    }

    public Long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDayS() {
        return DayS;
    }

    public void setDayS(String DayS) {
        this.DayS = DayS;
    }

    public ClassS getClassS() {
        return classS;
    }

    public void setClassS(ClassS classS) {
        this.classS = classS;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shedule shedule = (Shedule) o;
        return Objects.equals(id, shedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Shedule{" +
                "id=" + id +
                ", DayS='" + DayS + '\'' +
                ", number='" + number + '\'' +
                ", subject='" + classS.toString() + '\'' +
				", auditorium='" + auditorium.toString() + '\'' +
                '}';
    }
}
