package com.cml.reco.mapper;


import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import com.cml.reco.entity.Order;

@Mapper
public interface OrderMapper {
	@Select("SELECT MAX(unix_time) FROM orders")
	int getUnixTime();
   
	@Select("SELECT user_id FROM orders where unix_time="
   		+ "#{time}")
   Set<String> getUsersBytime(int time);
   
   
   
   @Select("select * from orders where user_id=#{user}")
   Vector<Order> getOrdersByUser(@Param("user") String user);
   
   //根据商品获取覆盖率最大
   
   @Select("<script>"
   		+ "with t1 as (select user_id,count(*) as cnt from orders where prod_asin in"
   		+ "<foreach collection='items' item='item' open='(' separator=',' close=')'>"
   		+ "#{item}"
   		+ "</foreach>"
   		+ "group by user_id),"
   		+ "t2 as (select * from orders where user_id in (select t1.user_id from t1)),"
   		+ "t3 as (select user_id,count(*) as cnt from t2 group by user_id),"
   		+ "t4 as (select t1.user_id,(t1.cnt/(t3.cnt+#{itemSize}-t1.cnt)) as rate from t1,t3 where t1.user_id=t3.user_id "
   		+ "and #{itemSize}&lt;t3.cnt order by rate desc limit 10)"
   		+ " select * from t2 where user_id in (select user_id from t4)"
   		+ "</script>")
    Vector<Order> getSimilarOrders(@Param("items") Set<String> items,@Param("itemSize") int itemSize);
   

   
   
}
