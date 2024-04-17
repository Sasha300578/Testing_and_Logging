import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    void getHorsesNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    void getHorsesEmptyList() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.emptyList()));

        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    void getHorses() {
        List<Horse> horsesList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            horsesList.add(new Horse("sasha", i, 100-i));
        }

        Hippodrome hippodrome = new Hippodrome(horsesList);

        assertEquals(horsesList, hippodrome.getHorses());
    }

    @Test
    public void move(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for(Horse horse : horses){
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horsesList = new ArrayList<>();

        Horse misha = new Horse("misha", 2, 300);

        horsesList.add(misha);

        for (int i = 0; i < 30; i++) {
            horsesList.add(new Horse("sasha", i, 100-i));
        }

        Hippodrome hippodrome = new Hippodrome(horsesList);

        assertEquals(misha, hippodrome.getWinner());
    }
}