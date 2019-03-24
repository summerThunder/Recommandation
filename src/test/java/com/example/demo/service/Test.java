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
	  String s="D:\\test2\\A36U4FW2XQ12Z6\\recommand\\16GB Accessories Bundle Kit For Olympus Stylus Tough 8010 6020 TG-610 TG-810 TG-820 iHS, TG-830 iHS, TG-630 iHS Digital Camera 16GB High Speed SD Memory Card + Extended (1000maH) Replacement LI-50B Battery + AcDc Travel Charger + FLOAT STRAP + Case +More.jpg";
	  System.out.println(s.length());
	}
	
//	@org.junit.Test
//	public void test2() {
//		a=a+2;
//		System.out.println(a);
//	}
	
}
