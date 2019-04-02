package com.cml.reco.recommand;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ItemInfoSubGraph {
	  private Set<String> items;
	  private Set<String> tags;
	  private ConcurrentHashMap<String,Set<String>> itemTagMap;
	  private ConcurrentHashMap<String, Integer> degrees;
	
	
	public ItemInfoSubGraph() {
		super();
		// TODO Auto-generated constructor stub
		items=Collections.newSetFromMap(new ConcurrentHashMap<>());
		tags=Collections.newSetFromMap(new ConcurrentHashMap<>());
		itemTagMap=new ConcurrentHashMap<>();
		degrees=new ConcurrentHashMap<>();
	}

	public ItemInfoSubGraph( Set<String> items,Set<String> tags,
			ConcurrentHashMap<String, Set<String>> itemTagMap,
			ConcurrentHashMap<String, Integer> degrees) {
		super();
		this.items = items;
		this.tags = tags;
		this.itemTagMap=itemTagMap;
		this.degrees = degrees;
	}
	
	public void print() {
		System.out.println("items:"+items.toString());
		System.out.println("tags:"+tags.toString());
		System.out.println("degrees:"+degrees.toString());
		System.out.println("itemTagMap:");
		for(String item:itemTagMap.keySet()) {
			System.out.println(item+":"+itemTagMap.toString());
		}
	}
	

	public Set<String> getItems() {
		return items;
	}

	public void setItems(Set<String> items) {
		this.items = items;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public ConcurrentHashMap<String, Set<String>> getItemTagMap() {
		return itemTagMap;
	}

	public void setItemTagMap(ConcurrentHashMap<String, Set<String>> itemTagMap) {
		this.itemTagMap = itemTagMap;
	}

	public ConcurrentHashMap<String, Integer> getDegrees() {
		return degrees;
	}

	public void setDegrees(ConcurrentHashMap<String, Integer> degrees) {
		this.degrees = degrees;
	}
	
}
