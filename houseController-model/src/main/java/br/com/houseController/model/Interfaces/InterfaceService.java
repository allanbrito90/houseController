package br.com.houseController.model.Interfaces;

import java.util.ArrayList;

public interface InterfaceService <T extends Object>{
	
	public Integer insert(T obj);
	
	public Object findOne (Object obj);
	
	public ArrayList<T> findAll();
	
	public Integer delete(int id);


}
