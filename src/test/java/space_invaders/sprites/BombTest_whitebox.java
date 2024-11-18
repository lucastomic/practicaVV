package space_invaders.sprites;

import main.Commons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class BombTest {
    private Alien alien;
    private Alien.Bomb bomb;

 
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
        assertEquals(Commons.BOARD_HEIGHT, bomb.getY());
    }


}

