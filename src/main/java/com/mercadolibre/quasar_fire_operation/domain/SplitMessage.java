package com.mercadolibre.quasar_fire_operation.domain;

import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteSplitInfoDto;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SplitMessage {

    private Map<String, SatelliteSplitInfoDto> splitMessage = new HashMap<>();

    public Map<String, SatelliteSplitInfoDto> getSplitMessage() {
        return splitMessage;
    }

    public void setSplitMessage(Map<String, SatelliteSplitInfoDto> splitMessage) {
        this.splitMessage = splitMessage;
    }
}
