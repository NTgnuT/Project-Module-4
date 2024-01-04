package com.ra.model.dao.category;

import com.ra.model.entity.Category;
import com.ra.util.ConnectionDB;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CategoryDAO_Impl implements ICategoryDAO {
    public static int totalPage;
    @Override
    public List<Category> findAll() {
        Connection collection = ConnectionDB.openConnection();
        List<Category> categories = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL show_cat()");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                category.setStatus(resultSet.getBoolean("status"));

                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return categories;
    }

    @Override
    public boolean saveOrUpdate(Category category) {
        Connection connection = ConnectionDB.openConnection();
        int check;
        CallableStatement callableStatement;
        try {
            if (category.getCategoryId() == 0) {
                callableStatement = connection.prepareCall("CALL add_cat(?, ?, ?)");
                callableStatement.setString(1, category.getCategoryName());
                callableStatement.setString(2, category.getDescription());
                callableStatement.setBoolean(3, category.isStatus());
            } else {
                callableStatement = connection.prepareCall("CALL update_cat(?, ?, ?, ?)");
                callableStatement.setString(1, category.getCategoryName());
                callableStatement.setString(2, category.getDescription());
                callableStatement.setBoolean(3, category.isStatus());
                callableStatement.setInt(4, category.getCategoryId());
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
    public Category findById(Integer integer) {
        Connection connection = ConnectionDB.openConnection();
        Category category = new Category();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL find_by_id_cat(?)");
            callableStatement.setInt(1, integer);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                category.setStatus(resultSet.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return category;
    }

    @Override
    public List<Category> findByName(String name) {
        Connection connection = ConnectionDB.openConnection();
        List<Category> categories = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL find_by_name_cat(?)");
            callableStatement.setString(1, name);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                category.setStatus(resultSet.getBoolean("status"));

                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return categories;
    }

    @Override
    public List<Category> pagination(Integer limit, Integer currentPage) {
        Connection collection = ConnectionDB.openConnection();
        List<Category> categories = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL pagination_category(?, ?, ?)");
            callableStatement.setInt(1, limit);
            callableStatement.setInt(2, currentPage);
            callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            totalPage = callableStatement.getInt(3);
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                category.setStatus(resultSet.getBoolean("status"));

                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return categories;
    }

    @Override
    public void changeStatus(Integer integer) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL change_status_cat(?)");
            callableStatement.setInt(1, integer);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public List<Category> paginationBySearch(String name, Integer limit, Integer currentPage) {
        Connection collection = ConnectionDB.openConnection();
        List<Category> categories = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL CAT_FIND_PAGED(?, ?, ?)");
            callableStatement.setString(1, name);
            callableStatement.setInt(2, limit);
            callableStatement.setInt(3, currentPage);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                category.setStatus(resultSet.getBoolean("status"));

                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return categories;
    }

    @Override
    public int paginationTotal(String name, Integer limit, Integer currentPage) {
        Connection collection = ConnectionDB.openConnection();
        List<Category> categories = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL CAT_FIND_PAGED(?, ?, ?)");
            callableStatement.setString(1, name);
            callableStatement.setInt(2, limit);
            callableStatement.setInt(3, currentPage);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                category.setStatus(resultSet.getBoolean("status"));

                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }

        return (int) Math.ceil((double) categories.size() / limit);
    }
}
