package com.cml.reco.service;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cml.reco.RecoApplication;
import com.cml.reco.entity.ItemInfo;
import com.cml.reco.mapper.ItemInfoMapper;
import com.cml.reco.recommand.ItemInfoSubGraph;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RecoApplication.class)
public class ItemInfoServiceTest extends ItemInfoService {
    @Autowired
	ItemInfoService itemInfoService=new ItemInfoService();
	@Test
	public void test() {
		Set<String> tags=new HashSet<>();
   	    tags.add("Cookbook Stands");
   	    tags.add("Home & Kitchen");
   	    tags.add("Commuter Mugs & Tumblers");
//        ItemInfoSubGraph isg=itemInfoService.getSimialrItemInfoGraph(tags,20);
//		isg.print();
		
	}
	
//	@Test
//	public void test2() {
//		Set<String> mainItems=new HashSet<>();
//   	    mainItems.add("B000BN94F8");
//        ItemInfoSubGraph isg=itemInfoService.getGraphByItems(mainItems);
//		isg.print();
//		
//	}

}
