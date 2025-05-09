package putus.teddy.data.repository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Repository interface for managing entities.
 * It provides methods for creating, finding, and deleting entities.
 *
 * @param <T> The type of entity managed by the repository.
 */
public interface Repository<T> {
    boolean create(T entity);

    void createMany(List<T> entities);

    Stream<T> findAll();

    T findOne(List<Predicate<T>> query);

    Stream<T> findMany(List<Predicate<T>> query);

    Stream<T> findAny(List<Predicate<T>> predicates);

    boolean deleteOne(List<Predicate<T>> query);

    int deleteMany(List<Predicate<T>> query);
}