package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.TypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private final AccidentRepository accidentRepository;

    private final TypeRepository typeRepository;

    @Override
    public Accident create(Accident accident) {
        var typeId = accident.getType().getId();
        accident.setType(new AccidentType(typeId, typeRepository.findById(typeId).get().getName()));
        return accidentRepository.create(accident);
    }

    @Override
    public void deleteById(int id) {
        accidentRepository.deleteById(id);
    }

    @Override
    public boolean update(Accident accident) {
        var typeId = accident.getType().getId();
        accident.setType(new AccidentType(typeId, typeRepository.findById(typeId).get().getName()));
        return accidentRepository.update(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }
}
