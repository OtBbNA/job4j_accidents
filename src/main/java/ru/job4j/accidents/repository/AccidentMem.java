package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements AccidentRepository {

    private final AtomicInteger nextId = new AtomicInteger(0);

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private AccidentMem() {
        create(new Accident(0, "Парковка в неположенном месте", "Заезд на газон придомовой территории", "ул. Ленина, д. 16/3", new AccidentType(4, "ПДД")));
        create(new Accident(0, "Повреждение муниципальной собственности ТС", "ТС согнуты столбы ограждения, повреждено лакокрасочное покрытие", "ул. Героев Севастополя, д. 38", new AccidentType(3, "Машина и имущество")));
        create(new Accident(0, "Парковка в неположенном месте", "ТС частично перекрывает пешеходный переход", "ул. Фрунзе, д. 41", new AccidentType(4, "ПДД")));
        create(new Accident(0, "Агрессивное вождение", "Множественный обгон без включения световых указателей поворота, не соблюдение безопасной дистанции и скорости при выполнении маневра обгона", "Ростовское шоссе", new AccidentType(4, "ПДД")));
        create(new Accident(0, "Превышение скорости более чем на 20 км/ч", "Находясь в населенном пункте обогнал патрульный автомобиль, который двигался на вызов и имел скорость 102 км/ч", "проспект Шолохова", new AccidentType(4, "ПДД")));
        create(new Accident(0, "Не уступил дорогу пешеходу", "Водитель ТС продолжил движение по левому ряду, когда водители правого ряда пропускали пешехода", "ул. Невская, д. 16Б", new AccidentType(4, "ПДД")));
    }

    @Override
    public Accident create(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return null;
    }

    @Override
    public void deleteById(int id) {
        accidents.remove(id);
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) -> new Accident(
                oldAccident.getId(),
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType()
        )) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }
}
