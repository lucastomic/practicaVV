package main;

import mocks.GeneratorMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import space_invaders.sprites.Alien;
import space_invaders.sprites.Player;
import space_invaders.sprites.Shot;
import space_invaders.sprites.Sprite;

import java.lang.reflect.Method;
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
        board.setPlayer(new Player());
        board.setShot(new Shot());
    }

    //Test GI-1
    @Test
    public void testGameInit_PlayerInitialized() {
        Player player = board.getPlayer();
        assertNotNull(player, "El jugador no fue inicializado correctamente");
    }
    //Test GI-2
    @Test
    public void testGameInit_ShotInitialized() {
        Shot shot = board.getShot();
        assertNotNull(shot, "El disparo no fue inicializado correctamente");
    }
    //Test GI-3
    @Test
    public void testAlienPositions() {
        board.gameInit();
        List<Alien> aliens = board.getAliens();

        int expectedX = Commons.ALIEN_INIT_X;
        int expectedY = Commons.ALIEN_INIT_Y;

        assertEquals(aliens.size(), Commons.NUMBER_OF_ALIENS_TO_DESTROY, "El número de aliens no es correcto" );
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
        board.setDirection(1);
        aliens.add(new Alien(-10, 50));  // Posición fuera de tablero por la izquierda

        board.update_aliens();

        assertEquals(1, board.getDirection(), "La dirección debería permanecer en 1 (derecha)");
    }

    // Test SR-2: Dirección derecha, alien en el margen izquierdo
    @Test
    public void testDirectionRight_AlienAtLeftMargin() {
        board.setDirection(1);
        aliens.add(new Alien(Commons.BORDER_LEFT, 50));  // Posición en el margen izquierdo

        board.update_aliens();

        assertEquals(1, board.getDirection(), "La dirección debería permanecer en 1 (derecha)");
    }

    // Test SR-3: Dirección derecha, alien en el centro del tablero
    @Test
    public void testDirectionRight_AlienAtCenter() {
        board.setDirection(1);
        aliens.add(new Alien(Commons.BOARD_WIDTH / 2, 50));  // Posición en el centro

        board.update_aliens();

        assertEquals(1, board.getDirection(), "La dirección debería permanecer en 1 (derecha)");
    }

    // Test SR-4: Dirección derecha, alien en el margen derecho
    @Test
    public void testDirectionRight_AlienAtRightMargin() {
        board.setDirection(1);
        aliens.add(new Alien(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT, 50));  // Posición en el margen derecho
        board.update_aliens();
        assertEquals(-1, board.getDirection(), "La dirección debería cambiar a -1 (izquierda)");
        assertTrue(aliens.stream().allMatch(a -> a.getY() == 50 + Commons.GO_DOWN),
                "Todos los aliens deberían descender una posición");
    }

    // Test SR-5: Dirección derecha, alien fuera del tablero por la derecha
    @Test
    public void testDirectionRight_AlienRightOutOfBounds() {
        board.setDirection(1);
        aliens.add(new Alien(Commons.BOARD_WIDTH + 10, 50));  // Posición fuera de tablero por la derecha

        board.update_aliens();

        assertEquals(-1, board.getDirection(), "La dirección debería cambiar a -1 (izquierda)");
    }

    // Test SR-6: Dirección izquierda, alien fuera del tablero por la izquierda
    @Test
    public void testDirectionLeft_AlienLeftOutOfBounds() {
        board.setDirection(-1);
        aliens.add(new Alien(-10, 50));  // Posición fuera de tablero por la izquierda

        board.update_aliens();

        assertEquals(1, board.getDirection(), "La dirección debería cambiar 1 (derecha)");
    }

    // Test SR-7: Dirección izquierda, alien en el margen izquierdo
    @Test
    public void testDirectionLeft_AlienAtLeftMargin() {
        board.setDirection(-1);
        aliens.add(new Alien(Commons.BORDER_LEFT, 50));  // Posición en el margen izquierdo

        board.update_aliens();

        assertEquals(1, board.getDirection(), "La dirección debería cambiar a 1 (derecha)");
        assertTrue(aliens.stream().allMatch(a -> a.getY() == 50 + Commons.GO_DOWN),
                "Todos los aliens deberían descender una posición");
    }

    // Test SR-8: Dirección izquierda, alien en el centro del tablero
    @Test
    public void testDirectionLeft_AlienAtCenter() {
        board.setDirection(-1);
        aliens.add(new Alien(Commons.BOARD_WIDTH / 2, 50));  // Posición en el centro

        board.update_aliens();

        assertEquals(-1, board.getDirection(), "La dirección debería permanecer en -1 (izquierda)");
    }

    // Test SR-9: Dirección izquierda, alien en el margen derecho
    @Test
    public void testDirectionLeft_AlienAtRightMargin() {
        board.setDirection(-1);
        aliens.add(new Alien(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT, 50));  // Posición en el margen derecho

        board.update_aliens();

        assertEquals(-1, board.getDirection(), "La dirección debería permanecer en -1 (izquierda)");
    }

    // Test SR-10: Dirección izquierda, alien fuera del tablero por la derecha
    @Test
    public void testDirectionLeft_AlienRightOutOfBounds() {
        board.setDirection(-1);
        aliens.add(new Alien(Commons.BOARD_WIDTH + 10, 50));  // Posición fuera de tablero por la derecha

        board.update_aliens();

        assertEquals(-1, board.getDirection(), "La dirección debería permanecer en -1 (izquierda)");
    }


    // Test SR-11: Dirección fuera de valores esperados, alien fuera del tablero por la izquierda
    @Test
    public void testInvalidDirection_AlienLeftOutOfBounds() {
        board.setDirection(999);  // Valor fuera de los valores esperados
        aliens.add(new Alien(-10, 50));  // Posición fuera de tablero por la izquierda
        board.update_aliens();

        assertThrows(IllegalArgumentException.class, board::update_aliens);

    }

    // Test SR-15: Dirección fuera de valores esperados, alien fuera del tablero por la derecha
    @Test
    public void testInvalidDirection_AlienRightOutOfBounds() {
        board.setDirection(999);  // Valor fuera de los valores esperados
        aliens.add(new Alien(Commons.BOARD_WIDTH + 10, 50));  // Posición fuera de tablero por la derecha
        board.update_aliens();

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


    // VE-1: Disparo y alien visibles, hay colisión, shot.y>=4
    @Test
    public void testImpact_VisibleShotVisibleAlien() {
        board.getShot().setX(55);
        board.getShot().setY(55);
        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
        assertEquals(board.getDeaths(),1, "Las muertes deben aumentar");
    }

    // VE-2: Disparo y alien visibles, no hay colisión, shot.y>=4
    @Test
    public void testNoImpact_VisibleShotVisibleAlien() {
        board.getShot().setX(20);
        board.getShot().setY(20);

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo, sin impacto");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(),0, "Las muertes no deberian aumentar");
        assertEquals(board.getShot().getY(), 16, "El disparo deberia subir");
    }

    // VE-3: Disparo visible, alien no visible, hay colisión (en la misma posición), shot.y>=4
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
        assertEquals(board.getDeaths(),0, "Las muertes no deberian aumentar");
        assertEquals(board.getShot().getY(), 51, "El disparo deberia subir");
    }

    // VE-4: Disparo visible, alien no visible, no hay colisión, shot.y>=4
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
        assertEquals(board.getDeaths(),0, "Las muertes no deberian aumentar");
        assertEquals(board.getShot().getY(), 16, "El disparo deberia subir");
    }

    // VE-5: Disparo visible, hay colisión, shot.y < 4
    @Test
    public void testImpact_ShotYBelowThreshold() {
        board.getShot().setX(55);
        board.getShot().setY(3); // shot.y < 4
        Alien alien = new Alien(50, 3);
        aliens.add(alien);

        board.update_shots();
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer tras el impacto");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'");
        assertEquals(board.getDeaths(), 1, "Las muertes deben aumentar");
    }

    // VE-6: Disparo visible, no hay colisión, shot.y < 4
    @Test
    public void testNoImpact_ShotYBelowThreshold() {
        board.getShot().setX(20);
        board.getShot().setY(3); // shot.y < 4
        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();
        assertEquals(board.getShot().getY(), 3, "El disparo no deberia subir");
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer cuando shot.y < 4");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(), 0, "Las muertes no deberían aumentar");
    }

    // VE-7: Alien no visible, hay colisión, shot.y < 4
    @Test
    public void testNoImpact_InvisibleAlien_ShotYBelowThreshold() {
        board.getShot().setX(50);
        board.getShot().setY(3); // shot.y < 4

        Alien alien = new Alien(3, 50);
        alien.die(); // Alien no visible
        aliens.add(alien);

        board.update_shots();
        assertEquals(board.getShot().getY(), 3, "El disparo no deberia subir");
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer cuando shot.y < 4");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(), 0, "Las muertes no deberían aumentar");
    }

    // VE-8: Alien no visible, no hay colisión, shot.y < 4
    @Test
    public void testNoImpact_NoCollisionInvisibleAlien_ShotYBelowThreshold() {
        board.getShot().setX(20);
        board.getShot().setY(3); // shot.y < 4

        Alien alien = new Alien(50  , 50);
        alien.die(); // Alien no visible
        aliens.add(alien);

        board.update_shots();
        assertEquals(board.getShot().getY(), 3, "El disparo no deberia subir");
        assertFalse(board.getShot().isVisible(), "El disparo debería desaparecer cuando shot.y < 4");
        assertFalse(alien.isDying(), "El alien no debería estar afectado");
        assertEquals(board.getDeaths(), 0, "Las muertes no deberían aumentar");
    }


    // CAE-1: Caso especial cuando el disparo no es visible
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

    // PT-1: Disparo no visible, sin modificaciones
    @Test
    public void testShotNotVisible_NoChanges() {
        board.getShot().die(); // Disparo no visible
        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.update_shots();

        assertFalse(board.getShot().isVisible(), "El disparo no debería estar visible.");
        assertTrue(alien.isVisible(), "El alien no debería ser afectado.");
    }

    // PT-2: Disparo visible, sin aliens, disparo disminuye en Y
    @Test
    public void testShotVisible_NoAliens_YDecreases() {
        board.getShot().setX(50);
        board.getShot().setY(50);

        board.update_shots();

        assertEquals(46, board.getShot().getY(), "El disparo debería moverse hacia arriba.");
        assertTrue(board.getShot().isVisible(), "El disparo debería seguir visible.");
    }

    // PT-3: Disparo impacta en un alien, alien "muriendo", disparo inactivo, deaths disminuye
    @Test
    public void testShotHitsAlien() {
        board.getShot().setX(50);
        board.getShot().setY(50);

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.setDeaths(1);

        board.update_shots();

        assertFalse(board.getShot().isVisible(), "El disparo debería volverse inactivo tras el impacto.");
        assertTrue(alien.isDying(), "El alien debería estar en estado 'muriendo'.");
        assertEquals(0, board.getDeaths(), "El contador de muertes debería disminuir.");
    }

    // PT-4: Disparo visible, alien visible pero no hay impacto. El disparo esta por debajo
    @Test
    public void testShotNoImpact_AlienVisible() {
        board.getShot().setX(50);
        board.getShot().setY(50 + Commons.ALIEN_HEIGHT + 1); // No está en la zona del alien

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.setDeaths(1);

        board.update_shots();

        assertTrue(board.getShot().isVisible(), "El disparo debería seguir activo.");
        assertFalse(alien.isDying(), "El alien no debería ser afectado.");
        assertEquals(1, board.getDeaths(), "El contador de muertes no debería cambiar.");
    }

    // PT-5: Disparo visible, alien visible, fuera del rango Y. El disparo esta por arriba
    @Test
    public void testShotNoImpact_OutOfRangeY() {
        board.getShot().setX(50); // En rango X
        board.getShot().setY(0); // Fuera del rango Y del alien

        Alien alien = new Alien(50, 50);
        aliens.add(alien);

        board.setDeaths(1);

        board.update_shots();

        assertFalse(board.getShot().isVisible(), "El disparo debería volverse inactivo.");
        assertFalse(alien.isDying(), "El alien no debería estar en estado 'muriendo'.");
        assertEquals(1, board.getDeaths(), "El contador de muertes no debería cambiar.");
    }

    // PT-6: Disparo visible, alien visible, fuera del rango X por la derecha
    @Test
    public void testShotNoImpact_OutOfRangeX() {
        board.getShot().setX(50 + Commons.ALIEN_WIDTH + 1); // Fuera del rango X
        board.getShot().setY(0); // En rango Y

        Alien alien = new Alien(50, 0);
        aliens.add(alien);

        board.setDeaths(1);

        board.update_shots();

        assertFalse(board.getShot().isVisible(), "El disparo debería volverse inactivo.");
        assertFalse(alien.isDying(), "El alien no debería estar en estado 'muriendo'.");
        assertEquals(1, board.getDeaths(), "El contador de muertes no debería cambiar.");
    }

    // PT-7: Disparo visible, alien visible, fuera del rango X por la izquierda
    @Test
    public void testShotNoImpact_OutOfRangeX2() {
        board.getShot().setX(30); // Fuera del rango X
        board.getShot().setY(0);

        Alien alien = new Alien(50, 0);
        aliens.add(alien);

        board.setDeaths(1);

        board.update_shots();

        assertFalse(board.getShot().isVisible(), "El disparo debería volverse inactivo.");
        assertFalse(alien.isDying(), "El alien no debería estar en estado 'muriendo'.");
        assertEquals(1, board.getDeaths(), "El contador de muertes no debería cambiar.");
    }

    // PT-8: Disparo visible, alien no visible, sin impacto
    @Test
    public void testShotNoImpact_AlienNotVisible() {
        board.getShot().setX(50);
        board.getShot().setY(0);

        Alien alien = new Alien(50, 0);
        alien.die(); // Alien no visible
        aliens.add(alien);

        board.setDeaths(1);

        board.update_shots();

        assertFalse(board.getShot().isVisible(), "El disparo debería volverse inactivo.");
        assertFalse(alien.isDying(), "El alien no debería ser afectado.");
        assertEquals(1, board.getDeaths(), "El contador de muertes no debería cambiar.");
    }

    // TD-1: Bomba activa, colisión con jugador, jugador muere y bomba destruida
    @Test
    public void testBombHitsPlayerAndDestroysIt() {
        Alien alien = new Alien(50, 50);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(false); // Bomba activa
        bomb.setX(100);
        bomb.setY(100);
        aliens.add(alien);

        Player player = board.getPlayer();
        player.setX(100);
        player.setY(100);

        board.update_bomb();

        assertTrue(player.isDying(), "El jugador debería estar en estado 'muriendo'.");
        assertTrue(bomb.isDestroyed(), "La bomba debería estar destruida tras impactar al jugador.");
    }

    // TD-2: Bomba activa, sin colisión con jugador, toca el suelo y se destruye
    @Test
    public void testBombReachesGroundAndIsDestroyed() {
        Alien alien = new Alien(50, 50);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(false); // Bomba activa
        bomb.setY(Commons.GROUND - Commons.BOMB_HEIGHT - 1); // Posición justo antes del suelo
        Player player = board.getPlayer();

        aliens.add(alien);

        board.update_bomb();

        assertTrue(bomb.isDestroyed(), "La bomba debería estar destruida tras alcanzar el suelo.");
        assertFalse(player.isDying(), "El jugador no debería estar en estado 'muriendo'.");
    }

    // TD-3: Bomba activa, sin colisión con jugador ni suelo, baja una posición
    @Test
    public void testBombMovesDownWithoutCollisionOrGroundHit() {
        Alien alien = new Alien(50, 50);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(false);
        bomb.setY(100);
        aliens.add(alien);
        Player player = board.getPlayer();

        board.update_bomb();

        assertFalse(player.isDying(), "El jugador no debería estar en estado 'muriendo'.");
        assertEquals(101, bomb.getY(), "La bomba debería moverse hacia abajo.");
        assertFalse(bomb.isDestroyed(), "La bomba no debería estar destruida.");
    }

    // TD-4: Alien visible, bomba no activa, bomba generada en posición del alien, hay colisión
    @Test
    public void testBombGeneratedHitsPlayer() {
        Alien alien = new Alien(50, 50);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true); // Bomba inicialmente destruida
        aliens.add(alien);

        // Controla aleatoriedad para garantizar generación de bomba
        board.setGenerator(new GeneratorMock(Commons.CHANCE));
        Player player = board.getPlayer();
        player.setX(50); // Mismo X que la bomba
        player.setY(50); // Mismo Y que la bomba

        board.update_bomb();

        assertTrue(player.isDying(), "El jugador debería estar en estado 'muriendo'.");
        assertTrue(bomb.isDestroyed(), "La bomba debería estar destruida tras impactar al jugador.");
    }

    // TD-5: Alien visible, bomba no activa, bomba generada en posición del alien, no hay colisión pero toca el suelo
    @Test
    public void testBombGeneratedHitsGround() {
        Alien alien = new Alien(50, Commons.GROUND - Commons.BOMB_HEIGHT - 1); // Posición Y justo encima del suelo
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true); // Bomba inicialmente destruida
        aliens.add(alien);

        // Controla aleatoriedad para garantizar que es Commons.CHANCE
        board.setGenerator(new GeneratorMock(Commons.CHANCE));
        Player player = board.getPlayer();

        board.update_bomb();

        assertTrue(bomb.isDestroyed(), "La bomba debería estar destruida tras alcanzar el suelo.");
        assertFalse(player.isDying(), "El jugador no debería estar en estado 'muriendo'.");
    }

    // TD-6: Alien visible, bomba no activa, bomba generada en posición del alien, no hay colisión ni toca el suelo
    @Test
    public void testBombGeneratedMovesDown() {
        Alien alien = new Alien(50, 50);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true); // Bomba inicialmente destruida
        aliens.add(alien);
        Player player = board.getPlayer();

        // Controla aleatoriedad para garantizar que es Commons.CHANCE
        board.setGenerator(new GeneratorMock(Commons.CHANCE));
        board.update_bomb();

        assertFalse(player.isDying(), "El jugador no debería estar en estado 'muriendo'.");
        assertEquals(51, bomb.getY(), "La bomba debería moverse hacia abajo.");
        assertFalse(bomb.isDestroyed(), "La bomba no debería estar destruida.");
    }

    // TD-7: Alien visible, bomba no activa, no se genera. Hay colisión
    @Test
    public void testNoBombGeneratedButHitsPlayer() {
        Alien alien = new Alien(50, 50);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true); // Bomba inicialmente destruida
        aliens.add(alien);

        // Controla aleatoriedad para garantizar la NO generación de bomba
        board.setGenerator(new GeneratorMock(Commons.CHANCE - 1));
        Player player = board.getPlayer();
        player.setX(50); // Mismo X que la bomba
        player.setY(50); // Mismo Y que la bomba

        board.update_bomb();

        assertTrue(bomb.isDestroyed(), "La bomba debería seguir destruida.");
        assertFalse(player.isDying(), "El jugador no debería estar en estado 'muriendo'.");
    }

    // TD-8: Alien visible, bomba no activa, no se genera. No hay colisión pero toca el suelo
    @Test
    public void testNoBombGeneratedButHitsGround() {
        Alien alien = new Alien(50, Commons.GROUND - Commons.BOMB_HEIGHT - 1); // Posición Y justo encima del suelo
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true); // Bomba inicialmente destruida
        aliens.add(alien);

        // Controla aleatoriedad para garantizar que no es Commons.CHANCE
        board.setGenerator(new GeneratorMock(Commons.CHANCE - 1));
        Player player = board.getPlayer();

        board.update_bomb();

        assertTrue(bomb.isDestroyed(), "La bomba debería estar destruida tras alcanzar el suelo.");
        assertFalse(player.isDying(), "El jugador no debería estar en estado 'muriendo'.");
    }

    // TD-9: Alien visible, bomba no activa, no se genera. No hay colisión ni toca el suelo
    @Test
    public void testNoBombGeneratedNoCollisionOrGroundHit() {
        Alien alien = new Alien(50, 50);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true); // Bomba inicialmente destruida
        aliens.add(alien);

        // Controla aleatoriedad para garantizar que no es Commons.CHANCE
        board.setGenerator(new GeneratorMock(Commons.CHANCE - 1));
        Player player = board.getPlayer();

        board.update_bomb();

        assertTrue(bomb.isDestroyed(), "La bomba debería seguir destruida.");
        assertFalse(player.isDying(), "El jugador no debería estar en estado 'muriendo'.");
    }

    /*
    Pruebas update_aliens
     */

    @Test
    void testAliensOnRightAndWin() {
        // Camino: 1-2-3-4-5-6-5-7-1-11-12-13-14-15-16-17-18-12
        Alien alien = new Alien(Commons.BOARD_WIDTH - Commons.BORDER_RIGHT, Commons.GROUND - Commons.ALIEN_HEIGHT); // En el borde derecho
        //alien.setVisible(true);
        aliens.add(alien);

        board.setDirection(1);
        board.update_aliens();

        assertFalse(board.isInGame());

        assertEquals("Invasion!", board.getMessage());
    }

    @Test
    void testNoAliens() {
        // Camino: 1-11-12
        board.update_aliens();

        assertTrue(board.isInGame()); // El juego sigue porque no hay aliens
    }

    @Test
    void testAliensOnLeftAndWin() {
        // Camino: 1-2-3-7-8-9-10-9-1-11-12-13-14-15-16-17-18-12-fin
        Alien alien = new Alien(Commons.BORDER_LEFT, Commons.GROUND - Commons.ALIEN_HEIGHT + 1); // En el borde izquierdo
        //alien.setVisible(true);
        aliens.add(alien);

        board.setDirection(-1);
        board.update_aliens();

        assertFalse(board.isInGame());
        assertEquals("Invasion!", board.getMessage());
    }

    @Test
    void testAliensNotOnEdgesAndWin() {
        // Camino: 1-2-3-7-1-11-12-13-14-15-16-17-18-12-fin
        Alien alien = new Alien(50, Commons.GROUND - Commons.ALIEN_HEIGHT + 1); // No en los bordes
        //alien.setVisible(true);
        aliens.add(alien);

        board.setDirection(-1);
        board.update_aliens();

        assertFalse(board.isInGame());
        assertEquals("Invasion!", board.getMessage());
    }

    @Test
    void testAliensNotWinning() {
        // Camino: 1-2-3-7-1-11-12-13-14-15-16-18-12-fin
        Alien alien = new Alien(50, 50); // No alcanzan el borde inferior
        //alien.setVisible(true);
        aliens.add(alien);

        board.setDirection(0);
        board.update_aliens();

        assertTrue(board.isInGame());
    }

    @Test
    void testAliensNotVisible() {
        // Camino: 1-2-3-7-1-11-12-13-14-12-fin
        Alien alien = new Alien(50, 50); // No visibles

        // NO PUEDO TOCAR LA FUNCIÓN SI ES PROTECTED?
        // alien.setVisible(false);
        aliens.add(alien);

        board.setDirection(0);
        board.update_aliens();

        assertTrue(board.isInGame());
    }


    @Test
    public void testUpdateBomb_PT1() throws Exception {
        // Caso: shot != Commons.CHANCE y la bomba ya está destruida
        GeneratorMock mockGenerator = new GeneratorMock(14);  // shot != Commons.CHANCE (usamos 14 para simular que no se genera la bomba)
        board.setGenerator(mockGenerator); // Usamos el mock en lugar de un generador real

        Alien alien = new Alien(100, 100); // Alien en posición arbitraria
        aliens.add(alien);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true); // Bomba destruida

        // Usamos reflexión para acceder al método setVisible
        Method setVisibleMethod = Sprite.class.getDeclaredMethod("setVisible", boolean.class);
        setVisibleMethod.setAccessible(true);  // Permite acceder al método `protected`
        setVisibleMethod.invoke(alien, true);  // Establece el alien como visible

        board.update_bomb();

        // Verifica que la bomba no se ha actualizado
        assertTrue(bomb.isDestroyed(), "La bomba debería seguir destruida.");
        assertEquals(0, bomb.getX(), "La posición X de la bomba no debería haber cambiado.");
        assertEquals(0, bomb.getY(), "La posición Y de la bomba no debería haber cambiado.");
    }


    @Test
    public void testUpdateBomb_PT2() throws Exception {
        // Caso: shot == Commons.CHANCE, alien visible, bomba destruida
        GeneratorMock mockGenerator = new GeneratorMock(Commons.CHANCE);  // shot == Commons.CHANCE
        board.setGenerator(mockGenerator);  // Usamos el mock

        Alien alien = new Alien(50, 100);  // Alien en posición arbitraria
        aliens.add(alien);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true);  // Bomba destruida

        // Usamos reflexión para acceder al método setVisible
        Method setVisibleMethod = Sprite.class.getDeclaredMethod("setVisible", boolean.class);
        setVisibleMethod.setAccessible(true);  // Permite acceder al método `protected`
        setVisibleMethod.invoke(alien, true);  // Establece el alien como visible

        board.update_bomb();

        // Verifica que la bomba se ha colocado sobre el alien
        assertFalse(bomb.isDestroyed(), "La bomba no debería estar destruida.");
        assertEquals(50, bomb.getX(), "La posición X de la bomba debería ser la del alien.");
        assertEquals(100, bomb.getY(), "La posición Y de la bomba debería ser la del alien.");
    }


    @Test
    public void testUpdateBomb_PT3() throws Exception {
        // Caso: shot != Commons.CHANCE, alien no visible, bomba destruida
        GeneratorMock mockGenerator = new GeneratorMock(14);  // shot != Commons.CHANCE
        board.setGenerator(mockGenerator); // Usamos el mock

        Alien alien = new Alien(100, 100);  // Alien en posición arbitraria
        aliens.add(alien);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setDestroyed(true);  // Bomba destruida

        // Usamos reflexión para acceder al método setVisible
        Method setVisibleMethod = Sprite.class.getDeclaredMethod("setVisible", boolean.class);
        setVisibleMethod.setAccessible(true);  // Permite acceder al método `protected`
        setVisibleMethod.invoke(alien, false);  // Establece el alien como no visible

        board.update_bomb();

        // Verifica que la bomba no se ha actualizado
        assertTrue(bomb.isDestroyed(), "La bomba debería seguir destruida.");
        assertEquals(0, bomb.getX(), "La posición X de la bomba no debería haber cambiado.");
        assertEquals(0, bomb.getY(), "La posición Y de la bomba no debería haber cambiado.");
    }




    @Test
    public void testUpdateBomb_PT4() throws Exception {
        // Caso: shot == Commons.CHANCE, bomba activa, colisión con el jugador
        GeneratorMock mockGenerator = new GeneratorMock(Commons.CHANCE);  // shot == Commons.CHANCE
        board.setGenerator(mockGenerator); // Usamos el mock

        Alien alien = new Alien(100, 200);  // Alien en posición arbitraria
        aliens.add(alien);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setX(100);  // Bomba en la posición del alien
        bomb.setY(200);
        bomb.setDestroyed(false); // Bomba activa

        Player player = board.getPlayer();
        player.setX(100); // Mismo X que la bomba
        player.setY(200); // Mismo Y que la bomba

        // Usamos reflexión para acceder al método setVisible de Sprite para el jugador
        Method setVisibleMethod = Sprite.class.getDeclaredMethod("setVisible", boolean.class);
        setVisibleMethod.setAccessible(true);  // Permite acceder al método `protected`
        setVisibleMethod.invoke(player, true);  // Establece el jugador como visible

        board.update_bomb();

        // Verifica que la bomba se destruye al colisionar con el jugador
        assertTrue(bomb.isDestroyed(), "La bomba debería estar destruida.");
        assertTrue(player.isDying(), "El jugador debería estar en estado 'muriendo'.");
    }



    @Test
    public void testUpdateBomb_PT5() throws Exception {
        // Caso: Bomba tocando el suelo
        GeneratorMock mockGenerator = new GeneratorMock(Commons.CHANCE);  // shot == Commons.CHANCE
        board.setGenerator(mockGenerator); // Usamos el mock

        Alien alien = new Alien(100, 100);  // Alien en posición arbitraria
        aliens.add(alien);
        Alien.Bomb bomb = alien.getBomb();
        bomb.setY(Commons.GROUND - Commons.BOMB_HEIGHT - 1);  // Posición justo antes del suelo
        bomb.setDestroyed(false);  // Bomba activa

        board.update_bomb();

        // Verifica que la bomba se destruye al llegar al suelo
        assertTrue(bomb.isDestroyed(), "La bomba debería estar destruida.");
    }


}


