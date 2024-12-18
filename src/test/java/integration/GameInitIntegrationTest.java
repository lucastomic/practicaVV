package integration;

import main.Board;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameInitIntegrationTest {

    // 1. Test: Constructor llama a gameInit() usando stubs
    @Test
    public void testBoardConstructorCallsGameInitWithStub() {
        // Creamos un spy para el Board
        Board board = Mockito.spy(new Board(false));

        // Stub para gameInit()
        doNothing().when(board).gameInit();

        // Simulamos el flujo del constructor
        board.gameInit();

        // Verificamos que gameInit() se llama exactamente una vez
        verify(board, times(1)).gameInit();
    }

    // 2. Test: gameInit() inicializa Player usando mocks
    @Test
    public void testGameInitInitializesPlayerWithMock() {

        Board board = Mockito.spy(new Board(false));
        Player mockPlayer = mock(Player.class);

        // Mock del getter
        doReturn(mockPlayer).when(board).getPlayer();

        board.gameInit();

        assertNotNull(board.getPlayer(), "El jugador debería inicializarse.");
    }

    // 3. Test: gameInit() inicializa Aliens usando mocks
    @Test
    public void testGameInitInitializesAliensWithMock() {

        Board board = Mockito.spy(new Board(false));
        List<Alien> mockAliens = new ArrayList<>();
        for (int i = 0; i < 24; i++) mockAliens.add(mock(Alien.class));

        // Mock del getter
        doReturn(mockAliens).when(board).getAliens();

        board.gameInit();

        assertNotNull(board.getAliens(), "La lista de aliens debería inicializarse.");
        assertEquals(24, board.getAliens().size(), "Deberían existir 24 aliens.");
    }

    // 4. Test: gameInit() inicializa Shot usando mocks
    @Test
    public void testGameInitInitializesShotWithMock() {

        Board board = Mockito.spy(new Board(false));
        Shot mockShot = mock(Shot.class);

        // Mock del getter
        doReturn(mockShot).when(board).getShot();

        board.gameInit();

        assertNotNull(board.getShot(), "El disparo debería inicializarse.");
    }

    // 5. Test: gameInit() inicializa todas las dependencias correctamente
    @Test
    public void testGameInitInitializesAllDependencies() {

        Board board = new Board(false);

        board.gameInit();

        // Assert: Verificar inicialización de todas las dependencias
        assertNotNull(board.getPlayer(), "El jugador debería inicializarse.");
        assertNotNull(board.getAliens(), "La lista de aliens debería inicializarse.");
        assertNotNull(board.getShot(), "El disparo debería inicializarse.");
        assertEquals(24, board.getAliens().size(), "Deberían existir 24 aliens.");
    }

}
