package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.domain.Center;
import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteInfoDto;
import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.exceptions.QuasarFireOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SatelliteInformationServiceImplTest {

    public static final String KENOBI = "kenobi";
    public static final String VADER = "vader";

    @Mock
    private Map<String, Center> positionsCentersMap;

    @InjectMocks
    @Spy
    private SatelliteInformationServiceImpl satelliteInformationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void validateSatellitesNames_notFound() {
        ArrayList<String> names = new ArrayList<>();
        names.add(VADER);
        Mockito.when(this.positionsCentersMap.get(Mockito.any())).thenReturn(null);
        Assertions.assertThrows(QuasarFireOperationException.class, () -> this.satelliteInformationService.validateSatellitesNames(names));
    }

    @Test
    public void validateSatellitesNames_found() {
        ArrayList<String> names = new ArrayList<>();
        names.add(KENOBI);
        Mockito.when(this.positionsCentersMap.get(Mockito.anyString())).thenReturn(new Center(0F, 0F));
        Assertions.assertDoesNotThrow(() -> this.satelliteInformationService.validateSatellitesNames(names));
    }

    @Test
    public void getSatellitePositionByName() {
        Mockito.when(this.positionsCentersMap.get(Mockito.anyString())).thenReturn(new Center(0F, 0F));
        Assertions.assertNotNull(this.satelliteInformationService.getSatellitePositionByName(KENOBI));
    }

    @Test
    public void validateSatellites_noReference() {
        Assertions.assertThrows(QuasarFireOperationException.class, () -> this.satelliteInformationService.validateSatellites(null));
    }

    @Test
    public void validateSatellites_noSatellites() {
        TopSecretRequestDto topSecretRequestDto = new TopSecretRequestDto();
        Assertions.assertThrows(QuasarFireOperationException.class, () -> this.satelliteInformationService.validateSatellites(topSecretRequestDto));
    }

    @Test
    public void validateSatellites_noThreeSatellites() {
        TopSecretRequestDto topSecretRequestDto = new TopSecretRequestDto();
        SatelliteInfoDto satelliteInfoDto = new SatelliteInfoDto();
        ArrayList<SatelliteInfoDto> satellites = new ArrayList<>();
        satellites.add(satelliteInfoDto);
        topSecretRequestDto.setSatellites(satellites);
        Assertions.assertThrows(QuasarFireOperationException.class, () -> this.satelliteInformationService.validateSatellites(topSecretRequestDto));
    }

    @Test
    public void validateSatellites_noValidInformation() {
        TopSecretRequestDto topSecretRequestDto = new TopSecretRequestDto();
        SatelliteInfoDto satelliteInfoDto = new SatelliteInfoDto();
        ArrayList<SatelliteInfoDto> satellites = new ArrayList<>();
        satellites.add(satelliteInfoDto);
        satellites.add(satelliteInfoDto);
        satellites.add(satelliteInfoDto);
        topSecretRequestDto.setSatellites(satellites);
        Assertions.assertThrows(QuasarFireOperationException.class, () -> this.satelliteInformationService.validateSatellites(topSecretRequestDto));
    }

    @Test
    public void validateSatellites_validInformation() throws QuasarFireOperationException {
        TopSecretRequestDto topSecretRequestDto = new TopSecretRequestDto();
        SatelliteInfoDto satelliteInfoDto = new SatelliteInfoDto();
        satelliteInfoDto.setDistance(0F);
        satelliteInfoDto.setName(KENOBI);
        satelliteInfoDto.setMessage(new String[]{"", "", ""});
        ArrayList<SatelliteInfoDto> satellites = new ArrayList<>();
        satellites.add(satelliteInfoDto);
        satellites.add(satelliteInfoDto);
        satellites.add(satelliteInfoDto);
        topSecretRequestDto.setSatellites(satellites);
        Assertions.assertDoesNotThrow(() -> this.satelliteInformationService.validateSatellites(topSecretRequestDto));
    }
}