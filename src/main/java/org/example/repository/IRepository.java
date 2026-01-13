package org.example.repository;

import org.example.domain.Entity;
import java.util.List;

public interface IRepository<T extends Entity<Integer>> {
    void add(T entity);
    void delete(Integer id);
    void update(T entity);
    T findOne(Integer id);
    List<T> findAll();
}