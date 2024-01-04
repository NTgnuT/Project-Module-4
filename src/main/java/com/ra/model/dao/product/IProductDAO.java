package com.ra.model.dao.product;

import com.ra.model.dao.IGenericDAO;
import com.ra.model.entity.Product;

public interface IProductDAO extends IGenericDAO<Product, Integer> {
    int addPro(Product product);
    int updatePro(Product product);
    void deleteProduct (Integer id);
}
