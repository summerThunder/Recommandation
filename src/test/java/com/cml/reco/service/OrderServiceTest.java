package com.cml.reco.service;

import static org.junit.Assert.*;

import java.nio.charset.MalformedInputException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cml.reco.RecoApplication;
import com.cml.reco.mapper.OrderMapper;
import com.cml.reco.recommand.OrderSubGraph;
import com.cml.reco.service.OrderService;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RecoApplication.class)
public class OrderServiceTest {


	@Autowired
	OrderService orderService=new OrderService();
	
	Set<String> mainUsers;

	

//	
//	@Test
//	public void test2() {
//		// TODO Auto-generated method stub
//		//测试用A1T7EHCMZ92TKC user_id
//		
//		OrderSubGraph osg=orderService.getGraphByUser("A48V0YVLJY0LG");
//		osg.print();
//		
//		
//	}
	
	@Test
	public void test3() {
		// TODO Auto-generated method stub
		//测试用A48V0YVLJY0LG user_id
		
		int time=orderService.getNewestTime();
		Set<String> users=orderService.getNewestUsers(time);
		System.out.println(users.toString());
		
	}
	
	
	

}
