package com.mercadolibre.quasar_fire_operation.services;

import com.mercadolibre.quasar_fire_operation.domain.Center;
import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;

import java.util.ArrayList;

public interface SatelliteInformationService {

    public void validateSatellites(TopSecretRequestDto topSecretRequestDto) throws SatelliteException;
    public void validateSatellitesNames(ArrayList<String> names) throws SatelliteException;
    public Center getSatellitePositionByName(String name);
}
