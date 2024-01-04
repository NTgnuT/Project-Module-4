package com.ra.model.dao.category;

import com.ra.model.dao.IGenericDAO;
import com.ra.model.entity.Category;

import java.util.List;

public interface ICategoryDAO extends IGenericDAO<Category, Integer> {
    void changeStatus(Integer integer);
    List<Category> paginationBySearch(String name, Integer limit, Integer currentPage);
    int paginationTotal (String name, Integer limit, Integer currentPage);
}
