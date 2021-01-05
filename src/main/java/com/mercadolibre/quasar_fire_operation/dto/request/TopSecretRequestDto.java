package com.mercadolibre.quasar_fire_operation.dto.request;

import java.util.ArrayList;

public class TopSecretRequestDto {

    private ArrayList<SatteliteDto> satellites;

    public ArrayList<SatteliteDto> getSatellites() {
        return satellites;
    }

    public void setSatellites(ArrayList<SatteliteDto> satellites) {
        this.satellites = satellites;
    }
}
