package com.hamitmizrak.javase.dao;

import com.hamitmizrak.javase.dto.RegisterDto;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/*
Transaction:  Create, Delete, Update
connection.setAutoCommit(false)  => Default: true
connection.commit();   ==> Başarılı
connection.rollback(); ==> Başarısız
 */

public class RegisterDao implements IDaoGenerics<RegisterDto>, Serializable {

    @Override
    public String speedData(Long id) {
        return null;
    }

    @Override
    public String allDelete() {
        return null;
    }

    ////////////////////////////////////////////////////////
    // CREATE
    // INSERT INTO `cars`.`register` (`nick_name`,`email_address`,`password`,`roles`,`remaining_number`,`is_passive`)
    // VALUES ('computer', 'hamitmizrak@gmail.com', '123456','admin','5','1');
    @Override
    public RegisterDto create(RegisterDto registerDto) {
        try (Connection connection = getInterfaceConnection()) {
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

    // FIND
    @Override
    public RegisterDto findById(Long id) {
        return null;
    }

    @Override
    public RegisterDto findByEmail(String email) {
        return null;
    }

    // LIST
    @Override
    public ArrayList<RegisterDto> list() {
        return null;
    }

    // UPDATE
    @Override
    public RegisterDto update(Long id, RegisterDto registerDto) {
        try (Connection connection = getInterfaceConnection()) {
            // Transaction:
            connection.setAutoCommit(false); //default:true
            String sql = "UPDATE `cars`.`register` SET `nick_name`=?, `email_address`=?, `password`=?, `roles`=?, `remaining_number`=?, `is_passive`=?" +
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
            // Transaction:
            connection.setAutoCommit(false); //default:true
            String sql = "DELETE FROM `cars`.`register` WHERE (`id` = '1');";
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
