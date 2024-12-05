package space_invaders.sprites;

import main.Commons;

import javax.swing.ImageIcon;

public class Shot extends Sprite {

    public Shot() {
    }
    /**
     * Inicializa un nuevo objeto disparo en las coordenadas indicadas
     * @param x coordenada X de la posición del nuevo disparo
     * @param y coordenada Y de la posición del nuevo disparo
     * */
    public Shot(int x, int y) {

        initShot(x, y);
    }
    /**
     * Inicializa un nuevo objeto disparo en las coordenadas indicadas y le asigna la imagen correspondiente en la interfaz
     * El disparo sale del jugador, y le asigna las coordenadas sumando el valor H_SPACE (6) a la coordenada X,
     * y restando el valor V_SPACE (1) a la coordenada Y.
     * @param x coordenada X de la posición del nuevo disparo
     * @param y coordenada Y de la posición del nuevo disparo
     * */

    private void initShot(int x, int y) {

        var shotImg = "src/main/resources/images/shot.png";
        var ii = new ImageIcon(shotImg);
        setImage(ii.getImage());

        int H_SPACE = 6;
        int V_SPACE = 1;

        /*
        Lógica añadida para comprobar valores por debajo de límites inferiores
        y por encima de los límites superiores, teniendo en cuenta la desviación
         */
        if ( x > Commons.BOARD_WIDTH - H_SPACE){
            x = Commons.BOARD_WIDTH - H_SPACE;
        }
        else if(x < (-1 * H_SPACE) ){
            x = (-1 * H_SPACE);
        }

        if( y > Commons.BOARD_HEIGHT + V_SPACE){
            y = Commons.BOARD_HEIGHT + V_SPACE;
        }
        else if(y < H_SPACE){
            y = H_SPACE;
        }

        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }
}

