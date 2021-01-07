package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.domain.Center;
import com.mercadolibre.quasar_fire_operation.dto.request.SatelliteInfoDto;
import com.mercadolibre.quasar_fire_operation.dto.request.TopSecretRequestDto;
import com.mercadolibre.quasar_fire_operation.dto.response.PositionDto;
import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;
import com.mercadolibre.quasar_fire_operation.services.MessageDeterminationService;
import com.mercadolibre.quasar_fire_operation.services.PositionDeterminationService;
import com.mercadolibre.quasar_fire_operation.services.SatelliteInformationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QuasarFireOperationServiceImplTest {

    private static final String KENOBI = "kenobi";
    private static final String SKYWALKER = "skywalker";
    private static final String SATO = "sato";

    private TopSecretRequestDto topSecretRequestDto;

    @Mock
    private PositionDeterminationService positionDeterminationService;

    @Mock
    private MessageDeterminationService messageDeterminationService;

    @Mock
    private SatelliteInformationService satelliteInformationService;

    @InjectMocks
    @Spy
    private QuasarFireOperationServiceImpl quasarFireOperationService;


    @Before
    public void init() {
        topSecretRequestDto = new TopSecretRequestDto();
        SatelliteInfoDto satelliteInfoDto0 = new SatelliteInfoDto();
        SatelliteInfoDto satelliteInfoDto1= new SatelliteInfoDto();
        SatelliteInfoDto satelliteInfoDto2 = new SatelliteInfoDto();

        satelliteInfoDto0.setName(KENOBI);
        satelliteInfoDto0.setDistance(1.89F);
        satelliteInfoDto0.setMessage(new String[]{"este", "", "", "mensaje", ""});

        satelliteInfoDto1.setName(SKYWALKER);
        satelliteInfoDto1.setDistance(4.74130783645F);
        satelliteInfoDto1.setMessage(new String[]{"","es", "", "", "secreto"});

        satelliteInfoDto2.setName(SATO);
        satelliteInfoDto2.setDistance(4.17F);
        satelliteInfoDto2.setMessage(new String[]{"", "", "un", "", ""});

        ArrayList<SatelliteInfoDto> satellites = new ArrayList<>();
        satellites.add(satelliteInfoDto0);
        satellites.add(satelliteInfoDto1);
        satellites.add(satelliteInfoDto2);

        topSecretRequestDto.setSatellites(satellites);
    }

    @Test
    public void topSecret() throws SatelliteException {
        Mockito.doNothing().when(this.satelliteInformationService).validateSatellites(topSecretRequestDto);
        Mockito.doNothing().when(this.satelliteInformationService).validateSatellitesNames(Mockito.any());
        Mockito.when(this.satelliteInformationService.getSatellitePositionByName(Mockito.anyString())).thenReturn(new Center(0F, 0F));
        Mockito.when(this.positionDeterminationService.getLocation(Mockito.any(), Mockito.any())).thenReturn(new PositionDto(0F, 0F));
        Mockito.when(this.messageDeterminationService.getMessage(Mockito.any())).thenReturn("");
        this.quasarFireOperationService.topSecret(topSecretRequestDto);
    }
}