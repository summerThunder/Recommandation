package com.example.demo.controller;

import java.util.Collections;

import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Order;
import com.example.demo.recommand.OnePersonTask;
import com.example.demo.recommand.OrderSubGraph;
import com.example.demo.recommand.Task;
import com.example.demo.service.OrderService;


@RestController
public class HelloController {


 @RequestMapping("/hello")
  public int test(){

	 Task t=new OnePersonTask("A2VPWMZSYDI267", 0);
	 t.start();
	
   
   return 1;
 }
 
}
