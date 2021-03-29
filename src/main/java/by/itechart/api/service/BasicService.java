package by.itechart.api.service;

import java.util.List;

/**
 * Basic service is choosed because there could be some possibilities that
 * similar method with the same structures could exist, so there is no need to duplicate same methods
 * @param <T> Entity
 */
public interface BasicService<T> {
    T create(T t);
    void delete(Long id);
    List<T> findAll();
}
