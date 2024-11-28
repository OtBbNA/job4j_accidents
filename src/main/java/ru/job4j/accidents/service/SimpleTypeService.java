package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.type.TypeJdbcTemplate;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTypeService implements TypeService {

    private final TypeJdbcTemplate typeRepository;

    @Override
    public Optional<AccidentType> findById(int id) {
        return typeRepository.findById(id);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return typeRepository.findAll();
    }
}
