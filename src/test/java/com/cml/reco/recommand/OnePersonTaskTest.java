package com.cml.reco.recommand;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cml.reco.RecoApplication;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RecoApplication.class)
public class OnePersonTaskTest {
    //一个人的推荐更新，输入用户id,时间戳，推荐结果保存地址(可选，若选择会将推荐结果下载到本地)
	@Test
	public void test() {
		String path="F:\\推荐结果1";
		Task t=new OnePersonTask("A5NMT6VJVZYUX0" , 1325289600 ,path);
		t.start();
		
	}

}
