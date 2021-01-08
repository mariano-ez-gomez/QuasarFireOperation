package com.mercadolibre.quasar_fire_operation.services;

import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteSplittedInfoDto;
import com.mercadolibre.quasar_fire_operation.dto.response.TopSecretResponseDto;
import com.mercadolibre.quasar_fire_operation.exceptions.QuasarFireOperationException;

public interface SplittedMessageService {
    public void addMessage(String satellite_name, SatelliteSplittedInfoDto satelliteSplittedInfoDto) throws QuasarFireOperationException;

    public TopSecretResponseDto topSecretSplitDecode() throws QuasarFireOperationException;
}
