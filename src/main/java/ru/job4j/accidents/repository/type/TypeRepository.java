package ru.job4j.accidents.repository.type;

import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

public interface TypeRepository {

    AccidentType create(AccidentType type);

    void deleteById(int id);

    Optional<AccidentType> findById(int id);

    Collection<AccidentType> findAll();
}
