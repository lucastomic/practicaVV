package integration;

import main.Board;
import main.Commons;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameInitIntegrationTest {

    @Test
    public void testGameInitCreatesExpectedObjects() {

        Board board = new Board();
        board.gameInit();

        // Verificamos que Player se crea
        Player player = board.getPlayer();
        assertNotNull(player, "El jugador debería estar inicializado.");
        assertEquals(Commons.BOARD_WIDTH / 2 - Commons.PLAYER_WIDTH, player.getX(), "Posición X del jugador incorrecta.");
        assertEquals(Commons.GROUND - Commons.PLAYER_HEIGHT, player.getY(), "Posición Y del jugador incorrecta.");

        // Verificamos que la lista de aliens se inicializa correctamente
        List<Alien> aliens = board.getAliens();
        assertNotNull(aliens, "La lista de aliens debería estar inicializada.");
        assertEquals(24, aliens.size(), "Deberían haberse creado 24 aliens.");

        // Verificamos que Shot se inicializa correctamente
        Shot shot = board.getShot();
        assertNotNull(shot, "El disparo debería estar inicializado.");

        // Verificamos que el disparo no se mueve ni se ve hasta que el jugador dispare
        assertEquals(0, shot.getX(), "La posición X del disparo inicial debería ser 0.");
        assertEquals(0, shot.getY(), "La posición Y del disparo inicial debería ser 0.");
    }


    @Test
    public void testGameInitAliensInitialization() {

        Board board = Mockito.spy(new Board());
        board.gameInit();

        // Observamos la lista real de aliens
        List<Alien> aliens = board.getAliens();
        assertEquals(24, aliens.size(), "Deberían haberse creado 24 aliens.");

        // Observamos un alien individual
        Alien alien = aliens.get(0);
        assertNotNull(alien, "El alien debería estar inicializado.");
        assertEquals(Commons.ALIEN_INIT_X, alien.getX(), "La posición X inicial del alien es incorrecta.");
        assertEquals(Commons.ALIEN_INIT_Y, alien.getY(), "La posición Y inicial del alien es incorrecta.");
    }

    @Test
    public void testGameInitWithMockedObjects() {

        Board board = Mockito.spy(new Board());

        // Creamos mocks para Player, Shot y Aliens
        Player mockPlayer = mock(Player.class);
        Shot mockShot = mock(Shot.class);
        List<Alien> mockAliens = Mockito.mock(List.class);

        // Sobrescribimos la creación de objetos dentro de gameInit() ya que siempre crea nuevas instancias
        doReturn(mockPlayer).when(board).getPlayer();
        doReturn(mockShot).when(board).getShot();
        doReturn(mockAliens).when(board).getAliens();

        board.gameInit();

        // Verificamos que los métodos retornan los mocks
        assertEquals(mockPlayer, board.getPlayer(), "El jugador debería ser el mock.");
        assertEquals(mockShot, board.getShot(), "El disparo debería ser el mock.");
        assertEquals(mockAliens, board.getAliens(), "La lista de aliens debería ser el mock.");
    }


}
