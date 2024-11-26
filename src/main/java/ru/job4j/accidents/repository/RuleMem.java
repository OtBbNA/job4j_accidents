package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@AllArgsConstructor
public class RuleMem implements RuleRepository {

    private final AtomicInteger nextId = new AtomicInteger(0);

    private Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    private RuleMem() {
        create(new Rule(1, "Cтатья группы Происшествия области"));
        create(new Rule(2, "Репортаж программы ЧП"));
        create(new Rule(3, "Cтатья газеты Комсомольская правда"));
        create(new Rule(4, "Видео очевидцев"));
    }

    @Override
    public Rule create(Rule rule) {
        rule.setId(nextId.incrementAndGet());
        return rules.put(rule.getId(), rule);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    @Override
    public Collection<Rule> findAll() {
        return rules.values();
    }
}
