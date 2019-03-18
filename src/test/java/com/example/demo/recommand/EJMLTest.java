package com.example.demo.recommand;

import static org.junit.Assert.*;

import org.ejml.data.FMatrixRMaj;
import org.ejml.dense.row.CommonOps_FDRM;
import org.junit.Test;
import org.springframework.boot.jdbc.metadata.CommonsDbcp2DataSourcePoolMetadata;

public class EJMLTest {
     
	@Test
	public void test() {
		FMatrixRMaj m=new FMatrixRMaj(3,3);
		m.set(0,1,0.8f);
		CommonOps_FDRM ops=new CommonOps_FDRM();
		FMatrixRMaj eye=ops.identity(3);
		FMatrixRMaj re=new FMatrixRMaj(3);
		ops.add(2, m,3,eye,re);
		re.print();
		re=ops.extract(re, 0, 3, 0,1);
		re.print();
		
	}

}
