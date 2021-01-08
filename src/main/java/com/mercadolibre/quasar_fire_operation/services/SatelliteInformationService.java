package com.mercadolibre.quasar_fire_operation.services;

import com.mercadolibre.quasar_fire_operation.domain.Center;
import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.exceptions.QuasarFireOperationException;

import java.util.ArrayList;

public interface SatelliteInformationService {

    public void validateSatellites(TopSecretRequestDto topSecretRequestDto) throws QuasarFireOperationException;
    public void validateSatellitesNames(ArrayList<String> names) throws QuasarFireOperationException;
    public Center getSatellitePositionByName(String name);
}
