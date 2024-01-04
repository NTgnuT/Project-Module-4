package com.ra.model.service.category;

import com.ra.model.dao.category.ICategoryDAO;
import com.ra.model.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceIMPL implements ICategoryService{
    @Autowired
    private ICategoryDAO categoryDAO;
    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public boolean saveOrUpdate(Category category) {
        return categoryDAO.saveOrUpdate(category);
    }

    @Override
    public Category findById(Integer integer) {
        return categoryDAO.findById(integer);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryDAO.findByName(name);
    }

    @Override
    public List<Category> pagination(Integer limit, Integer currentPage) {
        return categoryDAO.pagination(limit, currentPage);
    }

    @Override
    public void changeStatus(Integer integer) {
        categoryDAO.changeStatus(integer);
    }

    @Override
    public List<Category> paginationBySearch(String name, Integer limit, Integer currentPage) {
        return categoryDAO.paginationBySearch(name, limit, currentPage);
    }

    @Override
    public int paginationTotal(String name, Integer limit, Integer currentPage) {
        return categoryDAO.paginationTotal(name, limit, currentPage);
    }

}
