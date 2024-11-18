package space_invaders.sprites;

import main.Commons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShotTest {

    private Shot shot;

    @BeforeEach
    void setUp() {
        shot = new Shot();
    }


    @Test
    public void testShot() {

        shot = new Shot(100, 200);

        int expectedX = 100 + 6;
        int expectedY = 200 - 1;

        assertEquals(expectedX, shot.getX());
        assertEquals(expectedY, shot.getY());
    }
}
