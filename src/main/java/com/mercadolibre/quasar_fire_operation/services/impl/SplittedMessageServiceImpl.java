package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.domain.SplittedMessage;
import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteInfoDto;
import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteSplittedInfoDto;
import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.dto.response.TopSecretResponseDto;
import com.mercadolibre.quasar_fire_operation.exceptions.QuasarFireOperationException;
import com.mercadolibre.quasar_fire_operation.services.QuasarFireOperationService;
import com.mercadolibre.quasar_fire_operation.services.SplittedMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

@Service
public class SplittedMessageServiceImpl implements SplittedMessageService {

    private static final int NUMBER_OF_SATELLITES = 3;
    private static final String SATELLITE_NAME_ERROR = "SATELLITE_NAME_ERROR";
    private static final String CANNOT_ADD_MORE_MESSAGES = "CANNOT_ADD_MORE_MESSAGES";
    private static final String NOT_ENOUGH_MESSAGES_RECEIVED = "NOT_ENOUGH_MESSAGES_RECEIVED";
    private ResourceBundle errorMessages = ResourceBundle.getBundle("errormessages");

    @Autowired
    SplittedMessage sessionScopedBean;

    @Autowired
    SatelliteInformationServiceImpl satelliteInformationService;

    @Autowired
    QuasarFireOperationService quasarFireOperationService;

    @Override
    public void addMessage(String satellite_name, SatelliteSplittedInfoDto satelliteSplittedInfoDto) throws QuasarFireOperationException {
        if(Objects.nonNull(satellite_name) && !satellite_name.isEmpty()) {
            ArrayList<String> names = new ArrayList<>();
            names.add(satellite_name);
            this.satelliteInformationService.validateSatellitesNames(names);
            if(this.sessionScopedBean.getSplittedMessages().keySet().stream().anyMatch(key -> key.equals(satellite_name)) ||
                    this.sessionScopedBean.getSplittedMessages().size() < NUMBER_OF_SATELLITES){
                this.sessionScopedBean.getSplittedMessages().put(satellite_name, satelliteSplittedInfoDto);
            } else {
                throw new QuasarFireOperationException(this.errorMessages.getString(CANNOT_ADD_MORE_MESSAGES));
            }
        } else {
            throw new QuasarFireOperationException(this.errorMessages.getString(SATELLITE_NAME_ERROR));
        }
    }

    @Override
    public TopSecretResponseDto topSecretSplitDecode() throws QuasarFireOperationException {
        if(this.sessionScopedBean.getSplittedMessages().size() == NUMBER_OF_SATELLITES){
            return this.quasarFireOperationService.topSecretDecode(this.buildTopSecretRequest(this.sessionScopedBean.getSplittedMessages()));
        } else {
            throw new QuasarFireOperationException(this.errorMessages.getString(CANNOT_ADD_MORE_MESSAGES));
        }
    }

    private TopSecretRequestDto buildTopSecretRequest(Map<String, SatelliteSplittedInfoDto> splittedMessages) {
        TopSecretRequestDto topSecretRequestDto = new TopSecretRequestDto();
        ArrayList<SatelliteInfoDto> satelliteInfoList = new ArrayList<>();
        splittedMessages.keySet().forEach(key -> {
            SatelliteInfoDto satelliteInfoDto = new SatelliteInfoDto();
            satelliteInfoDto.setName(key);
            satelliteInfoDto.setDistance(splittedMessages.get(key).getDistance());
            satelliteInfoDto.setMessage(splittedMessages.get(key).getMessage());
            satelliteInfoList.add(satelliteInfoDto);
        });
        topSecretRequestDto.setSatellites(satelliteInfoList);
        return topSecretRequestDto;
    }
}
