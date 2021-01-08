package com.mercadolibre.quasar_fire_operation.dto.request;

public class SatelliteSplitInfoDto {

    private float distance;
    private String[] message;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }
}
