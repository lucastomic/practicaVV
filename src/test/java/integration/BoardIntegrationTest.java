package integration;
import main.Board;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BoardIntegrationTest {

    @Test
    public void testUpdateWithAllMocks() {
        // Arrange
        Board board = Mockito.spy(new Board());
        board.setPlayer(mock(Player.class));

        // Mockear métodos individuales
        doNothing().when(board).update_shots();
        doNothing().when(board).update_aliens();
        doNothing().when(board).update_bomb();


        // Act
        board.update();

        // Assert: Verificar que se llamaron los métodos
        verify(board, times(1)).update_shots();
        verify(board, times(1)).update_aliens();
        verify(board, times(1)).update_bomb();
    }
    @Test
    public void testUpdateWithRealUpdateShots() {
        // Arrange
        Board board = Mockito.spy(new Board());
        board.setPlayer(mock(Player.class));

        // Reemplazamos `update_shots()` por el método real
        doCallRealMethod().when(board).update_shots();
        doNothing().when(board).update_aliens();
        doNothing().when(board).update_bomb();


        // Act
        board.update();

        // Assert
        verify(board, times(1)).update_shots();
        verify(board, times(1)).update_aliens();
        verify(board, times(1)).update_bomb();
    }

    @Test
    public void testUpdateWithRealUpdateAliens() {
        // Arrange
        Board board = Mockito.spy(new Board());
        board.setPlayer(mock(Player.class));

        // Reemplazar `update_aliens()` por el real
        doNothing().when(board).update_shots();
        doCallRealMethod().when(board).update_aliens();
        doNothing().when(board).update_bomb();

        // Act
        board.update();

        // Assert
        verify(board, times(1)).update_aliens();
    }
    @Test
    public void testUpdateWithRealUpdateBomb() {
        // Arrange
        Board board = Mockito.spy(new Board());
        board.setPlayer(mock(Player.class));

        // Reemplazar `update_bomb()` por el real
        doNothing().when(board).update_shots();
        doNothing().when(board).update_aliens();
        doCallRealMethod().when(board).update_bomb();

        // Act
        board.update();

        // Assert
        verify(board, times(1)).update_bomb();
    }
    @Test
    public void testUpdateWithAllRealMethods() {
        // Arrange
        Board board = new Board();

        // Act
        board.update();

        // Assert: Verificar que el estado se actualiza correctamente
        assertTrue(board.isInGame());
    }

    private List<Alien> createMockAliens() {
        List<Alien> aliens = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            aliens.add(new Alien(0, 0));
        }
        return aliens;
    }


}
