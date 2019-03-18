package com.example.demo.mapper;

import java.util.Set;
import java.util.Vector;
import com.example.demo.entity.ItemInfo;
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
     