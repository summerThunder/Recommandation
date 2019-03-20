package com.example.demo.controller;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;



import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.recommand.OnePersonTask;

import com.example.demo.recommand.Task;


@CrossOrigin
@RestController
public class RecoController {
  
//	@RequestMapping(value="/he",method=RequestMethod.GET)
//	public void hello(String he) {
//		System.out.println("he"+he);
//	}

 @RequestMapping(value="/reco",method=RequestMethod.GET)
   public int personalReco(String user_id){
	 Date date = new Date();  
	   DateFormat df1 = DateFormat.getDateInstance();
	   String date2=df1.format(date);
	   int time = 0;
	   try {
		time=(int) (df1.parse(date2).getTime()/1000);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return -1;
	}
	 try{
		 if(user_id!=null&&time!=0) {
	 Task t=new OnePersonTask(user_id, time);
	 t.start();
	 return 1;
	 }
	 }catch(Exception e){
		 e.printStackTrace();
		 return -1;
	 }
	 return 0;
	//"A2VPWMZSYDI267"
  
  
 }
 
}
