package Game;

import javax.swing.*;

/**
 * Created by SZYMON on 08.12.2016.
 */
public class Field {

    public StateStone stateStone;
    public JLabel image;

    public Field(){
        stateStone = StateStone.EMPTY;

        ImageIcon imageIcon = new ImageIcon();
        image = new JLabel();
    }
}
