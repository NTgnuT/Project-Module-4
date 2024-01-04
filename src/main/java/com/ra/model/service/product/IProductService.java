package com.ra.model.service.product;

import com.ra.model.dto.ProductDTO;
import com.ra.model.entity.Product;
import com.ra.model.service.IGenericService;

public interface IProductService extends IGenericService<Product, Integer> {
    int addPro(ProductDTO productDTO);
    int updatePro(Product product);
    void deleteProduct (Integer id);
}
