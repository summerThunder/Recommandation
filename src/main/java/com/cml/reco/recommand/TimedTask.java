package com.cml.reco.recommand;

import java.io.File;
import java.util.Collections;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cml.reco.entity.RecoItem;
import com.cml.reco.multiThread.Action;
import com.cml.reco.multiThread.MultiRead;
import com.cml.reco.service.ItemInfoService;
import com.cml.reco.service.OrderService;
import com.cml.reco.service.RecoItemService;
import com.cml.reco.utils.ApplicationContextProvider;


public class TimedTask extends Task{

    private String savePath;
    
    
    public TimedTask(String savePath) {
		super();
		this.savePath = savePath;
	}
   
    public TimedTask() {
 		super();
 		this.savePath = null;
 	}

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
				graph.mergeSideTags(sideTags);
				graph.mergeOrderSubGraph(orderGraph1);
				graph.mergeOtherOrderSubGraph(orderGraph2);
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
//				recoNames=recoItems.stream().map(RecoItem::getProd_asin).collect(Collectors.toSet());
				rs.SaveRecoItems(recoItems);
				
                if(savePath!=null) {
                   
                 	   File saveFile=new File(savePath);
                 	   if(!saveFile.exists()) {
                 		   saveFile.mkdirs();
                 	   }
				rs.savePics(user,items,recoNames,itemsOfSimilarUsers,savePath);
                }
			graph=null;
				System.gc();
			}
		});
    	mr.run();
    }

	
    
}
