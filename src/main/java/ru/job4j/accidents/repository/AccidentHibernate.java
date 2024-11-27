package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate implements AccidentRepository {
    private final SessionFactory sf;

    @Override
    public Accident create(Accident accident) {
        try (Session session = sf.openSession()) {
            session.save(accident);
            return accident;
        }
    }

    @Override
    public void deleteById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            var cq = session.createQuery("delete Accident where id = :fId", Accident.class);
            cq.setParameter("fId", id);
            cq.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public boolean update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.merge(accident);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public Optional<Accident> findById(int id) {
        try (Session session = sf.openSession()) {
            return Optional.ofNullable(session.get(Accident.class, id));
        }
    }

    @Override
    public Collection<Accident> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Accident", Accident.class)
                    .list();
        }
    }
}