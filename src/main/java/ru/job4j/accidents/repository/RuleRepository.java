package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleRepository {

    Rule create(Rule rule);

    void deleteById(int id);

    Optional<Rule> findById(int id);

    Collection<Rule> findAll();
}
