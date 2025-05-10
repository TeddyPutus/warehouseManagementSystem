package putus.teddy.data.repository;

import putus.teddy.printer.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * InMemoryRepository is an in-memory implementation of the Repository interface.
 * It provides methods for creating, finding, and deleting entities in memory.
 *
 * @param <T> The type of entity managed by the repository.
 */
public class InMemoryRepository<T> implements Repository<T>{
    /**
     * List to store data in memory.
     */
    private List<T> entities = new ArrayList<>();

    /**
     * Creates multiple entities in the repository.
     * @param entities The list of entities to be created.
     */
    public void createMany(List<T> entities) {
        this.entities.addAll(entities);
    }

    /**
     * Creates a new entity in the repository.
     * @param entity The entity to be created.
     * @return true if the entity was created successfully, false otherwise.
     */
    public boolean create(T entity) {
        return entities.add(entity);
    }

    /**
     * Finds all entities in the repository.
     * @return A stream of all entities.
     */
    public Stream<T> findAll() {
        return entities.stream();
    }

    /**
     * Finds a single entity in the repository based on the provided predicates.
     * @param query The list of predicates to filter the entities.
     * @return The first entity that matches all the predicates, or null if no entity is found.
     */
    public T findOne(List<Predicate<T>> query) {
        try {
            return findMany(query)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            Printer.error("Error finding entity: " + e.getMessage());
            return null;
        }
    }

    /**
     * Finds multiple entities in the repository based on the provided predicates.
     * @param predicates The list of predicates to filter the entities.
     * @return A stream of entities that match all the predicates.
     */
    public Stream<T> findMany(List<Predicate<T>> predicates) {
        try {
            return entities.stream()
                    .filter(entity -> predicates.stream().allMatch(predicate -> predicate.test(entity)));
        } catch (Exception e) {
            Printer.error("Error finding entities: " + e.getMessage());
            return Stream.empty();
        }
    }

    /**
     * Finds entities in the repository that match any of the given predicates.
     * @param predicates The list of predicates to filter the entities.
     * @return A stream of entities that match any of the predicates.
     */
    public Stream<T> findAny(List<Predicate<T>> predicates) {
        try {
            return entities.stream()
                    .filter(entity -> predicates.stream().anyMatch(predicate -> predicate.test(entity)));
        } catch (Exception e) {
            Printer.error("Error finding entities: " + e.getMessage());
            return Stream.empty();
        }
    }

    /**
     * Deletes a single entity from the repository based on the provided predicates.
     * @param query The list of predicates to filter the entities.
     * @return true if the entity was deleted successfully, false otherwise.
     */
    public boolean deleteOne(List<Predicate<T>> query) {
        return entities.remove(findOne(query));
    }

    /**
     * Deletes multiple entities from the repository based on the provided predicates.
     * @param query The list of predicates to filter the entities.
     * @return The number of entities deleted.
     */
    public int deleteMany(List<Predicate<T>> query) {
        int initialSize = entities.size();

        try{
            entities.removeIf(entity -> query.stream().allMatch(predicate -> predicate.test(entity)));
        } catch (Exception e) {
            Printer.error("Error deleting entities: " + e.getMessage());
            return 0;
        }

        return initialSize - entities.size();
    }
}
