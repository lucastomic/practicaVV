package main;

import org.junit.jupiter.api.Test;
import space_invaders.sprites.Alien;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class BoardTest {
    @Test
    void testUpdateAliensIntegration() {
        // Preparar mocks

        Alien mockAlien1 = mock(Alien.class);
        Alien mockAlien2 = mock(Alien.class);

        /*
        // Configurar bomb para evitar NullPointerException
        Alien.Bomb mockBomb1 = mock(Alien.Bomb.class);
        Alien.Bomb mockBomb2 = mock(Alien.Bomb.class);
        when(mockAlien1.getBomb()).thenReturn(mockBomb1);
        when(mockAlien2.getBomb()).thenReturn(mockBomb2);
        when(mockBomb1.isDestroyed()).thenReturn(false);
        when(mockBomb2.isDestroyed()).thenReturn(false);

         */



        // Configurar posiciones iniciales
        int initialY = 100;
        int expectedY = initialY + Commons.GO_DOWN;
        int initialDirection = 1;
        int expectedDirection = -1;
        when(mockAlien1.getX()).thenReturn(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT + 1); // Cerca del borde derecho
        when(mockAlien1.getY()).thenReturn(100); // En una posición segura
        when(mockAlien2.getX()).thenReturn(Commons.BORDER_LEFT + 1); // Cerca del borde izquierdo
        when(mockAlien2.getY()).thenReturn(100); // En una posición segura
        when(mockAlien1.isVisible()).thenReturn(true);
        when(mockAlien2.isVisible()).thenReturn(true);

        // Crear lista simulada de aliens
        List<Alien> aliens = Arrays.asList(mockAlien1, mockAlien2);

        // Instancia del tablero
        Board board = new Board();
        board.setAliens(aliens);

        // Dirección inicial (moviendo hacia la derecha)
        board.setDirection(initialDirection);

        // Ejecutar el método
        board.update_aliens();

        // Verificaciones:
        // 1. El alien cercano al borde derecho debe cambiar la dirección global a la izquierda (-1)
        verify(mockAlien1, never()).act(anyInt()); // No debería moverse porque cambia dirección
        assertEquals(expectedDirection, board.getDirection()); // Dirección cambiada a la izquierda

        // 2. Los aliens deben moverse hacia abajo
        verify(mockAlien1).setY(eq(expectedY));
        verify(mockAlien2).setY(eq(expectedY));

        // 3. Los aliens deben actuar en la nueva dirección
        verify(mockAlien1).act(expectedDirection);
        verify(mockAlien2).act(expectedDirection);

        // 4. Verificar condición de fin del juego si alien alcanza el límite inferior
        when(mockAlien1.getY()).thenReturn(Commons.GROUND - Commons.ALIEN_HEIGHT + 1);
        board.update_aliens();
        assertFalse(board.isInGame()); // El juego debería terminar
        assertEquals("Invasion!", board.getMessage()); // Verificar el mensaje de fin del juego
    }
}
