package com.example.demo.recommand;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demo.RecoApplication;
import com.example.demo.entity.RecoItem;
import com.example.demo.service.ItemInfoService;
import com.example.demo.service.OrderService;
import com.example.demo.service.RecoItemService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RecoApplication.class)
public class GraphTest {
    @Autowired
    OrderService os;
    
    @Autowired
    ItemInfoService is;
    
    @Autowired
    RecoItemService rs;
    
//    @Test
//    public void test() {
//		// TODO Auto-generated method stub
//    	int time=os.getNewestTime();
//    	Set<String> users=os.getNewestUsers(time);
//		System.out.println(users.toString());
//	}
	@Test
	public void test2() {
		int time=os.getNewestTime();
		
		OrderSubGraph orderGraph1=os.getGraphByUser("A2WFSP36C14NHS");
		Set<String> items=orderGraph1.getItems();
		OrderSubGraph orderGraph2=os.getSimilarOrdersGraph(items);
		Set<String> itemsOfSimilarUsers=orderGraph2.getItems();
		
		

		ItemInfoSubGraph itemInfoGraph1=is.getGraphByItems(items);
		Set<String> tags=itemInfoGraph1.getTags();
		ItemInfoSubGraph itemInfoGraph2=is.getSimialrItemInfoGraph(tags, itemsOfSimilarUsers.size());
		ItemInfoSubGraph itemInfoGraph3=is.getRestItemInfoGraph(itemsOfSimilarUsers, tags);
		
	
//		
		System.out.println("orderGraph1:"+orderGraph1.getItems().size());
		System.out.println("orderGraph2:"+orderGraph2.getItems().size());
		System.out.println("itemInfoGraph1:"+itemInfoGraph1.getItems().size());
		System.out.println("itemInfoGraph2:"+itemInfoGraph2.getItems().size());
		System.out.println(orderGraph2.getUsers().toString());
		System.out.println(orderGraph1.getItems().toString());
		
		Graph graph=new Graph(time,"A2WFSP36C14NHS");
		graph.mergeOrderSubGraph(orderGraph1);
		graph.mergeOrderSubGraph(orderGraph2);
		graph.mergeItemInfoSubGraph(itemInfoGraph1);
		graph.mergeItemInfoSubGraph(itemInfoGraph2);
		
		
//		graph.print();
//		System.out.println("++++++++++++++++++++++++++++++++++++");
		graph.mergeItemInfoSubGraph(itemInfoGraph3);
		System.out.println("graph:"+graph.getItemSet().size());
//		graph.print();

//		System.out.println(graph.getItemIndex().toString());
//		System.out.println(graph.getUserIndex().toString());
//		System.out.println(graph.getTagIndex().toString());
		graph.buildIndexes();
		System.out.println(graph.getItemIndex().toString());
		Vector<RecoItem> recoItems=graph.topK(16);
//		System.out.println(recoItems.toString());
		System.out.println("#######################################"+recoItems.toString());
		Set<String> recoNames=Collections.newSetFromMap(new ConcurrentHashMap<>());
		for(RecoItem ri:recoItems) {
			String item=ri.getProd_asin();
			recoNames.add(item);
			if(items.contains(item)) {
				System.out.println(item+":"+"have bought");
			}
			if(itemsOfSimilarUsers.contains(item)) {
				System.out.println(item+":"+"from users");
			}
			else {
				System.out.println(item+":"+"from tags");
			}
		}
		rs.SaveRecoItems(recoItems);
		
		String path="D:\\推荐结果";
		rs.savePics("AZMLVT2IHUFBJ",items,recoNames, path);
	

	}

}
