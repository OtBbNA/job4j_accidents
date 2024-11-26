package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@AllArgsConstructor
public class TypeMem implements TypeRepository {

    private final AtomicInteger nextId = new AtomicInteger(0);

    private Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();

    private TypeMem() {
        create(new AccidentType(1, "Две машины"));
        create(new AccidentType(2, "Машина и человек"));
        create(new AccidentType(3, "Машина и имущество"));
        create(new AccidentType(4, "ПДД"));
    }

    @Override
    public AccidentType create(AccidentType type) {
        type.setId(nextId.incrementAndGet());
        return accidentTypes.put(type.getId(), type);
    }

    @Override
    public void deleteById(int id) {
        accidentTypes.remove(id);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypes.get(id));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypes.values();
    }
}
