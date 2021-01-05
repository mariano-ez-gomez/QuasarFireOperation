package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.services.MessageDeterminationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageDeterminationServiceImpl implements MessageDeterminationService {

    @Override
    public String determineMessage(ArrayList<String[]> fragmentedMessages) {
        return null;
    }
}
