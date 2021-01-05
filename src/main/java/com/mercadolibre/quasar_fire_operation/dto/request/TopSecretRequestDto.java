package com.mercadolibre.quasar_fire_operation.dto.request;

import java.util.ArrayList;

public class TopSecretRequestDto {

    private ArrayList<SatelliteInfoDto> satellites;

    public ArrayList<SatelliteInfoDto> getSatellites() {
        return satellites;
    }

    public void setSatellites(ArrayList<SatelliteInfoDto> satellites) {
        this.satellites = satellites;
    }
}
