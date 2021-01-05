package com.mercadolibre.quasar_fire_operation.domain;

public class SatelliteCircle {

    private Center center;
    private float radius;

    public SatelliteCircle(Center center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
