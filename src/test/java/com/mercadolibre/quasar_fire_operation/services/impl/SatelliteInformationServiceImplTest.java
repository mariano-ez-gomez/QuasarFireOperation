package com.mercadolibre.quasar_fire_operation.services.impl;


import com.mercadolibre.quasar_fire_operation.domain.Center;
import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteInfoDto;
import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class SatelliteInformationServiceImplTest {

    public static final String KENOBI = "kenobi";

    @Mock
    private Map<String, Center> positionsCentersMap;

    @InjectMocks
    @Spy
    private SatelliteInformationServiceImpl satelliteInformationService;


    @Test(expected = SatelliteException.class)
    public void validateSatellitesNames_notFound() throws SatelliteException {
        ArrayList<String> names = new ArrayList<>();
        names.add(KENOBI);
        this.satelliteInformationService.validateSatellitesNames(names);
    }

    @Test
    public void validateSatellitesNames_found() throws SatelliteException {
        ArrayList<String> names = new ArrayList<>();
        names.add(KENOBI);
        Mockito.when(this.positionsCentersMap.get(Mockito.anyString())).thenReturn(new Center(0F, 0F));
        this.satelliteInformationService.validateSatellitesNames(names);
    }

    @Test
    public void getSatellitePositionByName() {
        Mockito.when(this.positionsCentersMap.get(Mockito.anyString())).thenReturn(new Center(0F, 0F));
        this.satelliteInformationService.getSatellitePositionByName(KENOBI);
    }

    @Test(expected = SatelliteException.class)
    public void validateSatellites_noReference() throws SatelliteException {
        this.satelliteInformationService.validateSatellites(null);
    }

    @Test(expected = SatelliteException.class)
    public void validateSatellites_noSatellites() throws SatelliteException {
        TopSecretRequestDto topSecretRequestDto = new TopSecretRequestDto();
        this.satelliteInformationService.validateSatellites(topSecretRequestDto);
    }

    @Test(expected = SatelliteException.class)
    public void validateSatellites_noThreeSatellites() throws SatelliteException {
        TopSecretRequestDto topSecretRequestDto = new TopSecretRequestDto();
        SatelliteInfoDto satelliteInfoDto = new SatelliteInfoDto();
        ArrayList<SatelliteInfoDto> satellites = new ArrayList<>();
        satellites.add(satelliteInfoDto);
        topSecretRequestDto.setSatellites(satellites);
        this.satelliteInformationService.validateSatellites(topSecretRequestDto);
    }

    @Test(expected = SatelliteException.class)
    public void validateSatellites_noValidInformation() throws SatelliteException {
        TopSecretRequestDto topSecretRequestDto = new TopSecretRequestDto();
        SatelliteInfoDto satelliteInfoDto = new SatelliteInfoDto();
        ArrayList<SatelliteInfoDto> satellites = new ArrayList<>();
        satellites.add(satelliteInfoDto);
        satellites.add(satelliteInfoDto);
        satellites.add(satelliteInfoDto);
        topSecretRequestDto.setSatellites(satellites);
        this.satelliteInformationService.validateSatellites(topSecretRequestDto);
    }

    @Test
    public void validateSatellites_validInformation() throws SatelliteException {
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
        this.satelliteInformationService.validateSatellites(topSecretRequestDto);
    }
}