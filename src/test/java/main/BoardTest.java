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



    // B-1: Fuera del alien por la izquierda, alineado verticalmente
    @Test
    public void testNoImpact_LeftOfAlien() {
        board.getShot().setX(49);
        board.getShot().setY(50 + Commons.ALIEN_HEIGHT/2); // AlienY + (ALIEN_HEIGHT / 2)

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
    }

    // B-2: Justo en el borde izquierdo del alien
    @Test
    public void testImpact_LeftEdgeOfAlien() {
        board.getShot().setX(50);
        board.getShot().setY(50 + Commons.ALIEN_HEIGHT/2); // AlienY + (ALIEN_HEIGHT / 2)

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
    }

    // B-3: Dentro del alien, ligeramente a la derecha del borde izquierdo
    @Test
    public void testImpact_InsideLeftOfAlien() {
        board.getShot().setX(51);
        board.getShot().setY(50 + Commons.ALIEN_HEIGHT/2); // AlienY + (ALIEN_HEIGHT / 2)

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
    }

    // B-4: Fuera del alien por arriba, alineado horizontalmente
    @Test
    public void testNoImpact_AboveAlien() {
        board.getShot().setX(50 + Commons.ALIEN_WIDTH/2); // AlienX + (ALIEN_WIDTH / 2)
        board.getShot().setY(49);

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
    }

    // B-5: Justo en el borde superior del alien
    @Test
    public void testImpact_TopEdgeOfAlien() {
        board.getShot().setX(50 + Commons.ALIEN_WIDTH/2); // AlienX + (ALIEN_WIDTH / 2)
        board.getShot().setY(50);

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
    }

    // B-6: Dentro del alien, ligeramente abajo del borde superior
    @Test
    public void testImpact_InsideTopOfAlien() {
        board.getShot().setX(50 + Commons.ALIEN_WIDTH/2); // AlienX + (ALIEN_WIDTH / 2)
        board.getShot().setY(51);

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
    }

    // B-7: Fuera del alien por la derecha, alineado verticalmente
    @Test
    public void testNoImpact_RightOfAlien() {
        board.getShot().setX(50+ Commons.ALIEN_WIDTH + 1); // AlienX + ALIEN_WIDTH + 1
        board.getShot().setY(50 + Commons.ALIEN_HEIGHT/2); // AlienY + (ALIEN_HEIGHT / 2)

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
    }

    // B-8: Justo en el borde derecho del alien
    @Test
    public void testImpact_RightEdgeOfAlien() {
        board.getShot().setX(50+ Commons.ALIEN_WIDTH); // AlienX + ALIEN_WIDTH
        board.getShot().setY(50 + Commons.ALIEN_HEIGHT/2); // AlienY + (ALIEN_HEIGHT / 2)

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
    }

    // B-9: Dentro del alien, ligeramente a la izquierda del borde derecho
    @Test
    public void testImpact_InsideRightOfAlien() {
        board.getShot().setX(50+ Commons.ALIEN_WIDTH -1);// AlienX + ALIEN_WIDTH - 1
        board.getShot().setY(50 + Commons.ALIEN_HEIGHT/2); // AlienY + (ALIEN_HEIGHT / 2)

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
    }

    // B-10: Fuera del alien por abajo, alineado horizontalmente
    @Test
    public void testNoImpact_BelowAlien() {
        board.getShot().setX(50+ Commons.ALIEN_WIDTH/2); // AlienX + (ALIEN_WIDTH / 2)
        board.getShot().setY(50 + Commons.ALIEN_HEIGHT+1); // AlienY + ALIEN_HEIGHT + 1

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
    }

    // B-11: Justo en el borde inferior del alien
    @Test
    public void testImpact_BottomEdgeOfAlien() {
        board.getShot().setX(50+ Commons.ALIEN_WIDTH/2); // AlienX + (ALIEN_WIDTH / 2)
        board.getShot().setY(50 + Commons.ALIEN_HEIGHT); // AlienY + ALIEN_HEIGHT

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
    }

    // B-12: Dentro del alien, ligeramente arriba del borde inferior
    @Test
    public void testImpact_InsideBottomOfAlien() {
        board.getShot().setX(50+ Commons.ALIEN_WIDTH/2); // AlienX + (ALIEN_WIDTH / 2)
        board.getShot().setY(50 + Commons.ALIEN_HEIGHT-1); // AlienY + ALIEN_HEIGHT - 1

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
    }
    // B-13: Disparo en el centro del alien
    @Test
    public void testImpact_CenterOfAlien() {
        board.getShot().setX(50+ Commons.ALIEN_WIDTH/2); // AlienX + (ALIEN_WIDTH / 2)
        board.getShot().setY(50+ Commons.ALIEN_HEIGHT/2); // AlienY + (ALIEN_HEIGHT / 2)

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
    }


    // VE-1: Disparo y alien visibles, hay colisión
    @Test
    public void testImpact_VisibleShotVisibleAlien() {
        board.getShot().setX(55);
        board.getShot().setY(55);

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
    }

    // VE-2: Disparo y alien visibles, no hay colisión
    @Test
    public void testNoImpact_VisibleShotVisibleAlien() {
        board.getShot().setX(20);
        board.getShot().setY(20);

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
    }

    // VE-3: Disparo visible, alien no visible, hay colisión (en la misma posición)
    @Test
    public void testImpact_VisibleShotInvisibleAlien() {
        board.getShot().setX(55);
        board.getShot().setY(55);

        Alien alien = new Alien(50, 50);
        alien.die();
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
    }

    // VE-4: Disparo visible, alien no visible, no hay colisión
    @Test
    public void testNoImpact_VisibleShotInvisibleAlienNoCollision() {
        board.getShot().setX(20);
        board.getShot().setY(20);

        Alien alien = new Alien(50, 50);
        alien.die();
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
    }
    // VE-5: Disparo no visible, alien no visible, hay colisión
    @Test
    public void testImpact_InvisibleShotVisibleAlien() {
        board.getShot().setX(55);
        board.getShot().setY(55);
        board.getShot().die();

        Alien alien = new Alien(50, 50);
        alien.die();
        aliens.add(alien);
        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería seguir inactivo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
    }

    // CE-1: `deaths` igual a `Commons.CHANCE`, el juego debería terminar
    @Test
    public void testGameWonWhenDeathsEqualsChance() {
        board.setDeaths(Commons.CHANCE); // Configura deaths igual a CHANCE

        board.update();

        assertFalse(board.isInGame(), "El juego debería terminar cuando deaths es igual a Commons.CHANCE.");
    }

    // CE-2: `deaths` menor que `Commons.CHANCE`, el juego debería continuar
    @Test
    public void testGameContinuesWhenDeathsLessThanChance() {
        board.setDeaths(Commons.CHANCE - 1); // Configura deaths menor que CHANCE

        board.update();

        assertTrue(board.isInGame(), "El juego debería continuar cuando deaths es menor que Commons.CHANCE.");
    }
}
