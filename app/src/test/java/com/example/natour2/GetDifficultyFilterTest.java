package com.example.natour2;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import com.example.natour2.fragment.SearchFragment;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class GetDifficultyFilterTest {

    private static SearchFragment searchFragment;
    private static SearchFragment spySearchFragment;


    @BeforeClass
    public static void beforeClassFunction(){
        searchFragment = new SearchFragment();
        spySearchFragment = Mockito.spy(searchFragment);
    }

    @Test
    public void testGetDifficultyFilterParameterGreaterThanZero(){

        Mockito.doReturn(true).when(spySearchFragment).verifyChipValidity(Mockito.anyInt());

        Integer par = 3;
        Integer expected = 1;

        Integer res =  spySearchFragment.getDifficultyFilter(par);

        assertEquals(expected, res);

    }


    @Test
    public void testGetDifficultyFilterNoSelectedDifficulty(){

        Mockito.doReturn(false).when(spySearchFragment).verifyChipValidity(Mockito.anyInt());

        Integer par = 3;
        Integer expected = null;

        Integer res =  spySearchFragment.getDifficultyFilter(par);

        assertEquals(expected, res);

    }

    @Test
    public void testGetDifficultyFilterParameterEqualsZero(){

        Integer par = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            spySearchFragment.getDifficultyFilter(par);
        });

    }

    @Test
    public void testGetDifficultyFilterParameterLessThanZero(){

        Integer par = -5;

        assertThrows(IllegalArgumentException.class, () -> {
            spySearchFragment.getDifficultyFilter(par);
        });

    }

    @Test
    public void testGetDifficultyFilterParameterEqualsNull(){

        Integer par = null;

        assertThrows(IllegalArgumentException.class, () -> {
            spySearchFragment.getDifficultyFilter(par);
        });

    }


}
