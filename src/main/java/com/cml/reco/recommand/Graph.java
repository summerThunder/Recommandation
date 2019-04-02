package com.cml.reco.recommand;
import com.cml.reco.entity.RecoItem;
import com.cml.reco.multiThread.Action;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import org.ejml.data.FMatrix;
import org.ejml.data.FMatrixD1;
import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.springframework.stereotype.Component;

import com.cml.reco.multiThread.MultiRead;




public class Graph {
  private int unix_time;
  private String user;
  private Set<String> itemSet;
  private Set<String> userSet;
  private Set<String> tagSet;
  private Set<String> sideTags;
  private Vector<String> items;
  private Vector<String> users;
  private Vector<String> tags;
  private ConcurrentHashMap<String, Integer> itemIndex;
  private ConcurrentHashMap<String, Integer> userIndex;
  private ConcurrentHashMap<String, Integer> tagIndex;
  private ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> userItemMap;
  private ConcurrentHashMap<String, Set<String>> itemTagMap;
  private ConcurrentHashMap<String, Integer> degrees;
  private Vector<RecoItem> topK;
  private Vector<String> topk;
  private int userItemCnt=-1;
  private int itemTagCnt=-1;
  private int userCnt=-1;
  private int itemCnt=-1;
  private int tagCnt=-1;
  private int nodeCnt=-1;
  private float alpha=0.8f;
  private float[] score;
  private Set<String> itemsFromOthers;
  private Comparator<String> cmp=new Comparator<String>() {


	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		float s1=score[itemIndex.get(o1)];
		float s2=score[itemIndex.get(o2)];
		return (s2-s1)>0?1:-1;
	}

//	@Override
//	public int compare(Integer o1, Integer o2) {
//		// TODO Auto-generated method stub
//		float s1=score[o1];
//		float s2=score[o2];
//		return (s2-s1)>0?1:-1;
//	}
	  
};
  
	public enum NodeClass{
		User,Tag,Item
	}
    
  
	public Graph(int unix_time,String user) {
		super();
		this.unix_time=unix_time;
		this.user = user;
		itemSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
		userSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
		tagSet=Collections.newSetFromMap(new ConcurrentHashMap<>());
		userItemMap=new ConcurrentHashMap<>();
		itemTagMap=new ConcurrentHashMap<>();
		degrees=new ConcurrentHashMap<>();
				
	}
	private void userItemMapMerge(ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> mergeMap) {
		MultiRead mr=new MultiRead(mergeMap, new Action() {
	
			@Override
			public void action(Object o) {
				// TODO Auto-generated method stub
				String user=(String)o;
				
				userItemMap.putIfAbsent(user, new ConcurrentHashMap<>());
				for(String item:mergeMap.get(user).keySet()) {
					int times=mergeMap.get(user).get(item);
					userItemMap.get(user).putIfAbsent(item, 0);
					userItemMap.get(user).compute(item, (k,v)->(v+times));
				}
			}
		});
		mr.run();
	}
	private void itemTagMapMerge(ConcurrentHashMap<String, Set<String>> mergeMap) {
		MultiRead mr=new MultiRead(mergeMap, new Action() {
			
			@Override
			public void action(Object o) {
				// TODO Auto-generated method stub
				String item=(String)o;
				itemTagMap.putIfAbsent(item, Collections.newSetFromMap(new ConcurrentHashMap<>()));
				itemTagMap.get(item).addAll(mergeMap.get(item));
			}
		});
		mr.run();
	}
	private void degreesMerge(ConcurrentHashMap<String, Integer> mergeMap) {
	   MultiRead mr=new MultiRead(mergeMap, new Action() {
			
			@Override
			public void action(Object o) {
				// TODO Auto-generated method stub
				String node=(String)o;
				degrees.putIfAbsent(node, 0);
				degrees.compute(node,(k,v)->(v+mergeMap.get(node)));
			}
		});
	   mr.run();
	}
	
	public void mergeSideTags(Set<String> sideTags) {
		for(String sideTag:sideTags) {
			tagSet.add(sideTag);
			degrees.putIfAbsent(sideTag, 0);
			degrees.compute(sideTag, (k,v)->(v+1));
			
		}
		this.sideTags=sideTags;
		degrees.putIfAbsent(user, 0);
		degrees.compute(user,(k,v)->(v+sideTags.size()));
	}

	public void mergeOrderSubGraph(OrderSubGraph subGraph) {
		userItemMapMerge(subGraph.getUserItemMap());
		degreesMerge(subGraph.getDegrees());
		userSet.addAll(subGraph.getUsers());
		itemSet.addAll(subGraph.getItems());
	}
	
	
	public void mergeOtherOrderSubGraph(OrderSubGraph subGraph) {
		itemsFromOthers=subGraph.getItems();
		mergeOrderSubGraph(subGraph);
	}
	public void mergeItemInfoSubGraph(ItemInfoSubGraph subGraph) {
		itemTagMapMerge(subGraph.getItemTagMap());
		degreesMerge(subGraph.getDegrees());
		tagSet.addAll(subGraph.getTags());
		itemSet.addAll(subGraph.getItems());
	}
	
