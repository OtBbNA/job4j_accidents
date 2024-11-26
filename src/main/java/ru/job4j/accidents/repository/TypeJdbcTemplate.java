package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TypeJdbcTemplate implements TypeRepository {

    private final JdbcTemplate jdbc;

    @Override
    public AccidentType create(AccidentType type) {
        jdbc.update("insert into accident_types (name) where (?)", type.getName());
        return type;
    }

    @Override
    public void deleteById(int id) {
        jdbc.update("delete from accident_types where id = ?", id);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        try {
            AccidentType type = jdbc.queryForObject("select * from accident_types where id = ?",
                    new Object[]{id},
                    (rs, rowNum) -> {
                        AccidentType accidentType = new AccidentType();
                        accidentType.setId(rs.getInt("id"));
                        accidentType.setName(rs.getString("name"));
                        return accidentType;
                    });
            return Optional.of(type);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Collection<AccidentType> findAll() {
        return jdbc.query("select * from accident_types",
                (rs, rowNum) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }
}
