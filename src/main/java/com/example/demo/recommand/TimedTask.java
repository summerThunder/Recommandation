package com.example.demo.recommand;

import java.util.Collections;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.RecoItem;
import com.example.demo.multiThread.Action;
import com.example.demo.multiThread.MultiRead;
import com.example.demo.service.ItemInfoService;
import com.example.demo.service.OrderService;
import com.example.demo.service.RecoItemService;
import com.example.demo.utils.ApplicationContextProvider;


public class TimedTask extends Task{

    
    
    public void start() {
    	
    	
		int time = os.getNewestTime();
			

    	Set<String> users=os.getNewestUsers(time);
    	MultiRead mr=new MultiRead(users, new Action() {
            
			@Override
			public void action(Object o) {
				
				// TODO Auto-generated method stub
				String user=(String)o;
				System.out.println(user+":");
				OrderSubGraph orderGraph1=os.getGraphByUser(user);
				Set<String> items=orderGraph1.getItems();
				OrderSubGraph orderGraph2=os.getSimilarOrdersGraph(items);
				Set<String> itemsOfSimilarUsers=orderGraph2.getItems();
				
				ItemInfoSubGraph itemInfoGraph1=is.getGraphByItems(items);
				Set<String> tags=itemInfoGraph1.getTags();
				Set<String> sideTags=rs.getSideTags(user);
				tags.addAll(sideTags);
				ItemInfoSubGraph itemInfoGraph2=is.getSimialrItemInfoGraph(tags, sideTags,itemsOfSimilarUsers.size());
				ItemInfoSubGraph itemInfoGraph3=is.getRestItemInfoGraph(itemsOfSimilarUsers, tags);
				
				Graph graph=new Graph(time,user);
				graph.mergeOrderSubGraph(orderGraph1);
				graph.mergeOrderSubGraph(orderGraph2);
				graph.mergeItemInfoSubGraph(itemInfoGraph1);
				graph.mergeItemInfoSubGraph(itemInfoGraph2);
				graph.mergeItemInfoSubGraph(itemInfoGraph3);
				
				Vector<RecoItem> recoItems=graph.topK(16);
				Set<String> recoNames=Collections.newSetFromMap(new ConcurrentHashMap<>());
				
				for(RecoItem ri:recoItems) {
					String item=ri.getProd_asin();
					recoNames.add(item);
//					if(items.contains(item)) {
//						System.out.println(item+":"+"have bought");
//					}
//					if(itemsOfSimilarUsers.contains(item)) {
//						System.out.println(item+":"+"from users");
//					}
//					else {
//						System.out.println(item+":"+"from tags");
//					}
				}
				rs.SaveRecoItems(recoItems);
				
				String path="D:\\推荐结果";
				rs.savePics(user,items,recoNames, path);
				graph=null;
				System.gc();
			}
		});
    	mr.run();
    }

	
    
}
