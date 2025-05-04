package putus.teddy.data.repository;

import java.util.Map;
import java.util.stream.Stream;

public interface Repository<T> {
    boolean create(T entity);

    T findOne(Map<String,Object> query);

    Stream<T> findMany(Map<String,Object> query);

    Stream<T> findAll();

    boolean update(T entity, Map<String,Object> query);

    boolean deleteOne(Map<String,Object> query);

    int deleteMany(Map<String,Object> query);
}