package space_invaders.sprites;

import main.Commons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player();
    }

    @Test
    public void testActAtLeftBoundary() {
        // Inicio posición inicial cerca del borde izquierdo
        player.setX(2); // Establecer x al límite
        player.dx = 2;  // Intentar mover a la derecha

        player.act();

        assertEquals(2, player.getX(), "El jugador no debe moverse más allá del borde izquierdo.");
    }

    @Test
    public void testActAtRightBoundary() {
        // Establecer la posición en el borde derecho
        player.setX(Commons.BOARD_WIDTH - 2 * player.getWidth());
        player.dx = 2; // Intentar mover a la derecha

        player.act();

        assertEquals(Commons.BOARD_WIDTH - 2 * player.getWidth(), player.getX(), "El jugador no debe moverse más allá del borde derecho.");
    }

    @Test
    public void testKeyPressedAndReleasedLeft() {
        KeyEvent leftKeyPress = new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_LEFT, ' ');
        KeyEvent leftKeyRelease = new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_LEFT, ' ');

        // Presionar tecla izquierda
        player.keyPressed(leftKeyPress);
        assertEquals(2, player.dx, "dx debe ser 2 al presionar la tecla izquierda.");

        // Soltar tecla izquierda
        player.keyReleased(leftKeyRelease);
        assertEquals(0, player.dx, "dx debe ser 0 al soltar la tecla izquierda.");
    }

    @Test
    public void testKeyPressedAndReleasedRight() {
        KeyEvent rightKeyPress = new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_RIGHT, ' ');
        KeyEvent rightKeyRelease = new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_RIGHT, ' ');

        // Presionar tecla derecha
        player.keyPressed(rightKeyPress);
        assertEquals(2, player.dx, "dx debe ser 2 al presionar la tecla derecha.");

        // Soltar tecla derecha
        player.keyReleased(rightKeyRelease);
        assertEquals(0, player.dx, "dx debe ser 0 al soltar la tecla derecha.");
    }

    @Test
    public void testRapidKeyPresses() {
        KeyEvent leftKeyPress = new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_LEFT, ' ');
        KeyEvent rightKeyPress = new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_RIGHT, ' ');

        // Presionar tecla izquierda
        player.keyPressed(leftKeyPress);
        assertEquals(2, player.dx, "dx debe ser 2 al presionar la tecla izquierda.");

        // Soltar tecla izquierda
        player.keyReleased(leftKeyPress);
        assertEquals(0, player.dx, "dx debe ser 0 al soltar la tecla izquierda.");

        // Presionar tecla derecha
        player.keyPressed(rightKeyPress);
        assertEquals(2, player.dx, "dx debe ser 2 al presionar la tecla derecha.");

        // Soltar tecla derecha
        player.keyReleased(rightKeyPress);
        assertEquals(0, player.dx, "dx debe ser 0 al soltar la tecla derecha.");
    }
}