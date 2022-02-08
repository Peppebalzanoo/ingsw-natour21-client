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
        Integer numberOfPossibleChoices = 3;
        Integer expected = 1;

        Integer res =  spySearchFragment.getDifficultyFilter(numberOfPossibleChoices);

        assertEquals(expected, res);

    }


    @Test
    public void testGetDifficultyFilterNoSelectedDifficulty(){

        Mockito.doReturn(false).when(spySearchFragment).verifyChipValidity(Mockito.anyInt());

        Integer numberOfPossibleChoices = 3;
        Integer expected = null;

        Integer res =  spySearchFragment.getDifficultyFilter(numberOfPossibleChoices);

        assertEquals(expected, res);

    }

    @Test
    public void testGetDifficultyFilterParameterEqualsZero(){

        Integer numberOfPossibleChoices = 0;

        assertThrows(IllegalArgumentException.class, () -> {
            spySearchFragment.getDifficultyFilter(numberOfPossibleChoices);
        });

    }

    @Test
    public void testGetDifficultyFilterParameterLessThanZero(){

        Integer numberOfPossibleChoices = -5;

        assertThrows(IllegalArgumentException.class, () -> {
            spySearchFragment.getDifficultyFilter(numberOfPossibleChoices);
        });

    }

    @Test
    public void testGetDifficultyFilterParameterEqualsNull(){

        Integer numberOfPossibleChoices = null;

        assertThrows(IllegalArgumentException.class, () -> {
            spySearchFragment.getDifficultyFilter(numberOfPossibleChoices);
        });

    }



    @Test
    public void testGetDifficultyFilter_Path_2_3(){

        Integer numberOfPossibleChoices = -10   ;

        assertThrows(IllegalArgumentException.class, () -> {
            spySearchFragment.getDifficultyFilter(numberOfPossibleChoices);
        });

    }


    @Test
    public void testGetDifficultyFilter_Path_2_5_6_7_8_12_13_15(){

        Mockito.doReturn(true).when(spySearchFragment).verifyChipValidity(Mockito.anyInt());
        Integer numberOfPossibleChoices = 3;
        Integer expected = 1;

        Integer res =  spySearchFragment.getDifficultyFilter(numberOfPossibleChoices);

        assertEquals(expected, res);

    }

    @Test
    public void testGetDifficultyFilter_Path_2_5_6_7_8_10_12_13_15(){

        Mockito.doReturn(false)
                .doReturn(true).when(spySearchFragment).verifyChipValidity(Mockito.anyInt());
        Integer numberOfPossibleChoices = 3;
        Integer expected = 2;

        Integer res =  spySearchFragment.getDifficultyFilter(numberOfPossibleChoices);

        assertEquals(expected, res);

    }

    @Test
    public void testGetDifficultyFilter_Path_2_5_6_7_10_12_13_14(){

        Mockito.doReturn(false).when(spySearchFragment).verifyChipValidity(Mockito.anyInt());
        Integer numberOfPossibleChoices = 3;
        Integer expected = null;

        Integer res =  spySearchFragment.getDifficultyFilter(numberOfPossibleChoices);

        assertEquals(expected, res);

    }

}
