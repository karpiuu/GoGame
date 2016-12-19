package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;
import GameEngine.Stone;

import javax.swing.*;
import java.awt.*;

public class GameViewPanel extends JPanel {

    private int width;
    private int height;

    private int offX;
    private int offY;
    private GameEngine gameEngine;
    private int size;
    private int halfSize;
    private int rowNumber;
    private JLabel turn;

    private JLabel buttonList[][];
    private SocketClient client;

    public GameViewPanel(SocketClient newClient, int w, int h, GameEngine gameEngine, JLabel turn, JLabel blackP, JLabel whiteP) {


        this.gameEngine = gameEngine;
        this.turn = turn;

        client = newClient;

        width = w;
        height = h;

        if( width > height ) {
            offX = (width - height) / 2;
            offY = 0;
        }
        else if(height > width) {
            offY = (height - width) / 2;
            offX = 0;
        }

        offX += 20;
        offY += 20;

        setLayout( null );

        rowNumber = 19;

        setButtons(rowNumber, blackP, whiteP);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        drawBaseGamePlane(g2d, rowNumber);
        drawShapes(g2d);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    private void drawBaseGamePlane(Graphics2D g2d, int rowNumber) {

        g2d.setColor( new Color( 255, 225, 190 ) );

        g2d.fillRect( 0, 0, width, height );

        halfSize = size/2;

        g2d.setColor(Color.BLACK);
        for( int i = 0; i < rowNumber-1; i++ ) {
            for( int j = 0; j < rowNumber-1; j++ ) {
                g2d.drawRect( offX + halfSize + (size * i), offY + halfSize + (size * j), size, size );
            }
        }
    }

    private void drawShapes(Graphics2D g2d) {
        Stone stone;

        for( int i = 0; i < rowNumber; i++ ) {
            for( int j = 0; j < rowNumber; j++ ) {
                stone = gameEngine.getStone(i,j);

                if(stone.equals( Stone.BLACK ) ) {
                    g2d.setColor( new Color ( 20, 20, 20 ) );
                    g2d.fillOval( offX + (size * i), offY + (size * j), size, size );
                }
                else if(stone.equals( Stone.WHITE ) ) {
                    g2d.setColor(new Color(255, 255, 255));
                    g2d.fillOval(offX  + (size * i), offY + (size * j), size, size);
                }
            }
        }

        if( gameEngine.getLastMove()[0] != -1) {
            g2d.setColor(Color.RED);
            g2d.fillRect(offX + 14 + (size * gameEngine.getLastMove()[0]), offY + 14 + (size * gameEngine.getLastMove()[1]), 9, 9);
        }
    }

    private void setButtons(int rowNumber, JLabel blackP, JLabel whiteP) {

        buttonList = new JLabel[19][19];

        if( width > height ) size = (height - (offY*2)) / rowNumber;
        else size = (width - (offY*2)) / rowNumber;

        for( int i = 0; i < rowNumber; i++ ) {
            for( int j = 0; j < rowNumber; j++ ) {
                buttonList[i][j] = new JLabel();
                //buttonList[i][j].setOpaque(true);
                //buttonList[i][j].setBackground( Color.cyan );
                buttonList[i][j].setBounds( offX + (size * i), offY + (size * j), size, size );
                buttonList[i][j].addMouseListener( new FieldClickAdapter(client, i,j, turn, gameEngine, this, blackP, whiteP) );

                add( buttonList[i][j] );
            }
        }
    }
}