//	public int userItemCount(ConcurrentHashMap<String,ConcurrentHashMap<String,Integer>> chm) {
//		if(userItemCnt==-1) {
//		LongAdder lad=new LongAdder();
//		MultiRead mr=new MultiRead(chm, new Action() {
//			
//			@Override
//			public void action(Object o) {
//				// TODO Auto-generated method stub
//				lad.add(chm.get(o).keySet().size());
//			}
//		});
//		userItemCnt=(int) lad.sum();
//		}
//		return userItemCnt;
//	}
	
	public int itemTagCount() {
		if(itemTagCnt==-1) {
		LongAdder lad=new LongAdder();
		MultiRead mr=new MultiRead(itemTagMap, new Action() {
			
			@Override
			public void action(Object o) {
				// TODO Auto-generated method stub
				lad.add(itemTagMap.get(o).size());
			}
		});
		mr.run();
		itemTagCnt=(int) lad.sum();
		}
		return itemTagCnt;
	}
	
	public int userItemCount() {
		if(userItemCnt==-1) {
		LongAdder lad=new LongAdder();
		MultiRead mr=new MultiRead(userItemMap, new Action() {
			
			@Override
			public void action(Object o) {
				// TODO Auto-generated method stub
				lad.add(userItemMap.get(o).keySet().size());
			}
		});
		mr.run();
		userItemCnt=(int) lad.sum();
		}
		return userItemCnt;
	}
	
	public void print() {
		System.out.println("user:"+user);
		System.out.println("itemSet:"+itemSet.size());
		System.out.println("userSet:"+userSet.size());
		System.out.println("tagSet:"+tagSet.size());
		System.out.println("itemTagCount:"+itemTagCount());
		System.out.println("userItemCount:"+userItemCount());
		System.out.println("userItemMap:");
		for(String user:userItemMap.keySet()) {
			System.out.println(user+":"+userItemMap.get(user).keySet().toString());
		}
		System.out.println("itemTagMap:");
		for(String user:itemTagMap.keySet()) {
			System.out.println(user+":"+itemTagMap.get(user).toString());
		}
		
		
	}
	public Integer getNodeId(String name,NodeClass c) {
    	try {
		if(c==NodeClass.User) {
			return userIndex.get(name);
		}
		else if(c==NodeClass.Item) {
			return itemIndex.get(name)+userCnt;
		}
		else {
			return tagIndex.get(name)+itemCnt+userCnt;
		}
    	}catch(NullPointerException e){
    		e.printStackTrace();
    		System.out.println(name);
    	
    		
    		return null;
    	}
    	
    }
	public void buildIndexes() {
		userCnt=userSet.size();
		users=new Vector<>(userCnt);
//		userIndex=new ConcurrentHashMap<>(userSet.size());
		itemCnt=itemSet.size();
		items=new Vector<>(itemCnt);
//		itemIndex=new ConcurrentHashMap<>(itemSet.size());
		tagCnt=tagSet.size();
		tags=new Vector<>(tagCnt);
		nodeCnt=itemCnt+userCnt+tagCnt;
//		tagIndex=new ConcurrentHashMap<>(itemSet.size());
		
		userSet.remove(this.user);
		users.addElement(this.user);
		users.addAll(userSet);
		
		items.addAll(itemSet);
		
		tags.addAll(tagSet);
		
		userIndex=new ConcurrentHashMap<>(users.stream().collect(Collectors.toMap(k->k, k->users.indexOf(k),(k1,k2)->k1)));
		itemIndex=new ConcurrentHashMap<>(items.stream().collect(Collectors.toMap(k->k, k->items.indexOf(k),(k1,k2)->k1)));
		tagIndex=new ConcurrentHashMap<>(tags.stream().collect(Collectors.toMap(k->k, k->tags.indexOf(k),(k1,k2)->k1)));
		
	
		
	}

		public FMatrixRMaj buildMatrix() {
//    	System.out.println("开始构建矩阵");
    	FMatrixRMaj matrix=new FMatrixRMaj(nodeCnt,nodeCnt);
    	MultiRead mr1=new MultiRead(userItemMap, new Action() {
			
			@Override
			public void action(Object o) {
				// TODO Auto-generated method stub
				String user=(String)o;
				int uid=getNodeId(user, NodeClass.User);
				for(String item:userItemMap.get(user).keySet()) {
					int iid=getNodeId(item, NodeClass.Item);
					int times=userItemMap.get(user).get(item);
					matrix.set(uid, iid, (float)-alpha*1/degrees.get(item)*times);
					matrix.set(iid, uid, (float)-alpha*1/degrees.get(user)*times);
				}
			}
		});
    	mr1.run();
       
    	MultiRead mr2=new MultiRead(itemTagMap, new Action() {
			
			@Override
			public void action(Object o) {
				// TODO Auto-generated method stub
				String item=(String)o;
				int iid=getNodeId(item, NodeClass.Item);
				for(String tag:itemTagMap.get(item)) {
					int tid=getNodeId(tag, NodeClass.Tag);
					
					matrix.set(iid, tid, (float)-alpha*1/degrees.get(tag));
					matrix.set(tid, iid, (float)-alpha*1/degrees.get(item));
				}
			}
		});
    	mr2.run();
        
    	int uid=getNodeId(user,NodeClass.User);
        for(String sideTag:sideTags) {
        	int tid=getNodeId(sideTag, NodeClass.Tag);
        	matrix.add(tid,uid,(float)-alpha*1/degrees.get(user));
        	matrix.add(uid,tid,(float)-alpha*1/degrees.get(sideTag));
        }
  
 
		return matrix;
        

    }
	public  void recommand() {
		if(userIndex==null) {
			buildIndexes();
		}	
	    FMatrixRMaj matrix=buildMatrix();
//        System.out.println("开始推荐");
        
        
        CommonOps_FDRM ops=new CommonOps_FDRM();
        
//        csc.print();
        FMatrixRMaj A_inv=matrix.createLike();
//        System.out.println("开始加上单位矩阵");
        ops.add(1, matrix,1, ops.identity(nodeCnt,nodeCnt), A_inv);

        


		 
		 FMatrixRMaj A=new FMatrixRMaj(nodeCnt,nodeCnt);
		 
//		 System.out.println("开始矩阵求逆");
		 ops.invert(A_inv,A);
		 
		
		 FMatrixD1 reco=ops.extract(A,userCnt,userCnt+itemCnt,0,1);
		 score=reco.getData();
}
	public Vector<RecoItem> topK(int k){
		
		if(topK==null) {
			recommand();
			topK=new Vector<>(k);
			topk=new Vector<>(k);
			PriorityBlockingQueue<String> pbq=new PriorityBlockingQueue<>(itemSet.size(),cmp);
			MultiRead mr=new MultiRead(itemIndex.keySet(), new Action() {
				
				@Override
				public void action(Object o) {
					// TODO Auto-generated method stub
					pbq.add((String)o);
				}
			});
			mr.run();
			int rank=1;
		
			while(rank<=k) {
				if(pbq.isEmpty()) {
					break;
				}
				String prod_asin=pbq.poll();
				if(!userItemMap.get(this.user).containsKey(prod_asin)) {
					String source=itemsFromOthers.contains(prod_asin)?"Users":"Items";
					topK.addElement(new RecoItem(this.user, prod_asin, rank, unix_time,source));
					topk.addElement(prod_asin);
					rank+=1;
					System.out.println(prod_asin+":"+score[itemIndex.get(prod_asin)]);
				}
			}
		}
		return topK;
	}
	public int getUnix_time() {
		return unix_time;
	}
	public String getUser() {
		return user;
	}
	public Set<String> getItemSet() {
		return itemSet;
	}
	public Set<String> getUserSet() {
		return userSet;
	}
	public Set<String> getTagSet() {
		return tagSet;
	}
	public Vector<String> getItems() {
		return items;
	}
	public Vector<String> getUsers() {
		return users;
	}
	public Vector<String> getTags() {
		return tags;
	}
	public ConcurrentHashMap<String, Integer> getItemIndex() {
		return itemIndex;
	}
	public ConcurrentHashMap<String, Integer> getUserIndex() {
		return userIndex;
	}
	public ConcurrentHashMap<String, Integer> getTagIndex() {
		return tagIndex;
	}
	public ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> getUserItemMap() {
		return userItemMap;
	}
	public ConcurrentHashMap<String, Set<String>> getItemTagMap() {
		return itemTagMap;
	}
	public Vector<RecoItem> getTopK() {
		return topK;
	}
	public Vector<String> getTopk() {
		return topk;
	}
	public int getUserItemCnt() {
		return userItemCnt;
	}
	public int getItemTagCnt() {
		return itemTagCnt;
	}
	public int getUserCnt() {
		return userCnt;
	}
	public int getItemCnt() {
		return itemCnt;
	}
	public int getTagCnt() {
		return tagCnt;
	}
	public int getNodeCnt() {
		return nodeCnt;
	}
	public float[] getScore() {
		return score;
	}
	
	 













  
}
