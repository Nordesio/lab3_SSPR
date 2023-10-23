package ru.ulstu.is.sbapp.shedule.model;

import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Auditorium {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name="seq", sequenceName = "seq", allocationSize = 10)
    private Long id;
    private int cabinet;
	private String type;
    @ReadOnlyProperty
    @OneToMany(mappedBy = "auditorium", cascade = CascadeType.ALL)
    private List<Shedule> shedules;
    public Auditorium(){ }

    public Auditorium(int cabinet, String type){
        this.cabinet = cabinet;
		this.type = type;
    }
    public Long getId() {
        return id;
    }

    public int getCabinet() {
        return cabinet;
    }
	
	public void setCabinet(int cabinet) {
        this.cabinet = cabinet;
    }
	
	    public String getType() {
        return type;
    }
	
	public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auditorium auditorium = (Auditorium) o;
        return Objects.equals(id, auditorium.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<Shedule> getShedules() {
        return shedules;
    }
    @Override
    public String toString() {
        return "Auditorium{" +
                "id=" + id +
                ", cabinet='" + cabinet + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
