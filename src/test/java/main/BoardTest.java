package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;

import java.util.ArrayList;
import java.util.List;

public class BoardTest {

    private Board board;
    private List<Alien> aliens;

    @BeforeEach
    public void setUp() {
        board = new Board();
        aliens = new ArrayList<>();
        board.setAliens(aliens); // Asignamos la lista de aliens a la board para los tests de update_aliens
        board.setShot(new Shot());
    }

    // Tests de inicialización (gameInit)
    @Test
    public void testGameInit_PlayerInitialized() {
        Player player = board.getPlayer();
        assertNotNull(player, "El jugador no fue inicializado correctamente");
    }

    @Test
    public void testGameInit_ShotInitialized() {
        Shot shot = board.getShot();
        assertNotNull(shot, "El disparo no fue inicializado correctamente");
    }

    @Test
    public void testGameInit_AliensInitialized() {
        List<Alien> aliens = board.getAliens();
        assertNotNull(aliens, "La lista de aliens no fue inicializada correctamente");
        assertEquals(24, aliens.size(), "La cantidad de aliens inicializados no es correcta");
    }

    @Test
    public void testGameInit_AlienPositions() {
        List<Alien> aliens = board.getAliens();
        int expectedX = Commons.ALIEN_INIT_X;
        int expectedY = Commons.ALIEN_INIT_Y;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = aliens.get(i * 6 + j);
                assertEquals(expectedX + 18 * j, alien.getX(),
                        "La posición X del alien no es la esperada en la fila " + i + ", columna " + j);
                assertEquals(expectedY + 18 * i, alien.getY(),
                        "La posición Y del alien no es la esperada en la fila " + i + ", columna " + j);
            }
        }
    }

    // Test SR-1: Dirección derecha, alien fuera del tablero por la izquierda
    @Test
    public void testDirectionRight_AlienLeftOutOfBounds() {
        board.setDirection(-1);
        aliens.add(new Alien(-10, 50));  // Posición fuera de tablero por la izquierda

        board.update_aliens();

        assertEquals(-1, board.getDirection(), "La dirección debería permanecer en -1 (derecha)");
    }

    // Test SR-2: Dirección derecha, alien en el margen izquierdo
    @Test
    public void testDirectionRight_AlienAtLeftMargin() {
        board.setDirection(-1);
        aliens.add(new Alien(Commons.BORDER_LEFT, 50));  // Posición en el margen izquierdo

        board.update_aliens();

        assertEquals(-1, board.getDirection(), "La dirección debería permanecer en -1 (derecha)");
    }

    // Test SR-3: Dirección derecha, alien en el centro del tablero
    @Test
    public void testDirectionRight_AlienAtCenter() {
        board.setDirection(-1);
        aliens.add(new Alien(Commons.BOARD_WIDTH / 2, 50));  // Posición en el centro

        board.update_aliens();

        assertEquals(-1, board.getDirection(), "La dirección debería permanecer en -1 (derecha)");
    }

    // Test SR-4: Dirección derecha, alien en el margen derecho
    @Test
    public void testDirectionRight_AlienAtRightMargin() {
        board.setDirection(-1);
        aliens.add(new Alien(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT, 50));  // Posición en el margen derecho
        board.update_aliens();
        assertEquals(1, board.getDirection(), "La dirección debería cambiar a 1 (izquierda)");
        assertTrue(aliens.stream().allMatch(a -> a.getY() == 50 + Commons.GO_DOWN),
                "Todos los aliens deberían descender una posición");
    }

    // Test SR-5: Dirección derecha, alien fuera del tablero por la derecha
    @Test
    public void testDirectionRight_AlienRightOutOfBounds() {
        board.setDirection(-1);
        aliens.add(new Alien(Commons.BOARD_WIDTH + 10, 50));  // Posición fuera de tablero por la derecha

        board.update_aliens();

        assertEquals(1, board.getDirection(), "La dirección debería cambiar a 1 (izquierda)");
    }

    // Test SR-6: Dirección izquierda, alien fuera del tablero por la izquierda
    @Test
    public void testDirectionLeft_AlienLeftOutOfBounds() {
        board.setDirection(1);
        aliens.add(new Alien(-10, 50));  // Posición fuera de tablero por la izquierda

        board.update_aliens();

        assertEquals(-1, board.getDirection(), "La dirección debería cambiar a -1 (derecha)");
    }

    // Test SR-7: Dirección izquierda, alien en el margen izquierdo
    @Test
    public void testDirectionLeft_AlienAtLeftMargin() {
        board.setDirection(1);
        aliens.add(new Alien(Commons.BORDER_LEFT, 50));  // Posición en el margen izquierdo

        board.update_aliens();

        assertEquals(-1, board.getDirection(), "La dirección debería cambiar a -1 (derecha)");
        assertTrue(aliens.stream().allMatch(a -> a.getY() == 50 + Commons.GO_DOWN),
                "Todos los aliens deberían descender una posición");
    }

    // Test SR-8: Dirección izquierda, alien en el centro del tablero
    @Test
    public void testDirectionLeft_AlienAtCenter() {
        board.setDirection(1);
        aliens.add(new Alien(Commons.BOARD_WIDTH / 2, 50));  // Posición en el centro

        board.update_aliens();

        assertEquals(1, board.getDirection(), "La dirección debería permanecer en 1 (izquierda)");
    }

    // Test SR-9: Dirección izquierda, alien en el margen derecho
    @Test
    public void testDirectionLeft_AlienAtRightMargin() {
        board.setDirection(1);
        aliens.add(new Alien(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT, 50));  // Posición en el margen derecho

        board.update_aliens();

        assertEquals(1, board.getDirection(), "La dirección debería permanecer en 1 (izquierda)");
    }

    // Test SR-10: Dirección izquierda, alien fuera del tablero por la derecha
    @Test
    public void testDirectionLeft_AlienRightOutOfBounds() {
        board.setDirection(1);
        aliens.add(new Alien(Commons.BOARD_WIDTH + 10, 50));  // Posición fuera de tablero por la derecha
        assertThrows(IllegalArgumentException.class, board::update_aliens);
    }
    // Test SR-11: Dirección fuera de valores esperados, alien fuera del tablero por la izquierda
    @Test
    public void testInvalidDirection_AlienLeftOutOfBounds() {
        board.setDirection(999);  // Valor fuera de los valores esperados
        aliens.add(new Alien(-10, 50));  // Posición fuera de tablero por la izquierda
        assertThrows(IllegalArgumentException.class, board::update_aliens);

    }

    // Test SR-15: Dirección fuera de valores esperados, alien fuera del tablero por la derecha
    @Test
    public void testInvalidDirection_AlienRightOutOfBounds() {
        board.setDirection(999);  // Valor fuera de los valores esperados
        aliens.add(new Alien(Commons.BOARD_WIDTH + 10, 50));  // Posición fuera de tablero por la derecha
        assertThrows(IllegalArgumentException.class, board::update_aliens);
    }

    // Prueba de límite: Alien justo antes del límite inferior
    @Test
    public void testAlienJustAboveBottomBorder() {
        board.setDirection(1); // Dirección arbitraria
        aliens.add(new Alien(50, Commons.GROUND - Commons.ALIEN_HEIGHT - 1));  // Alien justo por encima del límite inferior

        board.update_aliens();

        assertTrue(board.isInGame(), "El juego debería continuar ya que el alien no ha tocado el límite inferior");
        assertNull(board.getMessage(), "No debería mostrarse ningún mensaje de 'Invasion!'");
    }

    // Prueba de límite: Alien tocando el límite inferior
    @Test
    public void testAlienAtBottomBorder() {
        board.setDirection(1); // Dirección arbitraria
        aliens.add(new Alien(50, Commons.GROUND - Commons.ALIEN_HEIGHT));  // Alien en el límite inferior

        board.update_aliens();

        assertFalse(board.isInGame(), "El juego debería terminar ya que el alien ha alcanzado el límite inferior");
        assertEquals("Invasion!", board.getMessage(), "El mensaje debería ser 'Invasion!'");
    }

    // Prueba de límite: Alien justo por debajo del límite inferior
    @Test
    public void testAlienBelowBottomBorder() {
        board.setDirection(1); // Dirección arbitraria
        aliens.add(new Alien(50, Commons.GROUND - Commons.ALIEN_HEIGHT + 1));  // Alien por debajo del límite inferior

        board.update_aliens();

        assertFalse(board.isInGame(), "El juego debería terminar ya que el alien ha sobrepasado el límite inferior");
        assertEquals("Invasion!", board.getMessage(), "El mensaje debería ser 'Invasion!'");
    }


    // CE-1: shotX < alienX & shotY < alienY
    @Test
    public void testNoImpact_ShotAboveLeft() {
        board.getShot().setX(20);
        board.getShot().setY(20);

        Alien alien = new Alien(50, 50); // Alien está abajo a la derecha del disparo
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(),0, "Las muertes deberian seguir siendo 0");
    }

    // CE-2: alienX < shotX < alienX + ALIEN_WIDTH & shotY < alienY
    @Test
    public void testNoImpact_ShotAboveAlien() {
        board.getShot().setX(55);
        board.getShot().setY(20);

        Alien alien = new Alien(50, 50); // Alien debajo del disparo
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(),0, "Las muertes deberian seguir siendo 0");

    }

    // CE-3: alienX + ALIEN_WIDTH < shotX & shotY < alienY
    @Test
    public void testNoImpact_ShotAboveRightAlien() {
        board.getShot().setX(90);
        board.getShot().setY(20);

        Alien alien = new Alien(50, 50); // Alien está a la izquierda y debajo del disparo
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(),0, "Las muertes deberian seguir siendo 0");

    }

    // CE-4: shotX < alienX & alienY < shotY < alienY + ALIEN_HEIGHT
    @Test
    public void testNoImpact_ShotLeftAlien() {
        board.getShot().setX(20);
        board.getShot().setY(55);

        Alien alien = new Alien(50, 50); // Alien está a la derecha del disparo
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(),0, "Las muertes deberian seguir siendo 0");

    }


    // CE-5: alienX < shotX < alienX + ALIEN_WIDTH & alienY < shotY < alienY + ALIEN_HEIGHT
    @Test
    public void testImpact_ShotInsideAlien() {
        board.getShot().setX(55);
        board.getShot().setY(55);

        Alien alien = new Alien(50, 50); // Alien está en el área del disparo
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
        assertEquals(board.getDeaths(),1, "Las muertes deberian subir a 1");

    }
    // CE-6: alienX + ALIEN_WIDTH < shotX & alienY < shotY < alienY + ALIEN_HEIGHT
    @Test
    public void testNoImpact_ShotRightAlien() {
        board.getShot().setX(90);
        board.getShot().setY(55);

        Alien alien = new Alien(50, 50); // Alien está a la izquierda del disparo en el área vertical
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(),0, "Las muertes deberian seguir siendo 0");

    }

    // CE-7: shotX < alienX & alienY + ALIEN_HEIGHT < shotY
    @Test
    public void testNoImpact_ShotBelowLeftAlien() {
        board.getShot().setX(20);
        board.getShot().setY(90);

        Alien alien = new Alien(50, 50); // Alien está arriba y a la derecha del disparo
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(),0, "Las muertes deberian seguir siendo 0");

    }

    // CE-8: alienX < shotX < alienX + ALIEN_WIDTH & alienY + ALIEN_HEIGHT < shotY
    @Test
    public void testNoImpact_ShotBelowAlien() {
        board.getShot().setX(55);
        board.getShot().setY(90);

        Alien alien = new Alien(50, 50); // Alien está arriba del disparo en el área horizontal
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(),0, "Las muertes deberian seguir siendo 0");

    }
    // CE-9: alienX + ALIEN_WIDTH < shotX & alienY + ALIEN_HEIGHT < shotY
    @Test
    public void testNoImpact_ShotBelowRight() {
        board.getShot().setX(90);
        board.getShot().setY(90);

        Alien alien = new Alien(50, 50); // Alien está arriba a la izquierda del disparo
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(),0, "Las muertes deberian seguir siendo 0");

    }
}
