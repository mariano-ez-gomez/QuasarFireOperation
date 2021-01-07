package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.domain.Center;
import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.dto.response.PositionDto;
import com.mercadolibre.quasar_fire_operation.dto.response.TopSecretResponseDto;
import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;
import com.mercadolibre.quasar_fire_operation.services.MessageDeterminationService;
import com.mercadolibre.quasar_fire_operation.services.PositionDeterminationService;
import com.mercadolibre.quasar_fire_operation.services.QuasarFireOperationService;
import com.mercadolibre.quasar_fire_operation.services.SatelliteInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service
public class QuasarFireOperationServiceImpl implements QuasarFireOperationService {

    @Autowired
    private PositionDeterminationService positionDeterminationService;

    @Autowired
    private MessageDeterminationService messageDeterminationService;

    @Autowired
    private SatelliteInformationService satelliteInformationService;

    @Override
    public TopSecretResponseDto topSecret(TopSecretRequestDto topSecretRequestDto) throws SatelliteException {

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Float> distances = new ArrayList<>();
        ArrayList<String[]> fragmentedMessages = new ArrayList<>();

        this.satelliteInformationService.validateSatellites(topSecretRequestDto);
        this.getNamesDistancesAndFragmentedMessages(topSecretRequestDto, names, distances, fragmentedMessages);
        this.satelliteInformationService.validateSatellitesNames(names);

        ArrayList<Center> centers = this.getCenters(names);
        PositionDto positionDto = this.positionDeterminationService.getLocation(centers, distances);
        String message = this.messageDeterminationService.getMessage(fragmentedMessages);
        return buildResponse(positionDto, message);
    }

    private void getNamesDistancesAndFragmentedMessages(TopSecretRequestDto topSecretRequestDto, ArrayList<String> names, ArrayList<Float> distances, ArrayList<String[]> fragmentedMessages) {
        topSecretRequestDto.getSatellites().forEach(sat -> {
                    names.add(sat.getName().toLowerCase());
                    distances.add(sat.getDistance());
                    fragmentedMessages.add(sat.getMessage());
        });
    }

    private ArrayList<Center> getCenters(ArrayList<String> names) {
        ArrayList<Center> centers = new ArrayList<>();
        names.forEach(name -> centers.add(this.satelliteInformationService.getSatellitePositionByName(name)));
        return centers;
    }

    private TopSecretResponseDto buildResponse(PositionDto positionDto, String message) {
        TopSecretResponseDto topSecretResponseDto = new TopSecretResponseDto();
        topSecretResponseDto.setPosition(positionDto);
        topSecretResponseDto.setMessage(message);
        return topSecretResponseDto;
    }

}
