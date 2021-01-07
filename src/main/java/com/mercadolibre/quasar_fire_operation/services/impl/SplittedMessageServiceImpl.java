package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.domain.SplittedMessage;
import com.mercadolibre.quasar_fire_operation.services.SplittedMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class SplittedMessageServiceImpl implements SplittedMessageService {

    @Autowired   //se tiene que llamar igual que el método que tiene el @Bean
    SplittedMessage sessionScopedBean;

    @Override
    public ArrayList<String> getList() {
        return sessionScopedBean.getList();
    }

    @Override
    public void setElement(String element) {
        sessionScopedBean.getList().add(element);
    }
}
