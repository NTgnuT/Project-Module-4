package com.ra.model.dao.image;

import com.ra.model.dao.product.IProductDAO;
import com.ra.model.entity.Image;
import com.ra.util.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ImageDAO_Impl implements ImageDAO {
    @Autowired
    IProductDAO productDAO;
    @Override
    public List<Image> findAll() {
        Connection collection = ConnectionDB.openConnection();
        List<Image> images = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL show_image()");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Image image = new Image();
                image.setImageId(resultSet.getInt("id"));
                image.setImageUrl(resultSet.getString("name"));
                image.setProductId(productDAO.findById(resultSet.getInt("product_id")).getProductId());

                images.add(image);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return images;
    }

    @Override
    public boolean saveOrUpdate(Image image) {
        Connection connection = ConnectionDB.openConnection();
        int check;
        CallableStatement callableStatement;
        try {
            if (image.getImageId() == 0) {
                callableStatement = connection.prepareCall("CALL add_image(?, ?)");
                callableStatement.setString(1, image.getImageUrl());
                callableStatement.setInt(2, image.getProductId());
            } else {
                callableStatement = connection.prepareCall("CALL update_image(?, ?, ?)");
                callableStatement.setString(1, image.getImageUrl());
                callableStatement.setInt(2, image.getProductId());
                callableStatement.setInt(3, image.getImageId());
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
    public Image findById(Integer integer) {
        Connection connection = ConnectionDB.openConnection();
        Image image = new Image();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL find_by_id_image(?)");
            callableStatement.setInt(1, integer);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                image.setImageId(resultSet.getInt("id"));
                image.setImageUrl(resultSet.getString("name"));
                image.setProductId(productDAO.findById(resultSet.getInt("product_id")).getProductId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return image;
    }



    @Override
    public List<Image> findByName(String name) {
        return null;
    }

    @Override
    public List<Image> pagination(Integer limit, Integer currentPage) {
        return null;
    }

    @Override
    public List<Image> findListImage(Integer id) {
        Connection connection = ConnectionDB.openConnection();
        List<Image> listImage = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL find_by_id_image(?)");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Image image = new Image();
                image.setImageId(resultSet.getInt("id"));
                image.setImageUrl(resultSet.getString("name"));
                image.setProductId(productDAO.findById(resultSet.getInt("product_id")).getProductId());

                listImage.add(image);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return listImage;
    }

    @Override
    public int saveImage(Image image) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL add_image(?, ?)");
            callableStatement.setString(1, image.getImageUrl());
            callableStatement.setInt(2, image.getProductId());

            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return 1;
    }

    @Override
    public int updateImage(Image image) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL update_image(?, ?, ?)");
            callableStatement.setString(1, image.getImageUrl());
            callableStatement.setInt(2, image.getProductId());
            callableStatement.setInt(3, image.getImageId());

            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return 1;
    }

    @Override
    public void deleteImage(Integer id) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL delete_image(?)");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }
}
