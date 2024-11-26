CREATE TABLE accident_rules (
    accident_id int references accidents(id),
    rule_id int references rules(id)
);