package com.example.demo.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.entity.Product;
import com.example.demo.entity.RecoItem;
import com.example.demo.mapper.RecoItemMapper;
import com.example.demo.multiThread.Action;
import com.example.demo.multiThread.MultiRead;

@Service
public class RecoItemService {
   @Autowired
   private RecoItemMapper recoItemMapper;
   
   public void SaveRecoItems(Vector<RecoItem> recoItems) {
	   try {
	   recoItemMapper.insertRecoItems(recoItems);
	   }
	   catch(Exception e) {
		   e.printStackTrace();
		   System.out.println(recoItems.toString());
	   }
   }
   
   public void SaveRecoItem(RecoItem recoItem) {
	   
	   try {
	   recoItemMapper.insertRecoItem(recoItem);
	   }
	   catch(Exception e) {
		   e.printStackTrace();
		   System.out.println(recoItem.toString());
		   System.out.println(recoItemMapper.toString());
	   }
   }
   
   public void savePics(String user,Set<String> histItems,Set<String> recoItems,String path) {
	   //测试时path自带分隔符
	   String userPath=path+File.separator+user;
	   String histPath=userPath+File.separator+"history";
	   String recoPath=userPath+File.separator+"recommand";
	   File hist=new File(histPath);
	   File reco=new File(recoPath);
	   if(!hist.exists()) {
		   hist.mkdirs();
	   }
	   if(!reco.exists()) {
		   reco.mkdirs();
	   }
	   Vector<Product> hItems=recoItemMapper.getProducts(histItems);
	   Vector<Product> rItems=recoItemMapper.getProducts(recoItems);
	   MultiRead mr1=new MultiRead(hItems, new Action() {
		
		@Override
		public void action(Object o) {
			// TODO Auto-generated method stub
			Product item=(Product)o;
			String imUrl=item.getImUrl();
			String title=item.getTitle();
			Pattern pattern = Pattern.compile("[\\\\/:\\*\\?\\\"<>\\|]");
	        Matcher matcher = pattern.matcher(title);
	        title= matcher.replaceAll("");
			String filePath=histPath+File.separator+title+".jpg";
			downloadPicture(imUrl,filePath);
		}
	});
	   mr1.run();
	   
	   MultiRead mr2=new MultiRead(rItems, new Action() {
			
			@Override
			public void action(Object o) {
				// TODO Auto-generated method stub
			    Product item=(Product)o;
				String imUrl=item.getImUrl();
				String title=item.getTitle();
				Pattern pattern = Pattern.compile("[\\\\/:\\*\\?\\\"<>\\|]");
		        Matcher matcher = pattern.matcher(title);
		        title= matcher.replaceAll("");
				String filePath=recoPath+File.separator+title+".jpg";
				downloadPicture(imUrl,filePath);
			
			}
		});
		mr2.run();
	   
   }
   
   private void downloadPicture(String urlList,String path) {
	   URL url=null;
	   try {
           url = new URL(urlList);
           DataInputStream dataInputStream = new DataInputStream(url.openStream());
           
           FileOutputStream fileOutputStream = null;
           try {
           fileOutputStream = new FileOutputStream(path);
           }catch(Exception e) {
        	   e.printStackTrace();
        	   System.out.println(path);
           }
           
           ByteArrayOutputStream output = new ByteArrayOutputStream();

           byte[] buffer = new byte[1024];
           int length;

           while ((length = dataInputStream.read(buffer)) > 0) {
               output.write(buffer, 0, length);
           }
           fileOutputStream.write(output.toByteArray());
           dataInputStream.close();
           fileOutputStream.close();
       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

   }
   
   
   
}
