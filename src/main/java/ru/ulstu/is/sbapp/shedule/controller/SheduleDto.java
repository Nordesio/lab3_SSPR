package ru.ulstu.is.sbapp.shedule.controller;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;
import ru.ulstu.is.sbapp.shedule.model.Shedule;

public class SheduleDto {
    private Long id;
    private String dayS;
    private int number;
    @Nullable
    private int auditorium;
    private Long classS;

    public SheduleDto(Shedule shedule) {

        this.dayS= shedule.getDayS();
        this.number= shedule.getNumber();
        this.id = shedule.getId();
        this.auditorium = shedule.getAuditorium().getCabinet();
        this.classS= shedule.getClassS().getId();
    }

    public SheduleDto() {
    }


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDayS() {
        return dayS;
    }
    public void setDayS(String dayS) {
        this.dayS = dayS;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getAuditorium(){
        return auditorium;
    }

    public Long getClassS() {
        return classS;
    }
    public void setClassS(Long classS) {
        this.classS = classS;
    }

    public void setAuditorium(int auditorium){
        this.auditorium = auditorium;
    }


}
