package com.ra.model.dao.product;

import com.ra.model.dao.category.ICategoryDAO;
import com.ra.model.entity.Product;
import com.ra.util.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAO_Impl implements IProductDAO {
    @Autowired
    ICategoryDAO categoryDAO;

    @Override
    public List<Product> findAll() {
        Connection collection = ConnectionDB.openConnection();
        List<Product> products = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL show_product()");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setCategory(categoryDAO.findById(resultSet.getInt("category_id")));
                product.setImage(resultSet.getString("image"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setStatus(resultSet.getBoolean("status"));

                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return products;
    }

    @Override
    public boolean saveOrUpdate(Product product) {
        Connection connection = ConnectionDB.openConnection();
        int check;
        CallableStatement callableStatement;
        try {
            if (product.getProductId() == 0) {
                callableStatement = connection.prepareCall("CALL add_product(?, ?, ?, ?, ?, ?, ?,?)");
                callableStatement.setString(1, product.getProductName());
                callableStatement.setInt(2, product.getCategory().getCategoryId());
                callableStatement.setString(3, product.getImage());
                callableStatement.setString(4, product.getDescription());
                callableStatement.setDouble(5, product.getPrice());
                callableStatement.setInt(6, product.getStock());
                callableStatement.setBoolean(7, product.isStatus());
                callableStatement.setInt(8, Types.INTEGER);
            } else {
                callableStatement = connection.prepareCall("CALL update_product(?, ?, ?, ?, ?, ?, ?, ?)");
                callableStatement.setString(1, product.getProductName());
                callableStatement.setInt(2, product.getCategory().getCategoryId());
                callableStatement.setString(3, product.getImage());
                callableStatement.setString(4, product.getDescription());
                callableStatement.setDouble(5, product.getPrice());
                callableStatement.setInt(6, product.getStock());
                callableStatement.setBoolean(7, product.isStatus());
                callableStatement.setInt(8, product.getProductId());
            }

            check = callableStatement.executeUpdate();

            if (check > 0) {
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Product findById(Integer integer) {
        Connection connection = ConnectionDB.openConnection();
        Product product = new Product();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL find_by_id_product(?)");
            callableStatement.setInt(1, integer);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setCategory(categoryDAO.findById(resultSet.getInt("category_id")));
                product.setImage(resultSet.getString("image"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setStatus(resultSet.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return product;
    }

    @Override
    public List<Product> findByName(String name) {
        return null;
    }

    @Override
    public List<Product> pagination(Integer limit, Integer currentPage) {
        return null;
    }

    @Override
    public int addPro(Product product) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement;
            callableStatement = connection.prepareCall("CALL add_product(?, ?, ?, ?, ?, ?, ?,?)");
            callableStatement.setString(1, product.getProductName());
            callableStatement.setInt(2, product.getCategory().getCategoryId());
            callableStatement.setString(3, product.getImage());
            callableStatement.setString(4, product.getDescription());
            callableStatement.setDouble(5, product.getPrice());
            callableStatement.setInt(6, product.getStock());
            callableStatement.setBoolean(7, product.isStatus());
            callableStatement.setInt(8, Types.INTEGER);
            callableStatement.executeUpdate();
            return callableStatement.getInt(8);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public int updatePro(Product product) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL update_product(?, ?, ?, ?, ?, ?, ?, ?)");
            callableStatement.setString(1, product.getProductName());
            callableStatement.setInt(2, product.getCategory().getCategoryId());
            callableStatement.setString(3, product.getImage());
            callableStatement.setString(4, product.getDescription());
            callableStatement.setDouble(5, product.getPrice());
            callableStatement.setInt(6, product.getStock());
            callableStatement.setBoolean(7, product.isStatus());
            callableStatement.setInt(8, product.getProductId());

            callableStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public void deleteProduct(Integer id) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL delete_product(?)");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }
}
