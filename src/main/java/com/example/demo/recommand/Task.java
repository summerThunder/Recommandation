package com.example.demo.recommand;

import com.example.demo.service.ItemInfoService;
import com.example.demo.service.OrderService;
import com.example.demo.service.RecoItemService;
import com.example.demo.utils.ApplicationContextProvider;

public abstract class Task {
   protected OrderService os=ApplicationContextProvider.getBean(OrderService.class);
   protected ItemInfoService is=ApplicationContextProvider.getBean(ItemInfoService.class);
   protected RecoItemService rs=ApplicationContextProvider.getBean(RecoItemService.class);
   public abstract void start();
}
