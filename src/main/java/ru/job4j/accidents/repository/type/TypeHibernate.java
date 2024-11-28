package ru.job4j.accidents.repository.type;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TypeHibernate implements TypeRepository {

    @NonNull
    private final CrudRepository crudRepository;

    private static final Logger LOG = LoggerFactory.getLogger(Accident.class);

    @Override
    public AccidentType create(AccidentType type) {
        AccidentType rsl = null;
        try {
            crudRepository.run(session -> session.persist(type));
            rsl = type;
        } catch (Exception e) {
            LOG.error("Ошибка при сохранении: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public void deleteById(int id) {
        try {
            crudRepository.run("DELETE AccidentType WHERE id = :fId", Map.of("fId", id));
        } catch (Exception e) {
            LOG.error("Произошла ошибка при удалении: " + e.getMessage());
        }
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        Optional<AccidentType> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional("FROM AccidentType WHERE id = :fId", AccidentType.class, Map.of("fId", id));
        } catch (Exception e) {
            LOG.error("Произошла ошибка во время поиска: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public Collection<AccidentType> findAll() {
        return crudRepository.query("FROM AccidentType", AccidentType.class);
    }
}
