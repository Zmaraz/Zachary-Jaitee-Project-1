package com.revature.dao;

import java.util.ArrayList;

public interface DAO<T> {
	
	ArrayList<T> getAll();
	T getById(int id);
	T add(T obj);
	ArrayList<T> update(T updatedObj);
	boolean delete(int id);
}