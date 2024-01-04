package com.ra.model.service.product;

import com.ra.model.dao.product.IProductDAO;
import com.ra.model.dto.ProductDTO;
import com.ra.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceIMPL implements IProductService{
    @Autowired
    IProductDAO productDAO;
    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public boolean saveOrUpdate(Product product) {
        return productDAO.saveOrUpdate(product);
    }

    @Override
    public int addPro(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        product.setCategory(productDTO.getCategory());
        product.setImage(productDTO.getFile().getOriginalFilename());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setStatus(productDTO.isStatus());


        return productDAO.addPro(product);
    }

    @Override
    public int updatePro(Product product) {
        return productDAO.updatePro(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        productDAO.deleteProduct(id);
    }

    @Override
    public Product findById(Integer integer) {
        return productDAO.findById(integer);
    }

    @Override
    public List<Product> findByName(String name) {
        return null;
    }

    @Override
    public List<Product> pagination(Integer limit, Integer currentPage) {
        return null;
    }
}
