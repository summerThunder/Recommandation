package com.example.demo.entity;

public class Order {
   private String user_id;
   private String prod_asin;
   private int num;
 @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "("+user_id+","+prod_asin+","+num+")";
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
public void setPro_asin(String prod_asin) {
	this.prod_asin = prod_asin;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
   
   
}
