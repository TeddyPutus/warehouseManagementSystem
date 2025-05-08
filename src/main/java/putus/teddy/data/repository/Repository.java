package putus.teddy.data.repository;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Repository<T> {
    boolean create(T entity);

    boolean createMany(List<T> entities);

    Stream<T> findAll();

    T findOne(List<Predicate<T>> query);

    Stream<T> findMany(List<Predicate<T>> query);

    boolean deleteOne(List<Predicate<T>> query);

    int deleteMany(List<Predicate<T>> query);
}