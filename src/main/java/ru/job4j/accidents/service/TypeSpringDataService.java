package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.type.TypeSpringDataRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeSpringDataService implements TypeService {

    private final TypeSpringDataRepository typeSpringData;

    @Override
    public Optional<AccidentType> findById(int id) {
        return typeSpringData.findById(id);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return (Collection<AccidentType>) typeSpringData.findAll();
    }
}
