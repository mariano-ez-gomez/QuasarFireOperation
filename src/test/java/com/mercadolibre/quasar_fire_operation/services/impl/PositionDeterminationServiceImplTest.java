package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.domain.Center;
import com.mercadolibre.quasar_fire_operation.dto.response.PositionDto;
import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;

public class PositionDeterminationServiceImplTest {

    public static final float ZERO = 0F;

    @Spy
    private PositionDeterminationServiceImpl positionDeterminationService;

    private ArrayList<Center> centers_no_distance = new ArrayList<>();
    private ArrayList<Float> distances_zero = new ArrayList<>();

    private ArrayList<Center> centers_correct = new ArrayList<>();
    private ArrayList<Float> distances_correct = new ArrayList<>();


    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        Center center_no_distance = new Center(ZERO, ZERO);
        centers_no_distance.add(center_no_distance);
        distances_zero.add(ZERO);

        //setting all correct values:
        Center center0 = new Center(-4.18F,1.66F);
        Center center1 = new Center(ZERO, ZERO);
        Center center2 = new Center(-7.02F,5.34F);
        centers_correct.add(center0);
        centers_correct.add(center1);
        centers_correct.add(center2);

        distances_correct.add(1.89F);
        distances_correct.add(4.74130783645F);
        distances_correct.add(4.17F);
    }

    @Test
    public void getLocation_withNoDistance() {
        Assertions.assertThrows(SatelliteException.class, () -> this.positionDeterminationService.getLocation(centers_no_distance, distances_zero));
    }

    @Test
    public void getLocation_correct_values() throws SatelliteException {
        PositionDto positionDto = this.positionDeterminationService.getLocation(centers_correct, distances_correct);
        Assertions.assertEquals(-3.3481317F, positionDto.getX(), 0.0001F);
        Assertions.assertEquals(3.3570843F, positionDto.getY(), 0.0001F);
    }

    @Test
    public void getLocation_no_intersection() throws SatelliteException {
        distances_correct.add(0, 25F);
        Assertions.assertThrows(SatelliteException.class, () -> this.positionDeterminationService.getLocation(centers_correct, distances_correct));
    }
}