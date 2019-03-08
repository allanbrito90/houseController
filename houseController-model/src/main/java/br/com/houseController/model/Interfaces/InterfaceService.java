package br.com.houseController.model.Interfaces;

import java.util.ArrayList;

public interface InterfaceService <T extends Object>{
	
	public Integer insert(T obj);
	
	public T findOne (T obj);
	
	public ArrayList<T> findAll();
	
	public Integer delete(int id);


}
