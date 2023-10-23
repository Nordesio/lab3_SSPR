package ru.ulstu.is.sbapp.shedule.controller;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.shedule.model.ClassS;
import javax.validation.constraints.NotBlank;
public class ClassSDto {
    private Long id;
    @NotBlank
    private String name;
    private String shedules="";
    public ClassSDto(){}

    public ClassSDto(ClassS classS) {
        this.id = classS.getId();
        this.name = classS.getName();
        if(classS.getShedules()!=null)
            this.shedules= classS.getShedules().toString();
    }
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getShedules() {
        return shedules;
    }
}
