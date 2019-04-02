package com.cml.reco.multiThread;


import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.cml.reco.entity.Order;
import com.cml.reco.recommand.OrderSubGraph;


public class OrderReader implements Action{
    private Set<String> userSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
    private Set<String> itemSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
    private ConcurrentHashMap<String, Integer> degrees=new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, ConcurrentHashMap<String,Integer>> userItemMap
    =new ConcurrentHashMap<>();
    private OrderSubGraph subGraph;
    public OrderReader() {
		// TODO Auto-generated constructor stub
    	userSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
    	itemSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
    	degrees=new ConcurrentHashMap<>();
    	userItemMap
        =new ConcurrentHashMap<>();
    	
	}

	@Override
	public void action(Object o) {
		// TODO Auto-generated method stub
		Order order=(Order)o;
		String user=order.getUser_id();
		String item=order.getProd_asin();
		int times=order.getNum();
		userSet.add(user);
		itemSet.add(item);
		
		degrees.putIfAbsent(user, 0);
		degrees.compute(user, (k,v)->(v+times));
		degrees.putIfAbsent(item, 0);
		degrees.compute(item, (k,v)->(v+times));
		
		userItemMap.putIfAbsent(user, new ConcurrentHashMap<String,Integer>());
		userItemMap.get(user).putIfAbsent(item, 0);
		userItemMap.get(user).compute(item,(k,v)->(v+times));
		
	}
	public OrderSubGraph getOrderSubGraph() {
		if(subGraph==null) {
			subGraph=new OrderSubGraph(userSet,itemSet,userItemMap,degrees);
		}
		return subGraph;
	}
	public Set<String> getItemSet() {
		return itemSet;
	}
	public void setItemSet(Set<String> itemSet) {
		this.itemSet = itemSet;
	}
	public ConcurrentHashMap<String, Integer> getDegrees() {
		return degrees;
	}
	public void setDegrees(ConcurrentHashMap<String, Integer> degrees) {
		this.degrees = degrees;
	}
	public ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> getUserItemMap() {
		return userItemMap;
	}
	public void setUserItemMap(ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> userItemMap) {
		this.userItemMap = userItemMap;
	}
	

}
