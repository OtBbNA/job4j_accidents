package ru.job4j.accidents.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.accident.AccidentSpringDataRepository;
import ru.job4j.accidents.repository.rule.RuleSpringDataRepository;
import ru.job4j.accidents.repository.type.TypeSpringDataRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentSpringDataService implements AccidentService {

    private final AccidentSpringDataRepository accidentRepository;

    private final TypeSpringDataRepository typeRepository;

    private final RuleSpringDataRepository ruleRepository;

    public Accident create(Accident accident, String[] ruleIds) {
        var typeId = accident.getType().getId();
        for (var id : ruleIds) {
            accident.getRules().add(ruleRepository.findById(Integer.parseInt(id)).get());
        }
        accident.setType(new AccidentType(typeId, typeRepository.findById(typeId).get().getName()));
        return accidentRepository.save(accident);
    }

    public void deleteById(int id) {
        accidentRepository.deleteById(id);
    }

    public boolean update(Accident accident, String[] ruleIds) {
        var typeId = accident.getType().getId();
        for (var id : ruleIds) {
            accident.getRules().add(ruleRepository.findById(Integer.parseInt(id)).get());
        }
        accident.setType(new AccidentType(typeId, typeRepository.findById(typeId).get().getName()));
        accidentRepository.save(accident);
        return true;
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public Collection<Accident> findAll() {
        return (Collection<Accident>) accidentRepository.findAll();
    }
}
