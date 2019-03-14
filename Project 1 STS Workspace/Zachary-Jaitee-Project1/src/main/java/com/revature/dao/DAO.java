package com.revature.dao;

import java.util.ArrayList;

public interface DAO<T> {
	
	ArrayList<T> getAll();
	T getById(int id);
	T add(T obj);
	boolean delete(int id);
}