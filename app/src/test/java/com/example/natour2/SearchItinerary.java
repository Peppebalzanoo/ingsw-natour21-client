package com.example.natour2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;

import com.example.natour2.controller.ControllerItinerary;
import com.example.natour2.fragment.AddItinerarioFragment;
import com.example.natour2.fragment.SearchFragment;
import com.example.natour2.model.Itinerary;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kotlin.jvm.internal.unsafe.MonitorKt;

public class SearchItinerary {

    private static ControllerItinerary ctrlItinerary;
    private static SearchFragment searchFragment;
    private static List<Itinerary> itineraries;

    @BeforeClass
    public static void beforeClassFunction(){
        searchFragment = new SearchFragment();
        ctrlItinerary = Mockito.mock(ControllerItinerary.class);

        itineraries = Mockito.mock(List.class);
        Mockito.when(itineraries.removeAll(any(List.class))).thenReturn(true);
        Mockito.when(itineraries.addAll(any())).thenReturn(true);
    }

    /*

    @Test
    public void testSearchItineraryAllValidReturnTrue(){

        Itinerary itr  = new Itinerary("prova", 50, 2, "gpx", true, null);
        List<Itinerary> itineraryList = Arrays.asList(itr);

        Mockito.when(ctrlItinerary.getAllItinerariesByFilters(anyString(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(itineraryList);

        String name = "prova";
        Integer duration = 50;
        Integer difficulty = 2;
        Boolean disabledAccess = true;

        boolean res = false;
        try {
            res = searchFragment.getItinerariesByFilters(ctrlItinerary, itineraries, name, duration, difficulty, disabledAccess);
            Mockito.verify(itineraries, Mockito.times(1)).addAll(any());
            Mockito.verify(itineraries, Mockito.times(1)).removeAll(any());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        assertEquals(true, res);
    }

    @Test
    public void testSearchItineraryAllValidReturnFalse(){

        Mockito.when(ctrlItinerary.getAllItinerariesByFilters(anyString(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(null);

        String name = "prova";
        Integer duration = 50;
        Integer difficulty = 2;
        Boolean disabledAccess = true;

        boolean res = false;
        try {
            res = searchFragment.getItinerariesByFilters(ctrlItinerary, itineraries, name, duration, difficulty, disabledAccess);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        assertEquals(false, res);
    }

    @Test
    public void testSearchItineraryDurationLessThanZero(){

        Mockito.when(ctrlItinerary.getAllItinerariesByFilters(anyString(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(null);

        String name = "prova";
        Integer duration = -5;
        Integer difficulty = 2;
        Boolean disabledAccess = true;

        assertThrows(IllegalArgumentException.class, () -> {
            searchFragment.getItinerariesByFilters(ctrlItinerary, itineraries, name, duration, difficulty, disabledAccess);
        });
    }

    @Test
    public void testSearchItineraryDurationEqualsZero(){

        Mockito.when(ctrlItinerary.getAllItinerariesByFilters(anyString(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(null);

        String name = "prova";
        Integer duration = 0;
        Integer difficulty = 2;
        Boolean disabledAccess = true;

        assertThrows(IllegalArgumentException.class, () -> {
            searchFragment.getItinerariesByFilters(ctrlItinerary, itineraries, name, duration, difficulty, disabledAccess);
        });
    }

    @Test
    public void testSearchItineraryDifficultyLessThanZero(){

        Mockito.when(ctrlItinerary.getAllItinerariesByFilters(anyString(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(null);

        String name = "prova";
        Integer duration = 50;
        Integer difficulty = -5;
        Boolean disabledAccess = true;

        assertThrows(IllegalArgumentException.class, () -> {
            searchFragment.getItinerariesByFilters(ctrlItinerary, itineraries, name, duration, difficulty, disabledAccess);
        });
    }


    @Test
    public void testSearchItineraryDifficultyEqualsZero(){

        Mockito.when(ctrlItinerary.getAllItinerariesByFilters(anyString(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(null);

        String name = "prova";
        Integer duration = 50;
        Integer difficulty = 0;
        Boolean disabledAccess = true;

        assertThrows(IllegalArgumentException.class, () -> {
            searchFragment.getItinerariesByFilters(ctrlItinerary, itineraries, name, duration, difficulty, disabledAccess);
        });
    }

    @Test
    public void testSearchItineraryDifficultyGreaterThanFour(){

        Mockito.when(ctrlItinerary.getAllItinerariesByFilters(anyString(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(null);

        String name = "prova";
        Integer duration = 50;
        Integer difficulty = 10;
        Boolean disabledAccess = true;

        assertThrows(IllegalArgumentException.class, () -> {
            searchFragment.getItinerariesByFilters(ctrlItinerary, itineraries, name, duration, difficulty, disabledAccess);
        });
    }

    @Test
    public void testSearchItineraryDifficultyEqualsFour(){

        Mockito.when(ctrlItinerary.getAllItinerariesByFilters(anyString(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(null);

        String name = "prova";
        Integer duration = 50;
        Integer difficulty = 4;
        Boolean disabledAccess = true;

        assertThrows(IllegalArgumentException.class, () -> {
            searchFragment.getItinerariesByFilters(ctrlItinerary, itineraries, name, duration, difficulty, disabledAccess);
        });
    }

    @Test
    public void testSearchItineraryEmptyName(){

        Mockito.when(ctrlItinerary.getAllItinerariesByFilters(anyString(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(null);

        String name = "";
        Integer duration = 50;
        Integer difficulty = 2;
        Boolean disabledAccess = true;

        assertThrows(IllegalArgumentException.class, () -> {
            searchFragment.getItinerariesByFilters(ctrlItinerary, itineraries, name, duration, difficulty, disabledAccess);
        });
    }

     */
}
