package com.mercadolibre.quasar_fire_operation.controllers;

import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.dto.response.TopSecretResponseDto;
import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;
import com.mercadolibre.quasar_fire_operation.services.QuasarFireOperationService;
import com.mercadolibre.quasar_fire_operation.services.SplittedMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/quasarfireoperation")
public class QuasarFireOperationController {

    @Autowired
    private QuasarFireOperationService quasarFireOperationService;

    @Autowired
    private SplittedMessageService splittedMessageService;

    @RequestMapping(value = "/topsecret", method = RequestMethod.POST)
    private ResponseEntity topSecret(@RequestBody TopSecretRequestDto topSecretRequestDto){
        try {
            return new ResponseEntity<TopSecretResponseDto>(this.quasarFireOperationService.topSecret(topSecretRequestDto), HttpStatus.OK);
        } catch (SatelliteException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/topsecret_split/{satellite_name}", method = RequestMethod.POST)
    private ResponseEntity topSecretSplitPost(@PathVariable String satellite_name, @RequestBody String satelliteSplittedInfoDto) {
        this.splittedMessageService.setElement(satelliteSplittedInfoDto);
        return null;
    }

    @RequestMapping(value = "/topsecret_split", method = RequestMethod.GET)
    private ResponseEntity topSecretSplitGet(){
        return new ResponseEntity( this.splittedMessageService.getList(), HttpStatus.OK);
    }
}
