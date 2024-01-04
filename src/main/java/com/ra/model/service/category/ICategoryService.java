package com.ra.model.service.category;

import com.ra.model.entity.Category;
import com.ra.model.service.IGenericService;

import java.util.List;

public interface ICategoryService extends IGenericService<Category, Integer> {
    void changeStatus(Integer integer);
    List<Category> paginationBySearch(String name, Integer limit, Integer currentPage);
    int paginationTotal (String name, Integer limit, Integer currentPage);
}
