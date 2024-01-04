package com.ra.model.dao;

import com.ra.model.entity.Category;

import java.util.List;

public interface IGenericDAO<T, ID> {
    List<T> findAll();
    boolean saveOrUpdate(T t);
    T findById (ID id);
    List<T> findByName(String name);
    List<T> pagination(ID limit, ID currentPage);
}
