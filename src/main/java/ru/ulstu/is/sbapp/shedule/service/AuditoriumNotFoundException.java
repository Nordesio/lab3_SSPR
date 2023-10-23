package ru.ulstu.is.sbapp.shedule.service;

public class AuditoriumNotFoundException extends RuntimeException {
    public AuditoriumNotFoundException(Long id) {
        super(String.format("Auditorium  with id [%s] is not found", id));
    }
}
