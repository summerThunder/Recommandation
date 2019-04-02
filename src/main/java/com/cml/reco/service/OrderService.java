package com.cml.reco.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cml.reco.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cml.reco.mapper.OrderMapper;
import com.cml.reco.multiThread.MultiRead;
import com.cml.reco.multiThread.OrderReader;
import com.cml.reco.recommand.OrderSubGraph;

@Service
public class OrderService {
	
  @Autowired
  private OrderMapper orderMapper;
  
  public int getNewestTime(){

	  return orderMapper.getUnixTime();
  }
  
  public Set<String> getNewestUsers(int time){
	  return orderMapper.getUsersBytime(time);
  }
  
 public OrderSubGraph getSimilarOrdersGraph(Set<String> items) {
	  Vector<Order> orders=orderMapper.getSimilarOrders(items,items.size());
	  OrderReader or=new OrderReader();
	  MultiRead mr=new MultiRead(orders, or);
	  mr.run();
	  return or.getOrderSubGraph();
}
 
  public OrderSubGraph getGraphByUser(String user) {
	  Vector<Order> orders=orderMapper.getOrdersByUser(user);
	  OrderReader or=new OrderReader();
	  MultiRead mr=new MultiRead(orders, or);
	  mr.run();
	  return or.getOrderSubGraph();
  }
//  
//  public Set<String> getSimilarUserByItems(Set<String> items){
//	  Set<String> similarUsers=Collections.newSetFromMap(
//			  new ConcurrentHashMap<>());
//	  
//	  similarUsers.addAll(orderMapper.getSimilarUsersByItems(items));
//	  return similarUsers;
//  }
  
  
  
//  public  OrderSubGraph addNewestOrders(){
//	  Set<String> userSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
//	  Set<String> itemSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
//	  ConcurrentHashMap<String, Integer> degrees=new ConcurrentHashMap<>();
//	  ConcurrentHashMap<String, ConcurrentHashMap<String,Integer>> userItemMap
//		=new ConcurrentHashMap<>();
//	  Vector<Order> newestOrders=orderMapper.getNewestOrders();
//	  OrderReader newestOrderReader=new OrderReader(userSet,itemSet,degrees,userItemMap);
//	  MultiRead newestMultiOrderReader=new MultiRead(newestOrders, newestOrderReader);
//	  newestMultiOrderReader.run();
//	  return newestOrderReader.getOrderSubGraph();
//	  
//  }
//  public Vector<Order> getOrdersOfSameItems(Vector<String> items){
//	  return orderMapper.getOrdersByItem(items);
//  }
//  public Vector<Order> getOrderOfSimilarUser(Vector<String> users){
//	  return orderMapper.getOrdersByUser(users);
//  }

public OrderMapper getOrderMapper() {
	return orderMapper;
}
public void setOrderMapper(OrderMapper orderMapper) {
	this.orderMapper = orderMapper;
}


  
  

  
}
