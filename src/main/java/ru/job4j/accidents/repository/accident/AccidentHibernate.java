package ru.job4j.accidents.repository.accident;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.CrudRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccidentHibernate implements AccidentRepository {

    @NonNull
    private final CrudRepository crudRepository;

    private static final Logger LOG = LoggerFactory.getLogger(Accident.class);

    @Override
    public Accident create(Accident accident) {
        Accident rsl = null;
        try {
            crudRepository.run(session -> session.persist(accident));
            rsl = accident;
        } catch (Exception e) {
            LOG.error("Ошибка при сохранении: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public void deleteById(int id) {
        try {
            crudRepository.run("DELETE Accident WHERE id = :fId", Map.of("fId", id));
        } catch (Exception e) {
            LOG.error("Произошла ошибка при удалении: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Accident accident) {
        boolean rsl = false;
        try {
            crudRepository.run(session -> session.merge(accident));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Ошибка при обновлении задачи: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public Optional<Accident> findById(int id) {
        Optional<Accident> rsl = Optional.empty();
        try {
            rsl = crudRepository.optional("SELECT a FROM Accident a JOIN FETCH a.rules WHERE a.id = :fId", Accident.class, Map.of("fId", id));
        } catch (Exception e) {
            LOG.error("Произошла ошибка во время поиска: " + e.getMessage());
        }
        return rsl;
    }

    @Override
    public Collection<Accident> findAll() {
        return crudRepository.query("FROM Accident", Accident.class);
    }
}