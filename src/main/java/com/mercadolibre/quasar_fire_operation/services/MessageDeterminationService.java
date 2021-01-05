package com.mercadolibre.quasar_fire_operation.services;

import java.util.ArrayList;

public interface MessageDeterminationService {

    public String determineMessage(ArrayList<String []> fragmentedMessages);
}
