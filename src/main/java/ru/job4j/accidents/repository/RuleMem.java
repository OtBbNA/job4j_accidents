package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RuleMem implements RuleRepository {

    private Map<Integer, Rule> rules = new ConcurrentHashMap<>() {
        {
            put(1, new Rule(1, "Cтатья группы \"Происшествия области\""));
            put(2, new Rule(2, "Репортаж программы \"ЧП\""));
            put(3, new Rule(3, "Cтатья газеты \"Комсомольская правда\""));
            put(4, new Rule(4, "Видео очевидцев"));
        }
    };

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    @Override
    public Collection<Rule> findAll() {
        return rules.values();
    }
}
