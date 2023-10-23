package ru.ulstu.is.sbapp.shedule.service;

public class ClassSNotFoundException extends RuntimeException {
    public ClassSNotFoundException(Long id) {
        super(String.format("ClassS  with id [%s] is not found", id));
    }
}
