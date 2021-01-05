package com.mercadolibre.quasar_fire_operation.dto.response;

public class TopSecretResponseDto {

    private PositionDto position;

    private String message;

    public PositionDto getPosition() {
        return position;
    }

    public void setPosition(PositionDto position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
