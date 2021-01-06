package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.domain.Center;
import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteInfoDto;
import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;
import com.mercadolibre.quasar_fire_operation.services.SatelliteInformationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SatelliteInformationServiceImpl implements SatelliteInformationService {

    private static final int NUMBER_OF_SATELLITES = 3;
    private static final String SATELLITE_NAME_ERROR = "Satellite name error";
    private static final String SATELLITE_VALIDATION_ERROR = "Error in satellites info";
    private Map<String, Center> positionsCentersMap;

    @Value("${satellite0.name}")
    private String sat0Name;
    @Value("${satellite1.name}")
    private String sat1Name;
    @Value("${satellite2.name}")
    private String sat2Name;

    @Value("${satellite0.position.x}")
    private float sat0PositionX;
    @Value("${satellite0.position.y}")
    private float sat0PositionY;
    @Value("${satellite1.position.x}")
    private float sat1PositionX;
    @Value("${satellite1.position.y}")
    private float sat1PositionY;
    @Value("${satellite2.position.x}")
    private float sat2PositionX;
    @Value("${satellite2.position.y}")
    private float sat2PositionY;

    @PostConstruct
    private void buildSatellitesCentersMap(){
        positionsCentersMap = new HashMap<>();
        positionsCentersMap.put(sat0Name, new Center(sat0PositionX, sat0PositionY));
        positionsCentersMap.put(sat1Name, new Center(sat1PositionX, sat1PositionY));
        positionsCentersMap.put(sat2Name, new Center(sat2PositionX, sat2PositionY));
    }

    @Override
    public void validateSatellitesNames(ArrayList<String> names) throws SatelliteException {
        if(names.stream().anyMatch(name -> Objects.isNull(this.positionsCentersMap.get(name)))) {
            throw new SatelliteException(SATELLITE_NAME_ERROR);
        }
    }

    @Override
    public Center getSatellitePositionByName(String name) {
        return this.positionsCentersMap.get(name);
    }

    @Override
    public void validateSatellites(TopSecretRequestDto topSecretRequestDto) throws SatelliteException {
        if(Objects.isNull(topSecretRequestDto) ||
                Objects.isNull(topSecretRequestDto.getSatellites()) ||
                topSecretRequestDto.getSatellites().size() != NUMBER_OF_SATELLITES ||
                this.validateSatellitesInfo(topSecretRequestDto.getSatellites())){
            throw new SatelliteException(SATELLITE_VALIDATION_ERROR);
        }
    }

    private boolean validateSatellitesInfo(ArrayList<SatelliteInfoDto> satellites) {
        return satellites.stream().anyMatch(sat -> Objects.isNull(sat.getName()) || Objects.isNull(sat.getDistance()) || Objects.isNull(sat.getMessage()));
    }
}
