package com.mercadolibre.quasar_fire_operation.controllers;

import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteSplitInfoDto;
import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.dto.response.TopSecretResponseDto;
import com.mercadolibre.quasar_fire_operation.exceptions.QuasarFireOperationException;
import com.mercadolibre.quasar_fire_operation.services.QuasarFireOperationService;
import com.mercadolibre.quasar_fire_operation.services.SplitMessageService;
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
    private SplitMessageService splitMessageService;

    @RequestMapping(value = "/topsecret", method = RequestMethod.POST)
    private ResponseEntity topSecretDecode(@RequestBody TopSecretRequestDto topSecretRequestDto){
        try {
            return new ResponseEntity<TopSecretResponseDto>(this.quasarFireOperationService.topSecretDecode(topSecretRequestDto), HttpStatus.OK);
        } catch (QuasarFireOperationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/topsecret_split/{satellite_name}", method = RequestMethod.POST)
    private ResponseEntity topSecretSplitPost(@PathVariable String satellite_name, @RequestBody SatelliteSplitInfoDto satelliteSplitInfoDto) {
        try {
            this.splitMessageService.addMessage(satellite_name, satelliteSplitInfoDto);
            return new ResponseEntity(HttpStatus.OK);
        } catch (QuasarFireOperationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/topsecret_split", method = RequestMethod.GET)
    private ResponseEntity topSecretSplitDecode(){
        try {
            return new ResponseEntity<TopSecretResponseDto>(this.splitMessageService.topSecretSplitDecode(), HttpStatus.OK);
        } catch (QuasarFireOperationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
