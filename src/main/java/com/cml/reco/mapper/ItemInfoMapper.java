package com.cml.reco.mapper;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import com.cml.reco.entity.ItemInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemInfoMapper {
    //查找与一个列表里的商品标签覆盖数最大的前20个商品
	@Select("<script>"
			+ "with t1 as (select * from prodcate where category in "
			+"<foreach collection='tags' item='tag' open='(' separator=',' close=')'>"
            +"#{tag}"
            +"</foreach>"
			+ "),"
			+ "t2 as (select product_asin from t1 GROUP BY product_asin order by count(*) desc limit #{k})"
			+ "select * from t1 where product_asin in (select product_asin from t2)" 
		    +"</script>")
	 Vector<ItemInfo> getSimilarItemInfos(@Param("tags") Set<String> tags,@Param("k")int k);
	
	
	@Select("<script>"
			+ "with t1 as (select * from prodcate where category in "
			+"<foreach collection='tags' item='tag' open='(' separator=',' close=')'>"
            +"#{tag}"
            +"</foreach>"
			+ "),"
			+"t2 as(select product_asin from t1 where product_asin not in(select product_asin from t1 where category not in"
			+"<foreach collection='sideTags' item='sideTag' open='(' separator=',' close=')'>"
            +"#{sideTag}"
            +"</foreach>"	
			+ ")),"
			+"t3 as(select product_asin from t1 where product_asin not in (select product_asin from t2) GROUP BY product_asin order by count(*) desc limit 1000)"
			+"select * from t1 where product_asin in (select product_asin from t3)"
		    +"</script>")
	 Vector<ItemInfo> getSimilarItemInfosWithSideTags(@Param("tags") Set<String> tags,@Param("sideTags") Set<String> sideTags,
			 @Param("k")int k);
	
	//更高效的比例写法
	@Select("<script>"
			+"with t1 as (select *  from prodcate where category in "
			+"<foreach collection='tags' item='tag' open='(' separator=',' close=')'>"
            +"#{tag}"
            +"</foreach>"
			+ "),"
            +"t2 as(select product_asin,count(*) as cnt from t1 group by product_asin order by cnt DESC limit #{k1}),"
			+"t3 as (select product_asin,count(*) as cnt from prodcate where product_asin in (select product_asin from t2) group by product_asin),"
			+"t6 as(select t2.product_asin from t2,t3 where t2.product_asin=t3.product_asin order by (t2.cnt/(3+t3.cnt-t2.cnt)) desc limit #{k2})"
			+"select * from t1 where product_asin in(select product_asin from t6)"
			+"</script>")
	Vector<ItemInfo> getSimilarItemInfos2(@Param("tags") Set<String> tags,@Param("k1")int k1,@Param("k2")int k2);
	
	
	@Select("<script>"
			+"with t1 as (select *  from prodcate where category in "
			+"<foreach collection='tags' item='tag' open='(' separator=',' close=')'>"
            +"#{tag}"
            +"</foreach>"
			+ "),"
            +"t4 as(select product_asin from t1 where product_asin not in (select product_asin from t1 where category not in "
            +"<foreach collection='sideTags' item='sideTag' open='(' separator=',' close=')'>"
            +"#{sideTag}"
            +"</foreach>"	
            + ")),"
            +"t5 as(select * from t1 where product_asin not in (select product_asin from t4)),"
            +"t2 as(select product_asin,count(*) as cnt from t5 group by product_asin order by cnt DESC limit #{k1}),"
			+"t3 as (select product_asin,count(*) as cnt from prodcate where product_asin in (select product_asin from t2) group by product_asin),"
            +"t6 as(select t2.product_asin from t2,t3 where t2.product_asin=t3.product_asin order by (t2.cnt/(3+t3.cnt-t2.cnt)) desc limit #{k2})"
			+"select * from t5 where product_asin in (select product_asin from t6)"
			+"</script>")
	 Vector<ItemInfo> getSimilarItemInfosWithSideTags2(@Param("tags") Set<String> tags,@Param("sideTags") Set<String> sideTags,
			 @Param("k1")int k1, @Param("k2")int k2);
	
	//根据一堆商品获取信息
	@Select("<script>"
			+ "select * from prodcate where product_asin in"
			+"<foreach collection='items' item='item' open='(' separator=',' close=')'> "
            +"#{item} "
            +"</foreach> "
            +"</script>")
	 Vector<ItemInfo> getItemInfosByItems(@Param("items") Set<String> items);
	
	@Select("<script>"
			+ "select * from prodcate where product_asin in"
			+"<foreach collection='items' item='item' open='(' separator=',' close=')'> "
            +"#{item} "
            +"</foreach>"
            + "and category in"
        	+"<foreach collection='tags' item='tag' open='(' separator=',' close=')'> "
            +"#{tag} "
            +"</foreach>"
            +"</script>"
			)
	 Vector<ItemInfo> getItemInfoByItemsTags(@Param("items") Set<String> items,@Param("tags") Set<String> tags);

}
     