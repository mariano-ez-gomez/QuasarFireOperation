package com.mercadolibre.quasar_fire_operation.controllers;

import com.mercadolibre.quasar_fire_operation.dto.response.PositionDto;
import com.mercadolibre.quasar_fire_operation.dto.response.TopSecretResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/quasarfireoperation/")
public class QuasarFireOperationController {

    @RequestMapping(value = "/getOperation", method = RequestMethod.GET)
    private ResponseEntity getOperation(){
        return null;
    }
}
