package ru.job4j.accidents.repository.rule;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RuleHibernate implements RuleRepository {

    @NonNull
    private final CrudRepository crudRepository;

    private static final Logger LOG = LoggerFactory.getLogger(Accident.class);

    @Override
    public Rule create(Rule rule) {
        Rule rsl = null;
        try {
            crudRepository.run(session -> session.persist(rule));
            rsl = rule;
        } catch (Exception e) {
            LOG.error("Ошибка при сохранении: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public void deleteById(int id) {
        try {
            crudRepository.run("DELETE Rule WHERE id = :fId", Map.of("fId", id));
        } catch (Exception e) {
            LOG.error("Произошла ошибка при удалении: " + e.getMessage());
        }
    }

    @Override
    public Optional<Rule> findById(int id) {
        Optional<Rule> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional("FROM Rule WHERE id = :fId", Rule.class, Map.of("fId", id));
        } catch (Exception e) {
            LOG.error("Произошла ошибка во время поиска: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public Collection<Rule> findAll() {
        return crudRepository.query("FROM Rule", Rule.class);
    }
}
