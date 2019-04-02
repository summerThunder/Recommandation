package com.cml.reco.controller;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.cml.reco.recommand.OnePersonTask;

import com.cml.reco.recommand.Task;


@CrossOrigin
@RestController
public class RecoController {
  
//	@RequestMapping(value="/he",method=RequestMethod.GET)
//	public void hello(String he) {
//		System.out.println("he"+he);
//	}

 @RequestMapping(value="/reco",method=RequestMethod.GET)
   public Map<String,Boolean> personalReco(String user_id){
	   Map<String,Boolean> re=new HashMap<>();
	 
	   
	   
	   try {
		   
		   new Thread() {
			 @Override
			public void run() {
				// TODO Auto-generated method stub
				 Date date = new Date();  
				 DateFormat df1 = DateFormat.getDateInstance();
				 String date2=df1.format(date);
				 int time = 0;
				 try {
					time=(int) (df1.parse(date2).getTime()/1000);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 Task t=new OnePersonTask(user_id, time);
				 t.start();
			}
		   }.start();
		 
		   re.put("success", true);
	   }catch(Exception e) {
	     e.printStackTrace();
	     re.put("success", false);
	   }
	   
	   return re;
	   
	 
	//"A2VPWMZSYDI267"
  
  
 }
 
}
