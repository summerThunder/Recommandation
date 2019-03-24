package com.example.demo.mapper;

import java.util.Set;
import java.util.Vector;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Product;
import com.example.demo.entity.Product;
import com.example.demo.entity.RecoItem;

@Mapper
public interface RecoItemMapper {
   @Insert("<script>"
   		+ "replace into personal_recom(user_id,prod_asin,reco_rank,update_time,source) values"
   		+ "<foreach collection='recoItems' item='recoItem'  separator=','>"
   		+ "(#{recoItem.user_id},#{recoItem.prod_asin},#{recoItem.rank},#{recoItem.update_time},#{recoItem.source})"
   		+ "</foreach>"
   		+ "</script>")
   public void insertRecoItems(@Param("recoItems")Vector<RecoItem> recoItems);
   
   @Insert("replace into personal_recom(user_id,prod_asin,reco_rank,update_time，source) values"
   		+ "(#{recoItem.user_id},#{recoItem.prod_asin},#{recoItem.rank},#{recoItem.update_time},"
   		+ "#{recoItem.source})")
   public void insertRecoItem(@Param("recoItem") RecoItem recoItem);

   
   @Select("<script>"
   		+ "select asin,imUrl,title from product where asin in("
   		+ "<foreach collection='items' item='item' separator=','>"
   		+ "#{item}"
   		+ "</foreach>)"
   		+ "</script>")
   public Vector<Product> getProducts(@Param("items") Set<String>items);
   
   @Select("select ifnull(gender,-1) from customer where user_id=#{user_id}")
   public int getGender(@Param("user_id") String user_id);
   
   
 }
