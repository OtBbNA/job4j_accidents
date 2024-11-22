package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TypeMem implements TypeRepository {

    private Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>() {
        {
            put(1, new AccidentType(1, "Две машины"));
            put(2, new AccidentType(2, "Машина и человек"));
            put(3, new AccidentType(3, "Машина и имущество"));
            put(4, new AccidentType(4, "ПДД"));
        }
    };

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypes.get(id));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypes.values();
    }
}
