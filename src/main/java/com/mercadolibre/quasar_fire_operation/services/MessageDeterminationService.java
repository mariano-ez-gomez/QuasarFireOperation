package com.mercadolibre.quasar_fire_operation.services;

import com.mercadolibre.quasar_fire_operation.exceptions.QuasarFireOperationException;

import java.util.ArrayList;

public interface MessageDeterminationService {

    public String getMessage(ArrayList<String []> messages) throws QuasarFireOperationException;
}
