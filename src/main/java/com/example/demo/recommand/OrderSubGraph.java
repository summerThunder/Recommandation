package com.example.demo.recommand;

import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class OrderSubGraph {
	  private Set<String> items;
	  private Set<String> users;
	  private ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> userItemMap;
	  private ConcurrentHashMap<String, Integer> degrees;
	  
	public OrderSubGraph( Set<String> users,Set<String> items,
			ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> userItemMap,
			ConcurrentHashMap<String, Integer> degrees) {
		super();
		this.items = items;
		this.users = users;
		this.userItemMap = userItemMap;
		this.degrees = degrees;
	}
	
	public void print() {
		System.out.println("users:"+users.toString());
		System.out.println("items:"+items.toString());
		System.out.println("degrees:"+degrees.toString());
		System.out.println("userItemMap:");
		for(String user:userItemMap.keySet()) {
			System.out.println(user+":"+userItemMap.get(user).toString());
		}
	}
	public Set<String> getItems() {
		return items;
	}
	public void setItems(Set<String> items) {
		this.items = items;
	}
	public Set<String> getUsers() {
		return users;
	}
	public void setUsers(Set<String> users) {
		this.users = users;
	}
	public ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> getUserItemMap() {
		return userItemMap;
	}
	public void setUserItemMap(ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> userItemMap) {
		this.userItemMap = userItemMap;
	}
	public ConcurrentHashMap<String, Integer> getDegrees() {
		return degrees;
	}
	public void setDegrees(ConcurrentHashMap<String, Integer> degrees) {
		this.degrees = degrees;
	}
	  
}
