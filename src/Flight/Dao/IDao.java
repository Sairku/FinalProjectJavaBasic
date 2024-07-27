package Flight.Dao;

import java.util.List;

public interface IDao<T> {
    T get(int id);
    List<T> getAll();
    void save(T t);
    void update(T t);
    T delete(int id);
}
