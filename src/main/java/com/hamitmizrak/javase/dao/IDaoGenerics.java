package com.hamitmizrak.javase.dao;

import com.hamitmizrak.javase.database.DatabaseConnection;
import java.sql.Connection;
import java.util.ArrayList;

// INTERFACE
public interface IDaoGenerics <T> {

    public String speedData(Long id);
    public String allDelete();

    ////////////////////////////////////////////////////
    // Counter

    ///////////////////////////////////////////////////

    // C R U D
    // CREATE
    public T create(T t);

    // FIND BY ID
    public T findById(Long id);
    public T findByEmail(String email);

    // LIST
    public ArrayList<T> list();

    // UPDATE
    public T update(Long id, T t);

    // DELETE
    public T deleteById(T t);

    ///////////////////////////////////////////////////////
    default Connection getInterfaceConnection(){
        return DatabaseConnection.getInstance().getConnection();
    } //end body interface
} //end interface IDaoGenerics
