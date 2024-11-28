package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.rule.RuleJdbcTemplate;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {

    private final RuleJdbcTemplate ruleMem;

    @Override
    public Optional<Rule> findById(int id) {
        return ruleMem.findById(id);
    }

    @Override
    public Collection<Rule> findAll() {
        return ruleMem.findAll();
    }
}
