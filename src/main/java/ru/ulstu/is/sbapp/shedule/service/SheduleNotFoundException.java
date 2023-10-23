package ru.ulstu.is.sbapp.shedule.service;

public class SheduleNotFoundException  extends RuntimeException {
    public SheduleNotFoundException(Long id) {
        super(String.format("Shedule with id [%s] is not found", id));
    }
}