package space_invaders.sprites;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import main.Commons;

class AlienTest {

    Alien alien;

    // PRUEBAS InitAlien (ROBUST BOUNDARY VALUE TESTING)
    // Parámetros: Posicion X, Posicion Y

    @Nested
    class InitAlienTestBlackBox {

        // Prueba 1: Posición X e Y nominal dentro de los límites
        @Test
        void testConstructorWithNominalPosition() {
            int x = Commons.BOARD_WIDTH / 2;
            int y = Commons.BOARD_HEIGHT / 2;
            alien = new Alien(x, y);

            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Prueba 2: Posición X justo por debajo del máximo. Posición Y nominal
        @Test
        void testConstructorWithXNearMax() {
            int x = Commons.BOARD_WIDTH - 1;
            int y = Commons.BOARD_HEIGHT / 2;
            alien = new Alien(x, y);

            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Prueba 3: Posición X justo por encima del minimo. Posición Y nominal
        @Test
        void testConstructorWithXNearMin() {
            int x = 1;
            int y = Commons.BOARD_HEIGHT / 2;
            alien = new Alien(x, y);

            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Prueba 4: Posición X en el máximo. Posición Y nominal
        @Test
        void testConstructorWithXAtMax() {
            int x = Commons.BOARD_WIDTH;
            int y = Commons.BOARD_HEIGHT / 2;
            alien = new Alien(x, y);

            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Prueba 5: Posición X en el mínimo. Posición Y nominal
        @Test
        void testConstructorWithXAtMin() {
            int x = 0;
            int y = Commons.BOARD_HEIGHT / 2;
            alien = new Alien(x, y);

            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Prueba 6: Posición X nominal. Posición Y justo debajo del maximo
        @Test
        void testConstructorWithYNearMax() {
            int x = Commons.BOARD_WIDTH / 2;
            int y = Commons.BOARD_HEIGHT - 1;
            alien = new Alien(x, y);

            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Prueba 7: Posición X nominal. Posición Y justo por encima del mínimo
        @Test
        void testConstructorWithYNearMin() {
            int x = Commons.BOARD_WIDTH / 2;
            int y = 1;
            alien = new Alien(x, y);

            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Prueba 8: Posición X nominal. Posición Y en el máximo
        @Test
        void testConstructorWithYAtMax() {
            int x = Commons.BOARD_WIDTH / 2;
            int y = Commons.BOARD_HEIGHT;
            alien = new Alien(x, y);

            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Prueba 9: Posición X nominal. Posición Y en el mínimo
        @Test
        void testConstructorWithYAtMin() {
            int x = Commons.BOARD_WIDTH / 2;
            int y = 0;
            alien = new Alien(x, y);

            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Prueba 10 (ROBUST): Posición X por encima del máximo. Posición Y nominal
        @Test
        void robustTestConstructorWithXAboveMax() {
            int x = Commons.BOARD_WIDTH + 10; // Fuera del límite
            int y = Commons.BOARD_HEIGHT / 2;
            alien = new Alien(x, y);

            assertEquals(Commons.BOARD_WIDTH, alien.getX(), "La posición X debe ajustarse al límite máximo del tablero");
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Prueba 11 (ROBUST): Posición X negativa. Posición Y nominal
        @Test
        void robustTestConstructorWithXBelowMin() {
            int x = -10; // Fuera del límite
            int y = Commons.BOARD_HEIGHT / 2;
            alien = new Alien(x, y);

            assertEquals(0, alien.getX(), "La posición X negativa debe ajustarse al límite mínimo del tablero");
            assertEquals(y, alien.getY(), "La posición Y debería ser " + y);
        }

        // Prueba 12 (ROBUST): Posición X nominal. Posición Y por encima del máximo
        @Test
        void robustTestConstructorWithYAboveMax() {
            int x = Commons.BOARD_WIDTH / 2; // Fuera del límite
            int y = Commons.BOARD_HEIGHT + 10;
            alien = new Alien(x, y);

            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(Commons.BOARD_HEIGHT, alien.getY(), "La posición Y debe ajustarse al límite máximo del tablero");
        }

        // Prueba 13 (ROBUST): Posición X nominal. Posición Y negativa
        @Test
        void robustTestConstructorWithYBelowMin() {
            int x = Commons.BOARD_WIDTH / 2; // Fuera del límite
            int y = -10;
            alien = new Alien(x, y);

            assertEquals(x, alien.getX(), "La posición X debería ser " + x);
            assertEquals(0, alien.getY(), "La posición Y negativa debe ajustarse al límite mínimo del tablero");
        }

    }

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


    // PRUEBAS Act (NORMAL BOUNDARY VALUE TESTING)
    // Parámetros: Posición X del Alien, Dirección a mover en el eje X al alien

    @Nested
    class ActTestBlackBox {

        @BeforeEach
        void initializeAlien() {
            alien = new Alien(Commons.BOARD_WIDTH /2, Commons.BOARD_HEIGHT /2);
        }

        // Prueba 1: Posición X nominal, Dirección nominal (0)
        @Test
        void testActNominalValues() {
            int currentX = alien.getX();
            alien.act(0);

            assertEquals(currentX, alien.getX(), "La posición X debería ser " + currentX);
        }

        // Prueba 2: Posición X nominal, Dirección en el limite izquierdo (-1)
        @Test
        void testActNominalPositionToLeft() {
            int currentX = alien.getX();
            alien.act(-1);

            assertEquals(currentX-1, alien.getX(), "La posición X debería ser " + (currentX-1));
        }

        // Prueba 3: Posición X nominal, Dirección en el limite derecho (+1)
        @Test
        void testActNominalPositionToRight() {
            int currentX = alien.getX();
            alien.act(+1);

            assertEquals(currentX+1, alien.getX(), "La posición X debería ser " + (currentX+1));
        }

        // Prueba 4: Posición X justo por debajo del limite, Dirección nominal (0)
        @Test
        void testActWithXNearMax() {
            int currentX = Commons.BOARD_WIDTH - 1;
            alien.setX(currentX);
            alien.act(0);

            assertEquals(currentX, alien.getX(), "La posición X debería ser " + (currentX));
        }

        // Prueba 5: Posición X justo por encima del limite inferior, Dirección nominal (0)
        @Test
        void testActWithXNearMin() {
            int currentX = 1;
            alien.setX(currentX);
            alien.act(0);

            assertEquals(currentX, alien.getX(), "La posición X debería ser " + (currentX));
        }

        // Prueba 6: Posición X en el limite superior, Dirección nominal (0)
        @Test
        void testActWithXAtMax() {
            int currentX = Commons.BOARD_WIDTH;
            alien.setX(currentX);
            alien.act(0);

            assertEquals(currentX, alien.getX(), "La posición X debería ser " + (currentX));
        }

        // Prueba 7: Posición X en el limite inferior, Dirección nominal (0)
        @Test
        void testActWithXAtMin() {
            int currentX = 0;
            alien.setX(currentX);
            alien.act(0);

            assertEquals(currentX, alien.getX(), "La posición X debería ser " + (currentX));
        }

        // Prueba 8 (WORST CASE): Posición X justo por debajo del limite superior. Dirección en el limite superior (+1)
        @Test
        void worstCaseTestActWithXNearMaxToRight() {
            int currentX = Commons.BOARD_WIDTH - 1;
            alien.setX(currentX);
            alien.act(+1);

            assertEquals(Commons.BOARD_WIDTH, alien.getX(), "La posición X debería ser " + (Commons.BOARD_WIDTH));
        }

        // Prueba 9 (WORST CASE): Posición X justo por encima del limite inferior. Dirección en el limite inferior (-1)
        @Test
        void worstCaseTestActWithXNearMinToLeft() {
            int currentX = 1;
            alien.setX(currentX);
            alien.act(-1);

            assertEquals(0, alien.getX(), "La posición X debería ser " + (0));
        }

        // Prueba 10 (WORST CASE): Posición X en el limite superior. Dirección en el limite superior (+1)
        @Test
        void worstCaseTestActWithXAtMaxToRight() {
            int currentX = Commons.BOARD_WIDTH;
            alien.setX(currentX);
            alien.act(+1);

            assertEquals(Commons.BOARD_WIDTH, alien.getX(), "La posición X debería ser " + (Commons.BOARD_WIDTH));
        }

        // Prueba 11 (WORST CASE): Posición X en el limite inferior. Dirección en el limite inferior (-1)
        @Test
        void worstCaseTestActWithXAtMinToLeft() {
            int currentX = 0;
            alien.setX(currentX);
            alien.act(-1);

            assertEquals(0, alien.getX(), "La posición X debería ser " + (0));
        }
    }

}