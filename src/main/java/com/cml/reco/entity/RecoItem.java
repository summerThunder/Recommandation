package com.cml.reco.entity;

public class RecoItem {
    private String user_id;
    private String prod_asin;
    private int rank;
    private int update_time;
    public RecoItem(String user_id, String prod_asin, int rank, int update_time, String source) {
		super();
		this.user_id = user_id;
		this.prod_asin = prod_asin;
		this.rank = rank;
		this.update_time = update_time;
		this.source = source;
	}
	private String source;
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public void setUpdate_time(int update_time) {
		this.update_time = update_time;
	}
	public RecoItem(String user_id, String prod_asin, int rank, int update_time) {
		super();
		this.user_id = user_id;
		this.prod_asin = prod_asin;
		this.rank = rank;
		this.update_time = update_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getProd_asin() {
		return prod_asin;
	}
	public void setProd_asin(String prod_asin) {
		this.prod_asin = prod_asin;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getUpdate_time() {
		return update_time;
	}
	public void setUnix_time(int update_time) {
		this.update_time = update_time;
	}
    public String toString() { 
    	String su="user_id:"+user_id+",";
    	String sp="prod_asin:"+prod_asin+",";
    	String sr="rank:"+rank+",";
    	String st="update_time:"+update_time;
    	return su+sp+sr+st;
    }
}
