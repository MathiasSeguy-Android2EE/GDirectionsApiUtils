package com.android2ee.formation.librairies.google.map.utils.direction;

import com.android2ee.formation.librairies.google.map.utils.direction.model.GDLegs;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDPath;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDPoint;
import com.android2ee.formation.librairies.google.map.utils.direction.model.GDirection;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 30/06/2017.
 */
public class GDirectionsApiUtilsTest extends TestCase {
    private static final String TAG = "GDirectionsApiUtilsTest";

    public void testDrawGDirection() throws Exception {

    }

    public void testDrawGDirection1() throws Exception {

    }

    public void testDrawGDirectionWithoutPathMarker() throws Exception {

    }


    public void testReduce() throws Exception {

        //first create a big huge initial GDir
        ArrayList<GDLegs> currentLegList = new ArrayList<>();
        GDLegs currentLeg;
        ArrayList<GDPath> currentPathList;
        GDPath currentPath;
        ArrayList<GDPoint> currentPointList;

        for (int i = 0; i < 100; i++) {
            currentPathList = new ArrayList<>(1000);
            for (int j = 0; j < 1000; j++) {
                currentPointList = new ArrayList<>(1000);
                for (int k = 0; k < 100; k++) {
                    currentPointList.add(new GDPoint(i, j / 10));
                }
                currentPath = new GDPath(currentPointList);
                currentPathList.add(currentPath);
            }
            currentLeg = new GDLegs(currentPathList);
            currentLegList.add(currentLeg);
        }
        GDirection initialGDir = new GDirection(currentLegList);
        int initial_weight = initialGDir.getWeight();
        int maxDotDisplayed = 101;
        GDirection reduceGDir = GDirectionsApiUtils.reduce(initialGDir, maxDotDisplayed);
        int reduced_weight = reduceGDir.getWeight();
        Assert.assertTrue(reduced_weight < initial_weight);

        Assert.assertTrue(reduced_weight < maxDotDisplayed + 1000);

        System.out.println("Have reduced from " + initial_weight + " to " + reduced_weight);
        Assert.assertTrue(reduced_weight > maxDotDisplayed - 20);
    }

    public void testGetDirection() throws Exception {

    }

}