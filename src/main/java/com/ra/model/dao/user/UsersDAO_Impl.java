package com.ra.model.dao.user;

import com.ra.model.entity.User;
import com.ra.util.ConnectionDB;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class UsersDAO_Impl implements IUsersDAO{
    @Override
    public List<User> findAll() {
        Connection collection = ConnectionDB.openConnection();
        List<User> users = new ArrayList<>();
        try {
            CallableStatement callableStatement = collection.prepareCall("CALL show_users()");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setAddress(resultSet.getString("address"));
                user.setStatus(resultSet.getBoolean("status"));
                user.setRole(resultSet.getBoolean("role"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(collection);
        }
        return users;
    }

    @Override
    public boolean saveOrUpdate(User user) {
        Connection connection = ConnectionDB.openConnection();
        int check;
        CallableStatement callableStatement;
        try {
            if (user.getUserId() == 0) {
                callableStatement = connection.prepareCall("CALL register_user(?, ?, ?, ?, ?, ?)");
                callableStatement.setString(1, user.getUserName());
                callableStatement.setString(2, user.getEmail());
                // Mã hóa mật khẩu.
                String hasPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(15));
                callableStatement.setString(3, hasPassword);
                callableStatement.setString(4, user.getAvatar());
                callableStatement.setString(5, user.getPhoneNumber());
                callableStatement.setString(6, user.getAddress());
            } else {
                callableStatement = connection.prepareCall("CALL update_user(?, ?, ?, ?, ?, ?)");
                callableStatement.setString(1, user.getUserName());
                callableStatement.setString(2, user.getEmail());
                callableStatement.setString(3, user.getAvatar());
                callableStatement.setString(4, user.getPhoneNumber());
                callableStatement.setString(5, user.getAddress());
                callableStatement.setInt(6, user.getUserId());
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
    public User findById(Integer id) {
        Connection connection = ConnectionDB.openConnection();
        User user = new User();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL find_by_id_user(?)");
            callableStatement.setInt(1, id);
            ResultSet resultSet =callableStatement.executeQuery();
            while (resultSet.next()) {
                user.setUserId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setAddress(resultSet.getString("address"));
                user.setStatus(resultSet.getBoolean("status"));
                user.setRole(resultSet.getBoolean("role"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return user;
    }

    @Override
    public List<User> findByName(String name) {
        return null;
    }

    @Override
    public List<User> pagination(Integer limit, Integer currentPage) {
        return null;
    }

    @Override
    public void changeStatus(Integer id) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL change_status_user(?)");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public void changeRole(Integer id) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL change_role_user(?)");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
    }

    @Override
    public User checkEmail(String email) {
        Connection connection = ConnectionDB.openConnection();
        User user = null;
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL check_email_user(?)");
            callableStatement.setString(1, email);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setAddress(resultSet.getString("address"));
                user.setStatus(resultSet.getBoolean("status"));
                user.setRole(resultSet.getBoolean("role"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection);
        }
        return user;
    }
}
