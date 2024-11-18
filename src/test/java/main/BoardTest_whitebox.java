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

    //Test GI-1 gameInit()
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


