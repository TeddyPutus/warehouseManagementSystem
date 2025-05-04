package putus.teddy.data.repository;

import putus.teddy.data.entity.DataEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class InMemoryRepository<T extends DataEntity> implements Repository<T>{
    private List<T> entities = new ArrayList<>();

    public void importData(List<T> entities) {
        this.entities = new ArrayList<>(entities);
    }

    public boolean create(T entity) {
        return entities.add(entity);
    }

    public T findOne(Map<String,Object> query) {
        return entities.stream()
                .filter(entity -> entity.matches(query))
                .findFirst()
                .orElse(null);
    }

    public Stream<T> findMany(Map<String,Object> query) {
        return entities.stream()
                .filter(entity -> entity.matches(query));
    }

    public Stream<T> findAll() {
        return entities.stream();
    }

    public boolean update(T entity, Map<String,Object> query) {
        try {
            entity.update(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteOne(Map<String,Object> query) {
        T entity = findOne(query);
        if (entity != null) {
            entities.remove(entity);
            return true;
        }
        return false;
    }

    public int deleteMany(Map<String,Object> query) {
        int initialSize = entities.size();
        entities.removeIf(supplier -> supplier.matches(query));
        return initialSize - entities.size();
    }
}
