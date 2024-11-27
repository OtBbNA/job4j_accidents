package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TypeHibernate implements TypeRepository {
    private final SessionFactory sf;

    @Override
    public AccidentType create(AccidentType type) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(type);
            session.getTransaction().commit();
            return type;
        }
    }

    @Override
    public void deleteById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            var cq = session.createQuery("delete AccidentType where id = :fId", AccidentType.class);
            cq.setParameter("fId", id);
            cq.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        try (Session session = sf.openSession()) {
            return Optional.ofNullable(session.get(AccidentType.class, id));
        }
    }

    @Override
    public Collection<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from AccidentType", AccidentType.class).list();
        }
    }
}
