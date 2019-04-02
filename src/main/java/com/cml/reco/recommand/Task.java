package com.cml.reco.recommand;

import com.cml.reco.service.ItemInfoService;
import com.cml.reco.service.OrderService;
import com.cml.reco.service.RecoItemService;
import com.cml.reco.utils.ApplicationContextProvider;

public abstract class Task {
   protected OrderService os=ApplicationContextProvider.getBean(OrderService.class);
   protected ItemInfoService is=ApplicationContextProvider.getBean(ItemInfoService.class);
   protected RecoItemService rs=ApplicationContextProvider.getBean(RecoItemService.class);
   public abstract void start();
}
