package integration;
import main.Board;
import main.Commons;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BoardIntegrationTest {
    @Test
    public void testUpdateWithAllMocks() {
        Board board = Mockito.spy(new Board(false));

        //Mock del player
        Player player = mock(Player.class);
        when(player.isVisible()).thenReturn(true);
        when(player.getX()).thenReturn(Commons.BOARD_WIDTH / 2 - Commons.PLAYER_WIDTH);
        when(player.getY()).thenReturn(Commons.GROUND - Commons.PLAYER_HEIGHT);
        board.setPlayer(player);

        //Mock de aliens
        board.setAliens(createMockAliens());

        // Mockear métodos individuales
        doAnswer(invocation -> {
            board.getShot().die();
            return null;
        }).when(board).update_shots();
        doNothing().when(board).update_aliens();
        doNothing().when(board).update_bomb();

        // Act
        board.update();

        // Assert: Verificar que se llamaron los métodos
        verify(board, times(1)).update_shots();
        verify(board, times(1)).update_aliens();
        verify(board, times(1)).update_bomb();
        verify(player, times(1)).act();

        // Assert: Verificar funcionamiento esperado
        generalAsserts(board);
    }
    @Test
    public void testUpdateWithRealPlayer() {
        // Arrange
        Board board = Mockito.spy(new Board(false));
        // Utilizamos un player real
        board.setPlayer(new Player());

        //Mock de aliens
        board.setAliens(createMockAliens());

        // Mockear métodos individuales
        doAnswer(invocation -> {
            board.getShot().die();
            return null;
        }).when(board).update_shots();
        doNothing().when(board).update_aliens();
        doNothing().when(board).update_bomb();

        // Act
        board.update();

        // Assert
        verify(board, times(1)).update_shots();
        verify(board, times(1)).update_aliens();
        verify(board, times(1)).update_bomb();


        // Assert: Verificar funcionamiento esperado
        generalAsserts(board);
    }

    @Test
    public void testUpdateWithRealUpdateShots() {
        // Arrange
        Board board = Mockito.spy(new Board(false));
        board.setPlayer(new Player());

        //Mock de aliens
        board.setAliens(createMockAliens());

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

        // Assert: Verificar funcionamiento esperado
        generalAsserts(board);
    }

    @Test
    public void testUpdateWithRealAliens() {
        // Arrange
        Board board = Mockito.spy(new Board(false));
        board.setPlayer(new Player());

        doCallRealMethod().when(board).update_shots();
        doAnswer(interaction-> {
            for (Alien alien : board.getAliens()) {alien.act(board.getDirection());}
            return null;
        }).when(board).update_aliens();
        doNothing().when(board).update_bomb();

        // Act
        board.update();

        // Assert
        verify(board, times(1)).update_aliens();
        verify(board, times(1)).update_shots();
        verify(board, times(1)).update_bomb();

        // Assert: Verificar funcionamiento esperado
        generalAsserts(board);
    }

    @Test
    public void testUpdateWithRealUpdateAliens() {
        // Arrange
        Board board = Mockito.spy(new Board(false));
        board.setPlayer(new Player());
        board.setTimer(mock(Timer.class));

        // Reemplazar `update_aliens()` por el real
        doCallRealMethod().when(board).update_shots();
        doCallRealMethod().when(board).update_aliens();
        doNothing().when(board).update_bomb();

        // Act
        board.update();

        // Assert
        verify(board, times(1)).update_aliens();
        verify(board, times(1)).update_shots();
        verify(board, times(1)).update_bomb();

        // Assert: Verificar funcionamiento esperado
        generalAsserts(board);
    }


    @Test
    public void testUpdateWithAllRealMethods() {
        // Arrange
        Board board = new Board(false);

        // Act
        board.update();

        // Assert: Verificar que el estado se actualiza correctamente
        generalAsserts(board);
    }

    public void generalAsserts(Board board) {
        assertTrue(board.isInGame());
        assertFalse(board.getShot().isVisible(), "El disparo no debería estar activo");
        assertFalse(board.getAliens().get(0).isDying(), "El alien no debería estar en estado 'muriendo'");
        assertFalse(board.getPlayer().isDying(), "El player no debería estar en estado 'muriendo'");
        assertTrue(board.getPlayer().isVisible(), "El player debería ser visible");
        assertEquals(board.getPlayer().getX(), Commons.BOARD_WIDTH / 2 - Commons.PLAYER_WIDTH , "El jugador debería estsar en el medio del tablero");
        assertEquals(board.getPlayer().getY(), Commons.GROUND - Commons.PLAYER_HEIGHT, "El jugador debería estsar en el borde inferior del tablero");
        assertEquals(board.getAliens().get(0).getX(), Commons.ALIEN_INIT_X - 1, "El alien se debería mover 1 posicion a la izquierda");
    }

    private List<Alien> createMockAliens() {
        List<Alien> aliens = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Alien alien = mock(Alien.class);
            when(alien.isVisible()).thenReturn(true);
            when(alien.getX()).thenReturn(149);
            aliens.add(alien);
        }
        return aliens;
    }
}
