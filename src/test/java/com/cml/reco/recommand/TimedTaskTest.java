package com.cml.reco.recommand;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cml.reco.RecoApplication;
import com.cml.reco.service.ItemInfoService;
import com.cml.reco.service.OrderService;
import com.cml.reco.service.RecoItemService;

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
