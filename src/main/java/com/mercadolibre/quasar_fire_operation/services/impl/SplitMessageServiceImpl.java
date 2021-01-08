package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.domain.SplitMessage;
import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteInfoDto;
import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteSplitInfoDto;
import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.dto.response.TopSecretResponseDto;
import com.mercadolibre.quasar_fire_operation.exceptions.QuasarFireOperationException;
import com.mercadolibre.quasar_fire_operation.services.QuasarFireOperationService;
import com.mercadolibre.quasar_fire_operation.services.SplitMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

@Service
public class SplitMessageServiceImpl implements SplitMessageService {

    private static final int MAX_NUMBER_MESSAGES = 3;
    private static final String SATELLITE_NAME_ERROR = "SATELLITE_NAME_ERROR";
    private static final String CANNOT_ADD_MORE_MESSAGES = "CANNOT_ADD_MORE_MESSAGES";
    private static final String NOT_ENOUGH_MESSAGES_RECEIVED = "NOT_ENOUGH_MESSAGES_RECEIVED";
    private final ResourceBundle errorMessages = ResourceBundle.getBundle("errormessages");

    @Autowired
    private SplitMessage sessionScopedSplitMessage;

    @Autowired
    private SatelliteInformationServiceImpl satelliteInformationService;

    @Autowired
    private QuasarFireOperationService quasarFireOperationService;

    @Override
    public void addMessage(String satellite_name, SatelliteSplitInfoDto satelliteSplitInfoDto) throws QuasarFireOperationException {
        if(Objects.nonNull(satellite_name) && !satellite_name.isEmpty()) {
            ArrayList<String> names = new ArrayList<>();
            names.add(satellite_name);
            this.satelliteInformationService.validateSatellitesNames(names);    //validates that the received satellites name exists
            if(this.sessionScopedSplitMessage.getSplitMessage().size() < MAX_NUMBER_MESSAGES){
                this.sessionScopedSplitMessage.getSplitMessage().put(satellite_name, satelliteSplitInfoDto);
            } else {
                throw new QuasarFireOperationException(this.errorMessages.getString(CANNOT_ADD_MORE_MESSAGES));
            }
        } else {
            throw new QuasarFireOperationException(this.errorMessages.getString(SATELLITE_NAME_ERROR));
        }
    }

    @Override
    public TopSecretResponseDto topSecretSplitDecode() throws QuasarFireOperationException {
        if(this.sessionScopedSplitMessage.getSplitMessage().size() == MAX_NUMBER_MESSAGES) {
            TopSecretResponseDto topSecretResponseDto = this.quasarFireOperationService.topSecretDecode(this.buildTopSecretRequest(this.sessionScopedSplitMessage.getSplitMessage()));
            //once a message is decoded the split parts are removed from the session scoped hashmap that contains them
            this.sessionScopedSplitMessage.getSplitMessage().clear();
            return topSecretResponseDto;
        } else {
            throw new QuasarFireOperationException(this.errorMessages.getString(NOT_ENOUGH_MESSAGES_RECEIVED));
        }
    }

    private TopSecretRequestDto buildTopSecretRequest(Map<String, SatelliteSplitInfoDto> splitMessages) {
        TopSecretRequestDto topSecretRequestDto = new TopSecretRequestDto();
        ArrayList<SatelliteInfoDto> satelliteInfoList = new ArrayList<>();
        splitMessages.keySet().forEach(key -> {
            SatelliteInfoDto satelliteInfoDto = new SatelliteInfoDto();
            satelliteInfoDto.setName(key);
            satelliteInfoDto.setDistance(splitMessages.get(key).getDistance());
            satelliteInfoDto.setMessage(splitMessages.get(key).getMessage());
            satelliteInfoList.add(satelliteInfoDto);
        });
        topSecretRequestDto.setSatellites(satelliteInfoList);
        return topSecretRequestDto;
    }
}
