/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 *
 * @author Biju Ale
 */
public interface DAO {
    ArrayList<Object> getAll();
    ArrayList<Object> getAll(int id);
    Object get(int id);
    
    boolean insert(Object obj);
    boolean update(Object obj);
    boolean delete(Object obj);
}
