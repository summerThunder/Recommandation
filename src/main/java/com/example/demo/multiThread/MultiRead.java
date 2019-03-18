package com.example.demo.multiThread;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiRead {
  private Action action;
  private Collection collection;

  private Map map;
  
  public MultiRead(Collection c,Action a) {
	  action=a;
	  collection=c;
  }
  public MultiRead(Map m,Action a) {
	  action=a;
	  map=m;
  }
  public void run() {
	  ExecutorService pool=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	  if(collection!=null) {
	  for(Object o:collection) {
		  pool.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				action.action(o);
			 }
		  });
	  }
    }
	  else {
		  for(Object o:map.keySet()) {
			  pool.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					action.action(o);
				}
			});
		  }
	  }
	  pool.shutdown();
	  while(!pool.isTerminated()) {
		  
	  }
  }
}
