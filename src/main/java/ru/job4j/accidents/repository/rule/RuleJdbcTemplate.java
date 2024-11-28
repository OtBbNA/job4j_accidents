package ru.job4j.accidents.repository.rule;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate implements RuleRepository {

    private final JdbcTemplate jdbc;

    @Override
    public Rule create(Rule rule) {
        try {
            jdbc.update("insert into rules (name) values (?)", rule.getName());
            return rule;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rule;
    }

    @Override
    public void deleteById(int id) {
        try {
            jdbc.update("delete from rules where id = ?", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Rule> findById(int id) {
        try {
            return Optional.ofNullable(jdbc.queryForObject("select * from rules where id = ?",
                    new Object[]{id},
                    (rs, rowNum) -> {
                        Rule rule = new Rule();
                        rule.setId(rs.getInt("id"));
                        rule.setName(rs.getString("name"));
                        return rule;
                    }));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Collection<Rule> findAll() {
        try {
            return jdbc.query("select * from rules",
                    (rs, rowNum) -> {
                        Rule rule = new Rule();
                        rule.setId(rs.getInt("id"));
                        rule.setName(rs.getString("name"));
                        return rule;
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
