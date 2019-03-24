package com.example.demo.service;



import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demo.RecoApplication;
import com.example.demo.mapper.ItemInfoMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RecoApplication.class)
public class Test {
    @Autowired
    OrderService os;
    
    @Autowired
    ItemInfoService is;
    
    @Autowired
    RecoItemService rs;
    
    @Autowired
    ItemInfoMapper im;
	
	@org.junit.Test
	public void test() {
	  Set<String> tags=new HashSet<>();
	  Set<String> sideTags=new HashSet<>();
	  tags.add("Watches");
	  tags.add("Women's");
	  sideTags.add("Women's");
	  System.out.println(im.getSimilarItemInfosWithSideTags2(tags, sideTags, 1000, 200));

	   
	}
	
//	@org.junit.Test
//	public void test2() {
//		a=a+2;
//		System.out.println(a);
//	}
	
}
