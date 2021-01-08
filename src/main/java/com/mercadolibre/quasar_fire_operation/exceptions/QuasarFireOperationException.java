package com.mercadolibre.quasar_fire_operation.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class QuasarFireOperationException extends Exception{

    final Logger logger = Logger.getLogger(this.getClass().getName());

    public QuasarFireOperationException(String errorMsg){
        super(errorMsg);
        this.logger.log(Level.INFO, errorMsg);
    }

}
