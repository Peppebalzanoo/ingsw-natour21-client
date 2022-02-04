package com.example.natour2;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

import com.example.natour2.controller.ControllerItinerary;
import com.example.natour2.fragment.AddItinerarioFragment;
import com.example.natour2.model.Itinerary;
import com.google.protobuf.StringValueOrBuilder;

@RunWith(MockitoJUnitRunner.class)
public class AddNewItineraryTest {

    private static ControllerItinerary ctrlItinerary;
    private static AddItinerarioFragment addItinerarioFragment;

    @BeforeClass
    public static void beforeClassFunction(){
        addItinerarioFragment = new AddItinerarioFragment();
        ctrlItinerary = Mockito.mock(ControllerItinerary.class);
    }

    /*

    @Test
    public void testPublishItineraryAllValidParameter(){

        Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "prova";
        Integer duration = 50;
        Integer difficulty = 2;
        String gpx = "gpx";
        Boolean disabledAcces = true;
        String description = "descrizione";

        Boolean res = null;
        try {
             res = addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assertTrue(res);
    }


    @Test
    public void testPublishNameNull(){

        //Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = null;
        Integer duration = 50;
        Integer difficulty = 2;
        String gpx = "gpx";
        Boolean disabledAcces = true;
        String description = "descrizione";

        assertThrows(IllegalAccessException.class, () -> {
            addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        });
    }


    @Test
    public void testPublishEmptyName(){

       //Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "";
        Integer duration = 50;
        Integer difficulty = 2;
        String gpx = "gpx";
        Boolean disabledAcces = true;
        String description = "descrizione";

        assertThrows(IllegalAccessException.class, () -> {
            addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        });
    }

    @Test
    public void testPublishDurationLessThanZero(){

        //Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "prova";
        Integer duration = -10;
        Integer difficulty = 2;
        String gpx = "gpx";
        Boolean disabledAcces = true;
        String description = "descrizione";

        assertThrows(IllegalAccessException.class, () -> {
            addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        });
    }

    @Test
    public void testPublishDurationEqualsZero(){

        //Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "prova";
        Integer duration = 0;
        Integer difficulty = 2;
        String gpx = "gpx";
        Boolean disabledAcces = true;
        String description = "descrizione";

        assertThrows(IllegalAccessException.class, () -> {
            addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        });
    }

    @Test
    public void testPublishDifficultyLessThenZero(){

        //Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "prova";
        Integer duration = 10;
        Integer difficulty = -2;
        String gpx = "gpx";
        Boolean disabledAcces = true;
        String description = "descrizione";

        assertThrows(IllegalAccessException.class, () -> {
            addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        });
    }

    @Test
    public void testPublishDifficultyEqualsZero(){

        //Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "prova";
        Integer duration = 10;
        Integer difficulty = 0;
        String gpx = "gpx";
        Boolean disabledAcces = true;
        String description = "descrizione";

        assertThrows(IllegalAccessException.class, () -> {
            addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        });
    }

    @Test
    public void testPublishDifficultyGreaterThanFour(){

       // Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "prova";
        Integer duration = 10;
        Integer difficulty = 5;
        String gpx = "gpx";
        Boolean disabledAcces = true;
        String description = "descrizione";

        assertThrows(IllegalAccessException.class, () -> {
            addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        });
    }

    @Test
    public void testPublishDifficultyEqualsFour(){

        //Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "prova";
        Integer duration = 10;
        Integer difficulty = 4;
        String gpx = "gpx";
        Boolean disabledAcces = true;
        String description = "descrizione";

        assertThrows(IllegalAccessException.class, () -> {
            addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        });
    }

    @Test
    public void testPublishGpxNull(){

        //Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "prova";
        Integer duration = 10;
        Integer difficulty = 2;
        String gpx = null;
        Boolean disabledAcces = true;
        String description = "descrizione";

        assertThrows(IllegalAccessException.class, () -> {
            addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        });
    }

    @Test
    public void testPublishEmptyGpx(){

        String name = "prova";
        Integer duration = 10;
        Integer difficulty = 2;
        String gpx = "";
        Boolean disabledAcces = true;
        String description = "descrizione";

        assertThrows(IllegalAccessException.class, () -> {
            addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        });
    }

    @Test
    public void testPublishDiasabledAccessNull(){

        String name = "prova";
        Integer duration = 10;
        Integer difficulty = 2;
        String gpx = "gpx";
        Boolean disabledAcces = null;
        String description = "descrizione";

        assertThrows(IllegalAccessException.class, () -> {
            addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        });
    }

    @Test
    public void testPublishDiasabledAccessFalse(){

        Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "prova";
        Integer duration = 10;
        Integer difficulty = 2;
        String gpx = "gpx";
        Boolean disabledAcces = false;
        String description = "descrizione";

        Boolean res = null;
        try {
            res = addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assertTrue(res);
    }

    @Test
    public void testPublishDescriptionNull(){

        Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "prova";
        Integer duration = 10;
        Integer difficulty = 2;
        String gpx = "gpx";
        Boolean disabledAcces = true;
        String description = null;

        Boolean res = null;
        try {
            res = addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assertTrue(res);
    }

    @Test
    public void testPublishEmptyDescription(){

        Mockito.when(ctrlItinerary.uploadItinerary(any(Itinerary.class))).thenReturn(true);

        String name = "prova";
        Integer duration = 10;
        Integer difficulty = 2;
        String gpx = "gpx";
        Boolean disabledAcces = true;
        String description = "";

        Boolean res = null;
        try {
            res = addItinerarioFragment.publishItinerary(ctrlItinerary, name, duration, difficulty, gpx, disabledAcces, description);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assertTrue(res);
    }

*/

}
