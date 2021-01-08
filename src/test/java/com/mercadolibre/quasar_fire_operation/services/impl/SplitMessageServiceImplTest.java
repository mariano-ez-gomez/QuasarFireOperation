package com.mercadolibre.quasar_fire_operation.services.impl;


import com.mercadolibre.quasar_fire_operation.domain.SplitMessage;
import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteSplitInfoDto;
import com.mercadolibre.quasar_fire_operation.exceptions.QuasarFireOperationException;
import com.mercadolibre.quasar_fire_operation.services.QuasarFireOperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SplitMessageServiceImplTest {

    private static final String VADER = "vader";
    private static final int EXCEEDED_NUMBER_MESSAGES = 4;
    private static final String KENOBI = "kenobi";
    private static final String SKYWALKER = "skywalker";
    private static final String SATO = "sato";

    @Mock
    private SplitMessage sessionScopedSplitMessage;

    @Mock
    private SatelliteInformationServiceImpl satelliteInformationService;

    @Mock
    private QuasarFireOperationService quasarFireOperationService;

    @InjectMocks
    @Spy
    private SplitMessageServiceImpl splitMessageService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void addMessage_satellite_name_null() {
        Assertions.assertThrows(QuasarFireOperationException.class,() -> this.splitMessageService.addMessage(null,null));
    }

    @Test
    public void addMessage_already_three_messages_received() throws QuasarFireOperationException {
        Mockito.doNothing().when(this.satelliteInformationService).validateSatellitesNames(Mockito.any());
        Map<String, SatelliteSplitInfoDto> splitMessage = new HashMap<>();
        splitMessage.put(KENOBI, new SatelliteSplitInfoDto());
        splitMessage.put(SKYWALKER, new SatelliteSplitInfoDto());
        splitMessage.put(SATO, new SatelliteSplitInfoDto());
        Mockito.when(this.sessionScopedSplitMessage.getSplitMessage()).thenReturn(splitMessage);
        Assertions.assertThrows(QuasarFireOperationException.class,() -> this.splitMessageService.addMessage("kenobi", null));
    }

    @Test
    public void topSecretSplitDecode_not_enough_messages_received() {
        Map<String, SatelliteSplitInfoDto> splitMessage = new HashMap<>();
        splitMessage.put(KENOBI, new SatelliteSplitInfoDto());
        Mockito.when(this.sessionScopedSplitMessage.getSplitMessage()).thenReturn(splitMessage);
        Assertions.assertThrows(QuasarFireOperationException.class,() -> this.splitMessageService.topSecretSplitDecode());
    }
}