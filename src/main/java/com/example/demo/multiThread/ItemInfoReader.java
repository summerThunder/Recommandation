package com.example.demo.multiThread;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.example.demo.entity.ItemInfo;
import com.example.demo.recommand.ItemInfoSubGraph;
import com.example.demo.recommand.OrderSubGraph;


public class ItemInfoReader implements Action {
	private Set<String> tagSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
    private Set<String> itemSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
    private ConcurrentHashMap<String, Integer> degrees=new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Set<String>> itemTagMap
    =new ConcurrentHashMap<>();
    private ItemInfoSubGraph subGraph;
    public ItemInfoReader() {
		// TODO Auto-generated constructor stub
    	tagSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
    	itemSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
    	degrees=new ConcurrentHashMap<>();
    	itemTagMap
        =new ConcurrentHashMap<>();
    	
	}
	@Override
	public void action(Object o) {
		// TODO Auto-generated method stub
		ItemInfo itemInfo=(ItemInfo)o;
		String item=itemInfo.getProduct_asin();
		String tag=itemInfo.getCategory();
		tagSet.add(tag);
		itemSet.add(item);
		
		degrees.putIfAbsent(item, 0);
		degrees.compute(item, (k,v)->(v+1));
		degrees.putIfAbsent(tag, 0);
		degrees.compute(tag, (k,v)->(v+1));
		
		itemTagMap.putIfAbsent(item, Collections.newSetFromMap(new ConcurrentHashMap<>()));
		itemTagMap.get(item).add(tag);
		
	}
	public ItemInfoSubGraph getItemInfoSubGraph() {
		if(subGraph==null) {
			subGraph=new ItemInfoSubGraph(itemSet,tagSet,itemTagMap,degrees);
		}
		return subGraph;
	}
	public Set<String> getTagSet() {
		return tagSet;
	}
	public void setTagSet(Set<String> tagSet) {
		this.tagSet = tagSet;
	}
	public Set<String> getItemSet() {
		return itemSet;
	}
	public void setItemSet(Set<String> itemSet) {
		this.itemSet = itemSet;
	}
	public ConcurrentHashMap<String, Integer> getDegrees() {
		return degrees;
	}
	public void setDegrees(ConcurrentHashMap<String, Integer> degrees) {
		this.degrees = degrees;
	}
	public ConcurrentHashMap<String, Set<String>> getItemTagMap() {
		return itemTagMap;
	}
	public void setItemTagMap(ConcurrentHashMap<String, Set<String>> itemTagMap) {
		this.itemTagMap = itemTagMap;
	}

	

}
