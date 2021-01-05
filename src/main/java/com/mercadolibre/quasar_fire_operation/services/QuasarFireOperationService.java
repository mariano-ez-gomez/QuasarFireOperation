package com.mercadolibre.quasar_fire_operation.services;

import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.dto.response.TopSecretResponseDto;
import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;

public interface QuasarFireOperationService {

    public TopSecretResponseDto topSecret(TopSecretRequestDto topSecretRequestDto) throws SatelliteException;

}
