package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private final JdbcTemplate jdbc;

    private final TypeJdbcTemplate typeJdbc;

    @Override
    public Accident create(Accident accident) {
        try {
            jdbc.update("insert into accidents (name, description, address, type_id) values (?, ?, ?, ?)",
                    accident.getName(), accident.getText(), accident.getAddress(),
                    accident.getType().getId());
            int accidentId = jdbc.queryForObject("select lastval()", Integer.class);
            for (Rule rule : accident.getRules()) {
                jdbc.update("insert into accident_rules (accident_id, rule_id) values (?, ?)",
                        accidentId, rule.getId());
            }
            return accident;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accident;
    }

    @Override
    public void deleteById(int id) {
        try {
            jdbc.update("delete from accident_rules where accident_id = ?", id);
            jdbc.update("delete from accidents where id = ?", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Accident accident) {
        try {
            jdbc.update("update accidents set name = ?, description = ?, address = ?, type_id = ? where id = ?",
                    accident.getName(), accident.getText(), accident.getAddress(),
                    accident.getType().getId(), accident.getId());
            jdbc.update("delete from accident_rules where accident_id = ?", accident.getId());
            for (Rule rule : accident.getRules()) {
                jdbc.update("insert into accident_rules (accident_id, rule_id) values (?, ?)",
                        accident.getId(), rule.getId());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Accident> findById(int id) {
        try {
            return Optional.ofNullable(jdbc.queryForObject("select * from accidents where id = ?",
                    new Object[]{id},
                    (rs, rowNum) -> {
                        Accident accident = new Accident();
                        accident.setId(rs.getInt("id"));
                        accident.setName(rs.getString("name"));
                        accident.setText(rs.getString("description"));
                        accident.setAddress(rs.getString("address"));
                        accident.setType(typeJdbc.findById(Integer.parseInt(rs.getString("type_id"))).get());
                        accident.setRules(findRulesByAccidentId(accident.getId()));
                        return accident;
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Set<Rule> findRulesByAccidentId(int accidentId) {
        try {
            return new HashSet<>(jdbc.query("select r.* from rules r join accident_rules ar on r.id = ar.rule_id WHERE ar.accident_id = ?",
                    new Object[]{accidentId},
                    (rs, rowNum) -> {
                        Rule rule = new Rule();
                        rule.setId(rs.getInt("id"));
                        rule.setName(rs.getString("name"));
                        return rule;
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    @Override
    public Collection<Accident> findAll() {
        try {
            return jdbc.query("select * from accidents",
                    (rs, rowNum) -> {
                        Accident accident = new Accident();
                        accident.setId(rs.getInt("id"));
                        accident.setName(rs.getString("name"));
                        accident.setText(rs.getString("description"));
                        accident.setAddress(rs.getString("address"));
                        accident.setType(typeJdbc.findById(Integer.parseInt(rs.getString("type_id"))).get());
                        accident.setRules(findRulesByAccidentId(accident.getId()));
                        return accident;
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}