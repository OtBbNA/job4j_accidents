package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleHibernate implements RuleRepository {
    private final SessionFactory sf;

    @Override
    public Rule create(Rule rule) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(rule);
            session.getTransaction().commit();
            return rule;
        }
    }

    @Override
    public void deleteById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            var cq = session.createQuery("delete Rule where id = :fId", Rule.class);
            cq.setParameter("fId", id);
            cq.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public Optional<Rule> findById(int id) {
        try (Session session = sf.openSession()) {
            return Optional.ofNullable(session.get(Rule.class, id));
        }
    }

    @Override
    public Collection<Rule> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Rule", Rule.class).list();
        }
    }
}
