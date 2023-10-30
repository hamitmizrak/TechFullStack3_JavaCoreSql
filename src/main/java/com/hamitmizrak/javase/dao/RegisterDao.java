package com.hamitmizrak.javase.dao;

import com.hamitmizrak.javase.dto.RegisterDto;
import com.hamitmizrak.javase.roles.ERoles;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/*
Transaction:  Create, Delete, Update
connection.setAutoCommit(false)  => Default: true
connection.commit();   ==> Başarılı
connection.rollback(); ==> Başarısız
 */

public class RegisterDao implements IDaoGenerics<RegisterDto>, Serializable {

    // SPEED DATA
    @Override
    public String speedData(Long id) {
        for (int i = 1; i <=id; i++) {
            try (Connection connection = getInterfaceConnection()) {
                // Manipulation: executeUpdate() [create, delete, update]
                // Sorgularda  : executeQuery [list, find]
                // Transaction:
                connection.setAutoCommit(false); //default:true
                String sql = "INSERT INTO `cars`.`register` (`nick_name`,`email_address`,`password`,`roles`,`remaining_number`,`is_passive`) \n" +
                        " VALUES ('?', '?', '?','?','?','?')";
                String rnd= UUID.randomUUID().toString();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "nickname() "+rnd);
                preparedStatement.setString(2, "emailAddress()"+rnd);
                preparedStatement.setString(3, "password()"+rnd);
                preparedStatement.setString(4, ERoles.USER.getValue());
                preparedStatement.setInt(5, 5);
                preparedStatement.setBoolean(6, true);
                // executeUpdate: create, delete, update için kullanılır.
                int rowsEffected = preparedStatement.executeUpdate();
                // eğer ekleme yapılmamışsa -1 değerini döner
                if (rowsEffected > 0) {
                    System.out.println(RegisterDao.class + " Başarılı Ekleme Tamamdır");
                    connection.commit(); // başarılı
                } else {
                    System.err.println(RegisterDao.class + " !!! Başarısız Ekleme Tamamdır");
                    connection.rollback(); // başarısız
                }
            } catch (SQLException sql) {
                sql.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println( id+ "tane veri eklendi");
        return id+ "tane veri eklendi";
    }

    // ALL DELETE
    @Override
    public String allDelete() {
        try (Connection connection = getInterfaceConnection()) {
            // Manipulation: executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            // Transaction:
            connection.setAutoCommit(false); //default:true
            String sql = "DELETE FROM `cars`.`register`";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer silme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(RegisterDao.class + " Başarılı Bütün Veriler Silme Tamamdır");
                connection.commit(); // başarılı
            } else {
                System.err.println(RegisterDao.class + " !!! Başarısız Bütün Silme Tamamdır");
                connection.rollback(); // başarısız
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list().size()+" tane veri silindi";
    }

    ////////////////////////////////////////////////////////
    // CREATE
    // INSERT INTO `cars`.`register` (`nick_name`,`email_address`,`password`,`roles`,`remaining_number`,`is_passive`)
    // VALUES ('computer', 'hamitmizrak@gmail.com', '123456','admin','5','1');
    @Override
    public RegisterDto create(RegisterDto registerDto) {
        try (Connection connection = getInterfaceConnection()) {
            // Manipulation: executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            // Transaction:
            connection.setAutoCommit(false); //default:true
            String sql = "INSERT INTO `cars`.`register` (`nick_name`,`email_address`,`password`,`roles`,`remaining_number`,`is_passive`) \n" +
                    " VALUES ('?', '?', '?','?','?','?')";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, registerDto.getuNickname());
            preparedStatement.setString(2, registerDto.getuEmailAddress());
            preparedStatement.setString(3, registerDto.getuPassword());
            preparedStatement.setString(4, registerDto.getRolles());
            preparedStatement.setInt(5, registerDto.getRemainingNumber());
            preparedStatement.setBoolean(6, registerDto.getPassive());
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer ekleme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(RegisterDao.class + " Başarılı Ekleme Tamamdır");
                connection.commit(); // başarılı
            } else {
                System.err.println(RegisterDao.class + " !!! Başarısız Ekleme Tamamdır");
                connection.rollback(); // başarısız
            }
            return  registerDto; // eğer başarılı ise return registerDto
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // FIND ID
    @Override
    public RegisterDto findById(Long id) {
        RegisterDto registerDto=null;
        try (Connection connection = getInterfaceConnection()) {
            // Dikkat: id Long olduğu için tırnak içinde yazmıyoruz  örneğin: id=1
            String sql = "SELECT * FROM cars.register where id="+id;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                // nick_name, email_address, password, roles, remaining_number, is_passive
                registerDto=new RegisterDto();
                registerDto.setId(resultSet.getLong("id"));
                registerDto.setuNickname(resultSet.getString("nick_name"));
                registerDto.setuEmailAddress(resultSet.getString("email_address"));
                registerDto.setuPassword(resultSet.getString("password"));
                registerDto.setRolles(resultSet.getString("roles"));
                registerDto.setRemainingNumber(resultSet.getInt("remaining_number"));
                registerDto.setPassive(resultSet.getBoolean("is_passive"));
                registerDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
            }
            return  registerDto; // eğer başarılı ise return registerDto
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // FIND EMAIL
    @Override
    public RegisterDto findByEmail(String email) {
        RegisterDto registerDto=null;
        try (Connection connection = getInterfaceConnection()) {
            // Dikkat: email_address String olduğu için tırnak içinde yazıyoruz örneğin: email="hamitmizrak@gmail.com"
            String sql = "SELECT * FROM cars.register where email_address='"+email+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                // nick_name, email_address, password, roles, remaining_number, is_passive
                registerDto=new RegisterDto();
                registerDto.setId(resultSet.getLong("id"));
                registerDto.setuNickname(resultSet.getString("nick_name"));
                registerDto.setuEmailAddress(resultSet.getString("email_address"));
                registerDto.setuPassword(resultSet.getString("password"));
                registerDto.setRolles(resultSet.getString("roles"));
                registerDto.setRemainingNumber(resultSet.getInt("remaining_number"));
                registerDto.setPassive(resultSet.getBoolean("is_passive"));
                registerDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
            }
            return  registerDto; // eğer başarılı ise return registerDto
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // LIST
    @Override
    public ArrayList<RegisterDto> list() {
        ArrayList<RegisterDto> list=new ArrayList<>();
        RegisterDto registerDto;
        try (Connection connection = getInterfaceConnection()) {
            // Dikkat: email_address String olduğu için tırnak içinde yazıyoruz örneğin: email="hamitmizrak@gmail.com"
            String sql = "SELECT * FROM cars.register";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                // nick_name, email_address, password, roles, remaining_number, is_passive
                registerDto=new RegisterDto();
                registerDto.setId(resultSet.getLong("id"));
                registerDto.setuNickname(resultSet.getString("nick_name"));
                registerDto.setuEmailAddress(resultSet.getString("email_address"));
                registerDto.setuPassword(resultSet.getString("password"));
                registerDto.setRolles(resultSet.getString("roles"));
                registerDto.setRemainingNumber(resultSet.getInt("remaining_number"));
                registerDto.setPassive(resultSet.getBoolean("is_passive"));
                registerDto.setSystemCreatedDate(resultSet.getDate("system_created_date"));
                list.add(registerDto);
            }
            return  list; // eğer başarılı ise return registerDto
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE
    @Override
    public RegisterDto update(Long id, RegisterDto registerDto) {
        try (Connection connection = getInterfaceConnection()) {
            // Manipulation: executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            // Transaction:
            connection.setAutoCommit(false); //default:true
            String sql = "UPDATE `cars`.`register` SET `nick_name`=?, `email_address`=?, `password`=?, `roles`=?, " +
                    "`remaining_number`=?, `is_passive`=?" +
                    " WHERE `id` =?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, registerDto.getuNickname());
            preparedStatement.setString(2, registerDto.getuEmailAddress());
            preparedStatement.setString(3, registerDto.getuPassword());
            preparedStatement.setString(4, registerDto.getRolles());
            preparedStatement.setInt(5, registerDto.getRemainingNumber());
            preparedStatement.setBoolean(6, registerDto.getPassive());
            preparedStatement.setLong(7, registerDto.getId());
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer güncelle yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(RegisterDao.class + " Başarılı Güncelleme Tamamdır");
                connection.commit(); // başarılı
            } else {
                System.err.println(RegisterDao.class + " !!! Başarısız Güncelleme Tamamdır");
                connection.rollback(); // başarısız
            }
            return  registerDto; // eğer başarılı ise return registerDto
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // DELETE
    @Override
    public RegisterDto deleteById(RegisterDto registerDto) {
        try (Connection connection = getInterfaceConnection()) {
            // Manipulation: executeUpdate() [create, delete, update]
            // Sorgularda  : executeQuery [list, find]
            // Transaction:
            connection.setAutoCommit(false); //default:true
            String sql = "DELETE FROM `cars`.`register` WHERE `id` = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, registerDto.getId());
            // executeUpdate: create, delete, update için kullanılır.
            int rowsEffected = preparedStatement.executeUpdate();
            // eğer silme yapılmamışsa -1 değerini döner
            if (rowsEffected > 0) {
                System.out.println(RegisterDao.class + " Başarılı Silme Tamamdır");
                connection.commit(); // başarılı
            } else {
                System.err.println(RegisterDao.class + " !!! Başarısız Silme Tamamdır");
                connection.rollback(); // başarısız
            }
            return  registerDto; // eğer başarılı ise return registerDto
        } catch (SQLException sql) {
            sql.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

} //end class RegisterDao
