package com.mercadolibre.quasar_fire_operation.services;

import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteSplitInfoDto;
import com.mercadolibre.quasar_fire_operation.dto.response.TopSecretResponseDto;
import com.mercadolibre.quasar_fire_operation.exceptions.QuasarFireOperationException;

public interface SplitMessageService {

    public void addMessage(String satellite_name, SatelliteSplitInfoDto satelliteSplitInfoDto) throws QuasarFireOperationException;

    public TopSecretResponseDto topSecretSplitDecode() throws QuasarFireOperationException;

}
