package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.RuleRepository;
import ru.job4j.accidents.repository.TypeRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private final AccidentRepository accidentRepository;

    private final TypeRepository typeRepository;

    private final RuleRepository ruleRepository;

    @Override
    public Accident create(Accident accident, String[] ruleIds) {
        var typeId = accident.getType().getId();
        for (var id : ruleIds) {
            accident.getRules().add(ruleRepository.findById(Integer.parseInt(id)).get());
        }
        accident.setType(new AccidentType(typeId, typeRepository.findById(typeId).get().getName()));
        return accidentRepository.create(accident);
    }

    @Override
    public void deleteById(int id) {
        accidentRepository.deleteById(id);
    }

    @Override
    public boolean update(Accident accident, String[] ruleIds) {
        var typeId = accident.getType().getId();
        for (var id : ruleIds) {
            accident.getRules().add(ruleRepository.findById(Integer.parseInt(id)).get());
        }
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
