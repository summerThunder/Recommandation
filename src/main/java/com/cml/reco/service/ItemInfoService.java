package com.cml.reco.service;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cml.reco.entity.ItemInfo;
import com.cml.reco.entity.Order;
import com.cml.reco.mapper.ItemInfoMapper;
import com.cml.reco.multiThread.ItemInfoReader;
import com.cml.reco.multiThread.MultiRead;
import com.cml.reco.multiThread.OrderReader;
import com.cml.reco.recommand.ItemInfoSubGraph;

@Service
public class ItemInfoService {
      @Autowired
      ItemInfoMapper itemInfoMapper;
      
//      public ItemInfoSubGraph getSimialrItemInfoGraph(Set<String> tags,int k) {
//    	  if(k==0) {
//    		  k=20;
//    	  }
//    	  Vector<ItemInfo> itemInfos=itemInfoMapper.getSimilarItemInfos(tags, k);
//    	  ItemInfoReader iir=new ItemInfoReader();
//    	  MultiRead mr=new MultiRead(itemInfos, iir);
//    	  mr.run();
//    	  return iir.getItemInfoSubGraph();
//    	 
//      }
      
      //辅助标签版
      public ItemInfoSubGraph getSimialrItemInfoGraph(Set<String> tags,Set<String> sideTags,int k) {
    	  if(k<20) {
    		  k=20;
    	  }
    	  Vector<ItemInfo> itemInfos;
    	  if(!sideTags.isEmpty()) {
    	  itemInfos=itemInfoMapper.getSimilarItemInfosWithSideTags2(tags,sideTags, k*2,k);
    	  }
    	  else {
    		  itemInfos=itemInfoMapper.getSimilarItemInfos2(tags,k*2,k);
    	  }
    	  ItemInfoReader iir=new ItemInfoReader();
    	  MultiRead mr=new MultiRead(itemInfos, iir);
    	  mr.run();
    	  return iir.getItemInfoSubGraph();
    	 
      }
      
      public ItemInfoSubGraph getGraphByItems(Set<String> items) {
    	  Vector<ItemInfo> itemInfos=itemInfoMapper.getItemInfosByItems(items);
    	  ItemInfoReader iir=new ItemInfoReader();
    	  MultiRead mr=new MultiRead(itemInfos, iir);
    	  mr.run();
    	  return iir.getItemInfoSubGraph();
    	 
      }
      
      public ItemInfoSubGraph getRestItemInfoGraph(Set<String> items,Set<String> tags) {
    	  if(!items.isEmpty()) {
    	  Vector<ItemInfo> itemInfos=itemInfoMapper.getItemInfoByItemsTags(items, tags);
    	  ItemInfoReader iir=new ItemInfoReader();
    	  MultiRead mr=new MultiRead(itemInfos, iir);
    	  mr.run();
    	  return iir.getItemInfoSubGraph();
    	  }
    	  else {
    		  return new ItemInfoSubGraph();
    	  }
      }
      

      
}
