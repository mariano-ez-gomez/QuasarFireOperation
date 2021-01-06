package com.mercadolibre.quasar_fire_operation.services.impl;

import com.mercadolibre.quasar_fire_operation.domain.Center;
import com.mercadolibre.quasar_fire_operation.domain.SatelliteCircle;
import com.mercadolibre.quasar_fire_operation.dto.response.PositionDto;
import com.mercadolibre.quasar_fire_operation.exceptions.SatelliteException;
import com.mercadolibre.quasar_fire_operation.services.PositionDeterminationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PositionDeterminationServiceImpl implements PositionDeterminationService {

    private static final float EPSILON = 0.01F;
    private static final float MINIMUM_DISTANCE = 0F;

    private static final String SHIP_ON_SAME_POSITION = "The enemy ship cannot be on the same position as an ally satellite";
    private static final String NO_INTERSECTION = "Circumferences of satellites communication do not intersect";

    @Value("${satellite0.position}")
    private ArrayList<Float> satellite0Position;
    @Value("${satellite1.position}")
    private ArrayList<Float> satellite1Position;
    @Value("${satellite2.position}")
    private ArrayList<Float> satellite2Position;

    @Override
    public PositionDto getLocation(ArrayList<Float> distances) throws SatelliteException {

        this.validateDistances(distances);

        SatelliteCircle satelliteCircle0 = new SatelliteCircle(new Center(satellite0Position.get(0), satellite0Position.get(1)), distances.get(0));
        SatelliteCircle satelliteCircle1 = new SatelliteCircle(new Center(satellite1Position.get(0), satellite1Position.get(1)), distances.get(1));
        SatelliteCircle satelliteCircle2 = new SatelliteCircle(new Center(satellite2Position.get(0), satellite2Position.get(1)), distances.get(2));

        return this.calculateThreeCircleIntersection(satelliteCircle0, satelliteCircle1, satelliteCircle2);

    }

    private void validateDistances(ArrayList<Float> distances) throws SatelliteException {
        if(!distances.stream().allMatch(dis -> dis > MINIMUM_DISTANCE)){
            throw new SatelliteException(SHIP_ON_SAME_POSITION);
        }
    }


    private PositionDto calculateThreeCircleIntersection(SatelliteCircle sat0, SatelliteCircle sat1, SatelliteCircle sat2) throws SatelliteException {
        float a, dx, dy, d, h, rx, ry, r0, r1, r2;
        float point2_x, point2_y;

        /* dx and dy are the vertical and horizontal distances between
         * the circle centers.
         */
        dx = sat1.getCenter().getX() - sat0.getCenter().getX();
        dy = sat1.getCenter().getY() - sat0.getCenter().getY();
        r0 = sat0.getRadius();
        r1 = sat1.getRadius();
        r2 = sat2.getRadius();

        /* Determine the straight-line distance between the centers. */
        d = (float) Math.sqrt((dy*dy) + (dx*dx));

        /* Check for solvability. */
        if (d > (r0 + r1))
        {
            /* no solution. circles do not intersect. */
            throw new SatelliteException(NO_INTERSECTION);
        }
        if (d < Math.abs(r0 - r1))
        {
            /* no solution. one circle is contained in the other */
            throw new SatelliteException(NO_INTERSECTION);
        }

        /* 'point 2' is the point where the line through the circle
         * intersection points crosses the line between the circle
         * centers.
         */

        /* Determine the distance from point 0 to point 2. */
        a = ((r0*r0) - (r1*r1) + (d*d)) / (2.0F * d) ;

        /* Determine the coordinates of point 2. */
        point2_x = sat0.getCenter().getX() + (dx * a/d);
        point2_y = sat0.getCenter().getY() + (dy * a/d);

        /* Determine the distance from point 2 to either of the
         * intersection points.
         */
        h = (float) Math.sqrt((r0*r0) - (a*a));

        /* Now determine the offsets of the intersection points from
         * point 2.
         */
        rx = -dy * (h/d);
        ry = dx * (h/d);

        /* Determine the absolute intersection points. */
        float intersectionPoint1_x = point2_x + rx;
        float intersectionPoint2_x = point2_x - rx;
        float intersectionPoint1_y = point2_y + ry;
        float intersectionPoint2_y = point2_y - ry;

        System.out.println("INTERSECTION Circle1 AND Circle2:" + "(" + intersectionPoint1_x + "," + intersectionPoint1_y + ")" + " AND (" + intersectionPoint2_x + "," + intersectionPoint2_y + ")");

        /* Lets determine if circle 3 intersects at either of the above intersection points. */
        dx = intersectionPoint1_x - sat2.getCenter().getX();
        dy = intersectionPoint1_y - sat2.getCenter().getY();
        float d1 = (float) Math.sqrt((dy*dy) + (dx*dx));

        dx = intersectionPoint2_x - sat2.getCenter().getX();
        dy = intersectionPoint2_y - sat2.getCenter().getY();
        float d2 = (float) Math.sqrt((dy*dy) + (dx*dx));

        if(Math.abs(d1 - r2) < EPSILON) {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3:" + "(" + intersectionPoint1_x + "," + intersectionPoint1_y + ")");
            return new PositionDto(intersectionPoint1_x, intersectionPoint1_y);
        }
        else if(Math.abs(d2 - r2) < EPSILON) {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3:" + "(" + intersectionPoint2_x + "," + intersectionPoint2_y + ")"); //here was an error
            return new PositionDto(intersectionPoint2_x, intersectionPoint2_y);
        }
        else {
            System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3:" + "NONE");
            throw new SatelliteException(NO_INTERSECTION);
        }
    }
}
