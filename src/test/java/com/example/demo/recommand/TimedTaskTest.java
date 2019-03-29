package com.example.demo.recommand;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demo.RecoApplication;
import com.example.demo.service.ItemInfoService;
import com.example.demo.service.OrderService;
import com.example.demo.service.RecoItemService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RecoApplication.class)
public class TimedTaskTest {
    //每日更新当天买过东西的人的推荐结果，输入图片保存地址（可选），不选的话只更新数据库
    @Test
	public void test() {
		TimedTask tt=new TimedTask("D://推荐结果2");
		tt.start();
	}

}
