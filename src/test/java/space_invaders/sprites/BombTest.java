package space_invaders.sprites;

import main.Commons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class BombTest {
    private Alien alien;
    private Alien.Bomb bomb;


        /*
    BLACKBOX TESTING
     */
   /*
    Valores dentro de los límites para pruebas inciales
    int BOARD_WIDTH = 358;
    int BOARD_HEIGHT = 350;
     */
        @BeforeEach
        void setUp() {
            alien = new Alien(Commons.BOARD_WIDTH/2, Commons.BOARD_HEIGHT/2);
            bomb = alien.new Bomb(Commons.BOARD_WIDTH/2, Commons.BOARD_HEIGHT/2);
        }

    /*
    Centro
    Siendo:
    int BOARD_WIDTH = 358;
    int BOARD_HEIGHT = 350;
    Entrada: Bomb(179,175)
    Salida esperada: La bomba debe inicializarse en la posición (179,175) y no debe estar destruida.
     */
    @Test
    void testBombInitializationWithinBounds() {
        alien = new Alien(Commons.BOARD_WIDTH/2, Commons.BOARD_HEIGHT/2);
        bomb = alien.new Bomb(Commons.BOARD_WIDTH/2, Commons.BOARD_HEIGHT/2);
        assertEquals(Commons.BOARD_WIDTH/2, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT/2, bomb.getY());
        assertFalse(bomb.isDestroyed());
    }

    /*
    Borde inferior
    Entrada: Bomb(179,0)
    Salida esperada: La bomba debe inicializarse en la posición (179,0) y no debe estar destruida.

        Entrada: Bomb(179,1)
    Salida esperada: La bomba debe inicializarse en la posición (179,1) y no debe estar destruida.
     */
    @Test
    void testBombInitializationWithinLowerBounds() {
        alien = new Alien(Commons.BOARD_WIDTH/2, 0);
        bomb = alien.new Bomb(Commons.BOARD_WIDTH/2, 0);
        assertEquals(Commons.BOARD_WIDTH/2, bomb.getX());
        assertEquals(0, bomb.getY());
        assertFalse(bomb.isDestroyed());

        alien = new Alien(Commons.BOARD_WIDTH/2, 1);
        bomb = alien.new Bomb(Commons.BOARD_WIDTH/2, 1);
        assertEquals(Commons.BOARD_WIDTH/2, bomb.getX());
        assertEquals(1, bomb.getY());
        assertFalse(bomb.isDestroyed());
    }

    /*
    Fuera del borde inferior
    Entrada: Bomb(179,-1)
    Salida esperada: La bomba debe inicializarse en la posición (179,0) y no debe estar destruida.
     */

    @Test
    void testBombInitializationOutOfLowerBounds() {
        alien = new Alien(Commons.BOARD_WIDTH/2, -1);
        bomb = alien.new Bomb(Commons.BOARD_WIDTH/2, -1);
        assertEquals(Commons.BOARD_WIDTH/2, bomb.getX());
        assertEquals(0, bomb.getY());
        assertFalse(bomb.isDestroyed());
    }

    /*
    Borde superior
    Entrada: Bomb(179, 350)
    Salida esperada: La bomba debe inicializarse en la posición (179,350) y no debe estar destruida.

    Entrada: Bomb(179, 349)
    Salida esperada: La bomba debe inicializarse en la posición (179,349) y no debe estar destruida.
     */

    @Test
    void testBombInitializationWithinUpperBounds() {
        alien = new Alien(Commons.BOARD_WIDTH / 2, Commons.BOARD_HEIGHT);
        bomb = alien.new Bomb(Commons.BOARD_WIDTH / 2, Commons.BOARD_HEIGHT);
        assertEquals(Commons.BOARD_WIDTH / 2, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT, bomb.getY());
        assertFalse(bomb.isDestroyed());

        alien = new Alien(Commons.BOARD_WIDTH / 2, Commons.BOARD_HEIGHT-1);
        bomb = alien.new Bomb(Commons.BOARD_WIDTH / 2, Commons.BOARD_HEIGHT-1);
        assertEquals(Commons.BOARD_WIDTH / 2, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT-1, bomb.getY());
        assertFalse(bomb.isDestroyed());

    }

    /*
    Fuera del borde superior
    Entrada: Bomb(179, 351)
    Salida esperada: La bomba debe inicializarse en la posición (179,350) y no debe estar destruida.
     */

    @Test
    void testBombInitializationOutOfUpperBounds() {
        alien = new Alien(Commons.BOARD_WIDTH / 2,Commons.BOARD_HEIGHT+1);
        bomb = alien.new Bomb(Commons.BOARD_WIDTH / 2, Commons.BOARD_HEIGHT+1);
        assertEquals(Commons.BOARD_WIDTH / 2, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT, bomb.getY());
        assertFalse(bomb.isDestroyed());

    }

        /*
    Borde izquierdo
    Entrada: Bomb(0, 175)
    Salida esperada: La bomba debe inicializarse en la posición (0,175) y no debe estar destruida.

    Entrada: Bomb(1, 175)
    Salida esperada: La bomba debe inicializarse en la posición (0,175) y no debe estar destruida.
     */

    @Test
    void testBombInitializationWithinLeftBounds() {
        alien = new Alien(0,Commons.BOARD_HEIGHT / 2);
        bomb = alien.new Bomb(0, Commons.BOARD_HEIGHT / 2);
        assertEquals(0, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT / 2, bomb.getY());
        assertFalse(bomb.isDestroyed());

        alien = new Alien(1,Commons.BOARD_HEIGHT / 2);
        bomb = alien.new Bomb(1, Commons.BOARD_HEIGHT / 2);
        assertEquals(1, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT / 2, bomb.getY());
        assertFalse(bomb.isDestroyed());
    }

        /*
    Fuera del borde izquierdo
    Entrada: Bomb(-1, 175)
    Salida esperada: La bomba debe inicializarse en la posición (0,175) y no debe estar destruida.
     */

    @Test
    void testBombInitializationOutOfLeftBounds() {
        alien = new Alien(-1,Commons.BOARD_HEIGHT/2);
        bomb = alien.new Bomb(-1, Commons.BOARD_HEIGHT/2);
        assertEquals(0, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT/2, bomb.getY());
        assertFalse(bomb.isDestroyed());
    }

            /*
    Borde Derecho
    Entrada: Bomb(358, 175)
    Salida esperada: La bomba debe inicializarse en la posición (358,175) y no debe estar destruida.

    Entrada: Bomb(357, 175)
    Salida esperada: La bomba debe inicializarse en la posición (357,175) y no debe estar destruida.
     */

    @Test
    void testBombInitializationWithinRightBounds() {
        alien = new Alien(Commons.BOARD_WIDTH,Commons.BOARD_HEIGHT / 2);
        bomb = alien.new Bomb(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT / 2);
        assertEquals(Commons.BOARD_WIDTH, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT / 2, bomb.getY());
        assertFalse(bomb.isDestroyed());

        alien = new Alien(Commons.BOARD_WIDTH-1,Commons.BOARD_HEIGHT / 2);
        bomb = alien.new Bomb(Commons.BOARD_WIDTH-1, Commons.BOARD_HEIGHT / 2);
        assertEquals(Commons.BOARD_WIDTH-1, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT / 2, bomb.getY());
        assertFalse(bomb.isDestroyed());
    }

        /*
    Fuera del borde derecho
    Entrada: Bomb(359, 175)
    Salida esperada: La bomba debe inicializarse en la posición (358,175) y no debe estar destruida.
     */

    @Test
    void testBombInitializationOutOfRightBounds() {
        alien = new Alien(Commons.BOARD_WIDTH+1,Commons.BOARD_HEIGHT / 2);
        bomb = alien.new Bomb(Commons.BOARD_WIDTH+1, Commons.BOARD_HEIGHT / 2);
        assertEquals(Commons.BOARD_WIDTH, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT / 2, bomb.getY());
        assertFalse(bomb.isDestroyed());
    }
    /*
    WHITEBOX TESTING
     */
 
    @Test
    public void testInitBomb_BothConditionsTrue() {
        bomb = new Alien(0, 0).new Bomb(100, 100);
        assertEquals(100, bomb.getX());
        assertEquals(100, bomb.getY() );
    }

    @Test
    public void testInitBomb_XTrue_YFalse() {
        bomb = new Alien(0, 0).new Bomb(100, 400);
        assertEquals(100, bomb.getX());
        assertEquals(Commons.BOARD_HEIGHT, bomb.getY());
    }

    @Test
    public void testInitBomb_XFalse_YTrue() {
        bomb = new Alien(0, 0).new Bomb(-1, 100);
        assertEquals(0, bomb.getX());
        assertEquals(100, bomb.getY());
    }

    @Test
    public void testInitBomb_BothConditionsFalse() {
        bomb = new Alien(0, 0).new Bomb(400, -1);
        assertEquals(Commons.BOARD_WIDTH, bomb.getX());
        assertEquals(0, bomb.getY());
    }


}

