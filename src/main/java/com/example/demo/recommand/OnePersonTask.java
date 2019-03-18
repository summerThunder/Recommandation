package com.example.demo.recommand;

import java.util.Collections;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.example.demo.entity.RecoItem;

public class OnePersonTask extends Task{
	
	private String user;
	private int time;

	

	public OnePersonTask(String user, int time) {
		super();
		this.user = user;
		this.time = time;
	}



	@Override
	public void start() {
		// TODO Auto-generated method stub
		System.out.println(user+":");
		OrderSubGraph orderGraph1=os.getGraphByUser(user);
		Set<String> items=orderGraph1.getItems();
		OrderSubGraph orderGraph2=os.getSimilarOrdersGraph(items);
		Set<String> itemsOfSimilarUsers=orderGraph2.getItems();
		
		ItemInfoSubGraph itemInfoGraph1=is.getGraphByItems(items);
		Set<String> tags=itemInfoGraph1.getTags();
		ItemInfoSubGraph itemInfoGraph2=is.getSimialrItemInfoGraph(tags, itemsOfSimilarUsers.size());
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

		rs.SaveRecoItems(recoItems);
//		
		String path="D:\\test";
		rs.savePics(user,items,recoNames, path);
		graph=null;
		System.gc();
	}
	}
    
}
