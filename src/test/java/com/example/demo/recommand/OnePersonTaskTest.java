package com.example.demo.recommand;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demo.RecoApplication;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RecoApplication.class)
public class OnePersonTaskTest {

	@Test
	public void test() {
		Task t=new OnePersonTask("PP2ROI" , 1325289600);
		t.start();
	}

}
