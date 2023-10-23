package ru.ulstu.is.sbapp.shedule.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.List;

@Entity
public class ClassS {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq1")
    @SequenceGenerator(name="seq1", sequenceName = "seq1", allocationSize = 10)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "classS", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shedule> shedules;

    public ClassS(){ }

    public ClassS(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }
	
    public String getName() {
        return name;
    }
	
	public void setName(String name){
	    this.name = name;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassS classS = (ClassS) o;
        return Objects.equals(id, classS.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
	
	@Override
    public String toString() {
        return name;
    }
    public List<Shedule> getShedules() {
        return shedules;
    }
    public void updateShedule(Long sheduleID, Shedule st) {
        for(Shedule shedule:shedules){
            if(shedule.getId()==st.getId()) {
                shedule = st;
                break;
            }
        }
    }

    public void addShedule(Shedule shedule) {
        shedules.add(shedule);
    }
    public void removeShedule(Long sheduleID){
        shedules.remove(sheduleID);
    }
    public void removeAllShedules(){
        shedules.clear();
    }
}

