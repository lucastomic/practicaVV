package space_invaders.sprites;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import main.Commons;

class AlienTest {

    Alien alien;

    @Nested
    class InitAlienTextWhiteBox{
        // Camino 1 (X e Y mayores que 0 y dentro de los límites del board):
        @Test
        void testInitAlienWithinBounds() {
            int x = Commons.BOARD_WIDTH / 2;
            int y = Commons.BOARD_HEIGHT / 2;
            alien = new Alien(x, y);
            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Camino 2 (X e Y mayores que 0. X fuera del board, Y dentro):
        @Test
        void testInitAlienXOutsideBoard() {
            int x = Commons.BOARD_WIDTH + 1;
            int y = Commons.BOARD_HEIGHT / 2;
            alien = new Alien(x, y);
            assertEquals(Commons.BOARD_WIDTH, alien.getX(), "La posición X debería ser " + Commons.BOARD_WIDTH);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Camino 3 (X e Y mayores que 0. X dentro del board, Y fuera)
        @Test
        void testInitAlienYOutsideBoard() {
            int x = Commons.BOARD_WIDTH / 2;
            int y = Commons.BOARD_HEIGHT + 1;
            alien = new Alien(x, y);
            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(Commons.BOARD_HEIGHT, alien.getY(), "La posición Y debería ser " + Commons.BOARD_HEIGHT);
        }

        // Camino 4 (X menor que 0, Y mayor. X e Y dentro del board)
        @Test
        void testInitAlienXBelow0() {
            int x = -1;
            int y = Commons.BOARD_HEIGHT / 2;
            alien = new Alien(x, y);
            assertEquals(0, alien.getX(), "La posición X debería ser " + 0);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Camino 5 (X mayor que 0, Y menor. X e Y dentro del board)
        @Test
        void testInitAlienYBelow0() {
            int x = Commons.BOARD_WIDTH / 2;
            int y = -1;
            alien = new Alien(x, y);
            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(0, alien.getY(), "La posición Y debería ser " + 0);
        }
    }



}