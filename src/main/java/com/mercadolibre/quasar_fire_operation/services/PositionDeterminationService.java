package com.mercadolibre.quasar_fire_operation.services;

import com.mercadolibre.quasar_fire_operation.domain.Center;
import com.mercadolibre.quasar_fire_operation.dto.response.PositionDto;
import com.mercadolibre.quasar_fire_operation.exceptions.QuasarFireOperationException;

import java.util.ArrayList;

public interface PositionDeterminationService {

    public PositionDto getLocation(ArrayList<Center> names, ArrayList<Float> distances) throws QuasarFireOperationException;

}
