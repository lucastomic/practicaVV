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

    // Test para initPlayer
    @Test
    public void testInitPlayer() {
        Player player = new Player();

        // Verificar que la imagen se ha cargado correctamente
        assertNotNull(player.getImage(), "La imagen del jugador no se ha cargado.");

        // Verificar que las coordenadas x y y sean las esperadas
        assertEquals(270, player.getX(), "La posición X del jugador no es correcta.");
        assertEquals(280, player.getY(), "La posición Y del jugador no es correcta.");
    }

    //Test en el límite izquierdo.
    @Test
    public void testActAtLeftBoundary() {
        player.setX(2); // Establecer x al límite izquierdo
        player.dx = -2;  // Intentar mover a la izquierda (hacia el borde)

        player.act();

        // Verificar que el jugador no se mueva más allá del borde izquierdo
        assertEquals(2, player.getX(), "El jugador no debe moverse más allá del borde izquierdo.");
    }

    //Test fuera del límite izquierdo.
    @Test
    public void testActBeyondLeftBoundary() {
        player.setX(0); // Establecer x fuera del límite izquierdo
        player.dx = -2; // Intentar mover a la izquierda
        player.act();
        assertEquals(2, player.getX(), "El jugador no debe moverse más allá del borde izquierdo.");
    }



    // Test en el límite derecho.
    @Test
    public void testActAtRightBoundary() {
        player.setX(Commons.BOARD_WIDTH - 2 * player.getWidth());  // Establecer en el borde derecho
        player.dx = 2;  // Intentar mover a la derecha

        player.act();

        // Verificar que el jugador no se mueva más allá del borde derecho
        assertEquals(Commons.BOARD_WIDTH - 2 * player.getWidth(), player.getX(), "El jugador no debe moverse más allá del borde derecho.");
    }

    //Test fuera del límite derecho.
    @Test
    public void testActBeyondRightBoundary() {
        player.setX(Commons.BOARD_WIDTH - player.getWidth()); // Establecer fuera del borde derecho
        player.dx = 2; // Intentar mover a la derecha
        player.act();
        assertEquals(Commons.BOARD_WIDTH - 2 * player.getWidth(), player.getX(), "El jugador no debe moverse más allá del borde derecho.");
    }

    //Test en posición intermedia, moviendo hacia la derecha.
    @Test
    public void testActMoveRightWithinBounds() {
        player.setX(100); // Establecer en una posición intermedia
        player.dx = 2; // Mover a la derecha
        player.act();
        assertEquals(102, player.getX(), "El jugador debe moverse hacia la derecha.");
    }

    //Test en posición intermedia, moviendo hacia la izquierda.
    @Test
    public void testActMoveLeftWithinBounds() {
        player.setX(102); // Establecer en una posición intermedia
        player.dx = -2; // Mover a la izquierda
        player.act();
        assertEquals(100, player.getX(), "El jugador debe moverse hacia la izquierda.");
    }

    //Test sin teclas presionadas.
    @Test
    public void testNoKeyPress() {
        int initialDx = player.dx;  // Guardar el valor inicial de dx

        // No presionar ninguna tecla
        player.keyReleased(new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_LEFT, ' '));
        player.keyReleased(new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_RIGHT, ' '));

        // Verificar que dx no ha cambiado
        assertEquals(initialDx, player.dx, "dx no debe cambiar si no se presiona ninguna tecla.");
    }


    // Test para la tecla izquierda (presionar y soltar)
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

    // Test para la tecla derecha (presionar y soltar)
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

    // Test para teclas rápidas (presionar y soltar rápidamente)
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

    // Test para teclas no relacionadas (tecla UP no debe afectar a dx)
    @Test
    public void testKeyPressedNonMovement() {
        KeyEvent upKeyPress = new KeyEvent(new java.awt.Component() {}, 0, 0, 0, KeyEvent.VK_UP, ' ');

        // Presionar tecla UP (no relacionada con movimiento)
        player.keyPressed(upKeyPress);
        assertEquals(0, player.dx, "dx no debe cambiar al presionar la tecla UP.");

        // Soltar tecla UP (no relacionada con movimiento)
        player.keyReleased(upKeyPress);
        assertEquals(0, player.dx, "dx no debe cambiar al soltar la tecla UP.");
    }
}
