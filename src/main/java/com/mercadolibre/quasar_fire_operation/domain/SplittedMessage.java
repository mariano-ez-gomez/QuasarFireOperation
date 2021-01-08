package com.mercadolibre.quasar_fire_operation.domain;

import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteSplittedInfoDto;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SplittedMessage {

    private Map<String, SatelliteSplittedInfoDto> splittedMessages = new HashMap<>();

    public Map<String, SatelliteSplittedInfoDto> getSplittedMessages() {
        return splittedMessages;
    }

    public void setSplittedMessages(Map<String, SatelliteSplittedInfoDto> splittedMessages) {
        this.splittedMessages = splittedMessages;
    }
}
