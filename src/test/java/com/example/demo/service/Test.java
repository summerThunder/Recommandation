package com.example.demo.service;



import java.io.File;
import java.util.Set;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demo.RecoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RecoApplication.class)
public class Test {
    @Autowired
    OrderService os;
    
    @Autowired
    ItemInfoService is;
    
    @Autowired
    RecoItemService rs;
	
	@org.junit.Test
	public void test() {
	   int time=os.getNewestTime();
	   Set<String> user=os.getNewestUsers(time);
	   
	   
	}
	
//	@org.junit.Test
//	public void test2() {
//		a=a+2;
//		System.out.println(a);
//	}
	
}
