package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.rule.RuleJdbcTemplate;
import ru.job4j.accidents.repository.rule.RuleSpringDataRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RuleSpringDataService implements RuleService {

    private final RuleSpringDataRepository ruleSpringData;

    @Override
    public Optional<Rule> findById(int id) {
        return ruleSpringData.findById(id);
    }

    @Override
    public Collection<Rule> findAll() {
        return (Collection<Rule>) ruleSpringData.findAll();
    }
}
