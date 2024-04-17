import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    @Test
    public void nullNameException(){
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    public void nullNameMessage(){
        try{
            new Horse(null, 1, 1);
        } catch (IllegalArgumentException e){
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "    ",
        "\t\t",
        "\n\n\n\n"
    })
    public void BlankNameException(String name){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));

        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void NegativeSpeedException(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("name", -1, 1));

        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    public void NegativeDistanceException(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("name", 1, -1));

        assertEquals("Distance cannot be negative.", e.getMessage());
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "Sasha",
            "Misha"
    })
    void getName(String name) {
        Horse horse = new Horse(name, 1, 1);
        assertEquals(name,  horse.getName());
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "4",
    })
    void getSpeed(int speed) {
        Horse horse = new Horse("Sasha", speed, 1);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    void getDistanceTwoParameters() {
        Horse horse = new Horse("sasha", 1);
        assertEquals(0, horse.getDistance());
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "4",
    })
    void getDistance(int distance) {
        Horse horse = new Horse("sasha", 1, distance);
        assertEquals(distance, horse.getDistance());

        horse = new Horse("sasha", 1);
        assertEquals(0, horse.getDistance());
    }

    @Test
    void moveGetRandomDouble() {
        try(MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
            new Horse("sasha", 1, 1).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0.35",
            "0.48",
            "1.00",
            "0.0",
            "2000.0"
    })
    void move(double value) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(value);

            Horse horse = new Horse("MyHorse", 10, 5);
            horse.move();

            double expectedDistance = 5 + 10 * value;
            assertEquals(expectedDistance, horse.getDistance());
        }
    }
}