package putus.teddy.data.repository;

import putus.teddy.data.entity.DataEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class InMemoryRepository<T extends DataEntity> implements Repository<T>{
    private List<T> entities = new ArrayList<>();

    public void importData(List<T> entities) {
        this.entities = new ArrayList<>(entities);
    }

    public boolean create(T entity) {
        return entities.add(entity);
    }

    public Stream<T> findAll() {
        return entities.stream();
    }

    public T findOne(List<Predicate<T>> query) {
        try {
            return findMany(query)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.out.println("Error finding entity: " + e.getMessage());
            return null;
        }
    }

    public Stream<T> findMany(List<Predicate<T>> predicates) {
        try {
            return entities.stream()
                    .filter(entity -> predicates.stream().allMatch(predicate -> predicate.test(entity)));
        } catch (Exception e) {
            System.out.println("Error finding entities: " + e.getMessage());
            return Stream.empty();
        }
    }

    public boolean deleteOne(List<Predicate<T>> query) {
        return entities.remove(findOne(query));
    }

    public int deleteMany(List<Predicate<T>> query) {
        int initialSize = entities.size();

        try{
            entities.removeIf(entity -> query.stream().allMatch(predicate -> predicate.test(entity)));
        } catch (Exception e) {
            System.out.println("Error deleting entities: " + e.getMessage());
            return 0;
        }

        return initialSize - entities.size();
    }
}
