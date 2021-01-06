package com.mercadolibre.quasar_fire_operation.services;

import com.mercadolibre.quasar_fire_operation.dto.response.PositionDto;
import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;

import java.util.ArrayList;

public interface PositionDeterminationService {

    public PositionDto getLocation(ArrayList<Float> distances) throws SatelliteException;
}
