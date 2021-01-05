package com.mercadolibre.quasar_fire_operation.dto.response;

public class PositionDto {
    private float x;
    private float y;

    public PositionDto(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
