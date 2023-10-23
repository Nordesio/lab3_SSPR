package ru.ulstu.is.sbapp.shedule.controller;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.shedule.model.Auditorium;

public class AuditoriumDto {
    private Long id;
    private int cabinet;
    private String type;
    private String shedules="";
    public AuditoriumDto(){}

    public AuditoriumDto(Auditorium auditorium) {
        this.id = auditorium.getId();
        this.cabinet = auditorium.getCabinet();
        this.type = auditorium.getType();
        if(auditorium.getShedules()!=null)
            this.shedules= auditorium.getShedules().toString();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCabinet() {
        return cabinet;
    }

    public String getType() {
        return type;
    }

    public void setCabinet(int cabinet) {
        this.cabinet = cabinet;
    }
    public String getShedules() {
        return shedules;
    }
    public void setType(String type) {
        this.type = type;
    }
}
