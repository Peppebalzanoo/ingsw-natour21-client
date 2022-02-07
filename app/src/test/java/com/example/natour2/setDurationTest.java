package com.example.natour2;

import com.example.natour2.adapter.ItinerarioAdapter;
import com.example.natour2.fragment.SearchFragment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class setDurationTest {

    private static ItinerarioAdapter itinerarioAdapter;

    @BeforeClass
    public static void beforeClassFunction(){
        itinerarioAdapter = new ItinerarioAdapter(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }


    @Test
    public void testSetDurationParameterGreaterThanZero(){
        int duration = 90;
        String expected = "1:30";
        String res = itinerarioAdapter.setDuration(duration);

        assertEquals(expected, res);
    }

    @Test
    public void testSetDurationParameterEqualsZero(){
        int duration = 0;
        assertThrows(IllegalArgumentException.class, () -> {
            itinerarioAdapter.setDuration(duration);
        });
    }

    @Test
    public void testSetDurationParameterLessThanZero(){
        int duration = -5;
        assertThrows(IllegalArgumentException.class, () -> {
            itinerarioAdapter.setDuration(duration);
        });
    }

    @Test
    public void testSetDurationParameterEquals1439(){
        int duration = 1439;
        String expected = "23:59";
        String res = itinerarioAdapter.setDuration(duration);

        assertEquals(expected, res);
    }

    @Test
    public void testSetDurationParameterGreaterThan1439(){
        int duration = 1440;
        assertThrows(IllegalArgumentException.class, () -> {
            itinerarioAdapter.setDuration(duration);
        });
    }

    @Test
    public void testSetDurationParameterModule60LessThan10(){
        int duration = 65;
        String expected = "1:05";
        String res = itinerarioAdapter.setDuration(duration);

        assertEquals(expected, res);
    }

    @Test
    public void testSetDurationParameterDivided60Equals0(){
        int duration = 5;
        String expected = "0:05";
        String res = itinerarioAdapter.setDuration(duration);

        assertEquals(expected, res);
    }
}
