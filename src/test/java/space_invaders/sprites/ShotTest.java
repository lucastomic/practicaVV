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


        /*
    BLACKBOX TESTING
     */


    /*
    Teniendo en cuenta:
    int BOARD_WIDTH = 358;
    int BOARD_HEIGHT = 350;
    y que  H_SPACE = 6
           V_SPACE = -1
    Caso de prueba: Inicialización dentro de los límites (centro del tablero)
    Entrada: Shot(179, 175)
    Salida esperada: Las coordenadas del disparo deberían ser:
     (179 + H_SPACE, 175 - V_SPACE) = (185,174)
    */
    @Test
    void testShotInitializationWithinBounds() {
        int x = (Commons.BOARD_WIDTH) / 2;
        int y = (Commons.BOARD_HEIGHT) / 2;
        shot = new Shot(x, y);

        assertEquals(x + 6, shot.getX()); // H_SPACE = 6
        assertEquals(y - 1, shot.getY()); // V_SPACE = 1
    }

    /*
    Caso de prueba: Límite inferior
    Entrada: Shot(179, 0) y Shot(179, 1)
    Salida esperada: Las coordenadas del disparo deberían ser:
    (185, 0)
    (185, 0)
    */
    @Test
    void testShotInitializationAtLowerBounds() {
        shot = new Shot(Commons.BOARD_WIDTH / 2, 0);
        assertEquals(Commons.BOARD_WIDTH / 2 + 6, shot.getX());
        assertEquals(0, shot.getY());

        shot = new Shot(Commons.BOARD_WIDTH / 2, 1);
        assertEquals(Commons.BOARD_WIDTH / 2 + 6, shot.getX());
        assertEquals(0, shot.getY());
    }

    /*
    Caso de prueba: Fuera del límite inferior
    Entrada: Shot(179, -1)
    Salida esperada: Las coordenadas del disparo deberían ser:
    (179 + H_SPACE, 0)
    */
    @Test
    void testShotInitializationOutOfLowerBounds() {
        shot = new Shot(Commons.BOARD_WIDTH / 2, -1);
        assertEquals(Commons.BOARD_WIDTH / 2 + 6, shot.getX());
        assertEquals(0, shot.getY());
    }

    /*
    Caso de prueba: Límite superior
    Entrada: Shot(179, 351) y Shot(179, 350)
    Salida esperada: Las coordenadas del disparo deberían ser:
    (179 + H_SPACE, BOARD_HEIGHT - 1)  = (185, 350)
    (179 + H_SPACE, BOARD_HEIGHT - 1)  = (185, 349)
    */
    @Test
    void testShotInitializationAtUpperBounds() {
        shot = new Shot(Commons.BOARD_WIDTH / 2, Commons.BOARD_HEIGHT + 1);
        assertEquals(Commons.BOARD_WIDTH / 2 + 6, shot.getX());
        assertEquals(Commons.BOARD_HEIGHT, shot.getY());

        shot = new Shot(Commons.BOARD_WIDTH / 2, Commons.BOARD_HEIGHT);
        assertEquals(Commons.BOARD_WIDTH / 2 + 6, shot.getX());
        assertEquals(Commons.BOARD_HEIGHT - 1, shot.getY());
    }

    /*
    Caso de prueba: Fuera del límite superior
    Entrada: Shot(179, 352)
    Salida esperada: Las coordenadas del disparo deberían ser:
     (179 + H_SPACE, BOARD_HEIGHT) = (185,350)
    */
    @Test
    void testShotInitializationOutOfUpperBounds() {
        shot = new Shot(Commons.BOARD_WIDTH / 2, Commons.BOARD_HEIGHT + 2);
        assertEquals(Commons.BOARD_WIDTH / 2 + 6, shot.getX());
        assertEquals(Commons.BOARD_HEIGHT, shot.getY());
    }

    /*
    Caso de prueba: Límite izquierdo
    Entrada: Shot(0, 175) y Shot(1, 175)
    Salida esperada: Coordenadas del disparo en (6, 174) y (7, 174)
    */
    @Test
    void testShotInitializationAtLeftBounds() {
        shot = new Shot(0, Commons.BOARD_HEIGHT / 2);
        assertEquals(6, shot.getX());
        assertEquals(Commons.BOARD_HEIGHT / 2 - 1, shot.getY());

        shot = new Shot(1, Commons.BOARD_HEIGHT / 2);
        assertEquals(1 + 6, shot.getX());
        assertEquals(Commons.BOARD_HEIGHT / 2 - 1, shot.getY());
    }

    /*
    Caso de prueba: Fuera del límite izquierdo
    Entrada: Shot(-1, 175)
    Salida esperada: Coordenadas del disparo en (0 + H_SPACE, 174)
    */
    @Test
    void testShotInitializationOutOfLeftBounds() {
        shot = new Shot(-1, Commons.BOARD_HEIGHT / 2);
        assertEquals(6, shot.getX());
        assertEquals(Commons.BOARD_HEIGHT / 2 - 1, shot.getY());
    }

    /*
    Caso de prueba: Límite derecho
    Entrada: Shot(352, 175) y Shot(351, 175)
    Salida esperada:
    Coordenadas del disparo en
    (BOARD_WIDTH, 174) = (358,174)
    (BOARD_WIDTH - 1, 174) = (357, 174)
    */
    @Test
    void testShotInitializationAtRightBounds() {
        shot = new Shot(Commons.BOARD_WIDTH - 6, Commons.BOARD_HEIGHT / 2);
        assertEquals(Commons.BOARD_WIDTH, shot.getX());
        assertEquals(Commons.BOARD_HEIGHT / 2 - 1, shot.getY());

        shot = new Shot(Commons.BOARD_WIDTH - 7, Commons.BOARD_HEIGHT / 2);
        assertEquals(Commons.BOARD_WIDTH - 1, shot.getX());
        assertEquals(Commons.BOARD_HEIGHT / 2 - 1, shot.getY());
    }

    /*
    Caso de prueba: Fuera del límite derecho
    Entrada: Shot(353, 175)
    Salida esperada: Coordenadas del disparo en (BOARD_WIDTH, 174)
    */
    @Test
    void testShotInitializationOutOfRightBounds() {
        shot = new Shot(Commons.BOARD_WIDTH - 5, Commons.BOARD_HEIGHT / 2);
        assertEquals(Commons.BOARD_WIDTH , shot.getX());
        assertEquals(Commons.BOARD_HEIGHT / 2 - 1, shot.getY());
    }
    /*
    WHITEBOX TESTING
     */
    @Test
    public void testShot() {

        shot = new Shot(100, 200);

        int expectedX = 100 + 6;
        int expectedY = 200 - 1;

        assertEquals(expectedX, shot.getX());
        assertEquals(expectedY, shot.getY());
    }
}
