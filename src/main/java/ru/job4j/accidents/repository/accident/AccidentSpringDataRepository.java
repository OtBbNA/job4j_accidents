package ru.job4j.accidents.repository.accident;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;

public interface AccidentSpringDataRepository extends CrudRepository<Accident, Integer> {
    @Query("SELECT a FROM Accident a JOIN FETCH a.rules")
    Collection<Accident> findAllWithRules();
}
