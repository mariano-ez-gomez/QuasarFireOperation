package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.dto.response.PositionDto;
import com.mercadolibre.quasar_fire_operation.dto.response.TopSecretResponseDto;
import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;
import com.mercadolibre.quasar_fire_operation.services.MessageDeterminationService;
import com.mercadolibre.quasar_fire_operation.services.PositionDeterminationService;
import com.mercadolibre.quasar_fire_operation.services.QuasarFireOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class QuasarFireOperationServiceImpl implements QuasarFireOperationService {

    private static final int NUMBER_OF_SATELLITES = 3;
    private static final String SATELLITE_VALIDATION_ERROR = "Error in satellites info";

    @Autowired
    private PositionDeterminationService positionDeterminationService;

    @Autowired
    private MessageDeterminationService messageDeterminationService;

    @Override
    public TopSecretResponseDto topSecret(TopSecretRequestDto topSecretRequestDto) throws SatelliteException {

        ArrayList<Float> distances = new ArrayList<>();
        ArrayList<String[]> fragmentedMessages = new ArrayList<>();

        if (this.validateSatellites(topSecretRequestDto)) {
            throw new SatelliteException(SATELLITE_VALIDATION_ERROR);
        }

        this.getDistancesAndFragmentedMessages(topSecretRequestDto, distances, fragmentedMessages);

        PositionDto positionDto = this.positionDeterminationService.determinePosition(distances);
        String message = this.messageDeterminationService.determineMessage(fragmentedMessages);
        return buildResponse(positionDto, message);
    }

    private void getDistancesAndFragmentedMessages(TopSecretRequestDto topSecretRequestDto, ArrayList<Float> distances, ArrayList<String[]> fragmentedMessages) {
        topSecretRequestDto.getSatellites().forEach(sat -> {
                    distances.add(sat.getDistance());
                    fragmentedMessages.add(sat.getMessage());
        });
    }

    private boolean validateSatellites(TopSecretRequestDto topSecretRequestDto) {
        return Objects.nonNull(topSecretRequestDto) &&
                Objects.nonNull(topSecretRequestDto.getSatellites()) &&
                topSecretRequestDto.getSatellites().size() != NUMBER_OF_SATELLITES;
    }

    private TopSecretResponseDto buildResponse(PositionDto positionDto, String message) {
        TopSecretResponseDto topSecretResponseDto = new TopSecretResponseDto();
        topSecretResponseDto.setPosition(positionDto);
        topSecretResponseDto.setMessage(message);
        return topSecretResponseDto;
    }

}
