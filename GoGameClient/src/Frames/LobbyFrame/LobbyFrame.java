package Frames.LobbyFrame;

import Connection.OpponentSignalObserver;
import Connection.SocketClient;
import GameEngine.SignalOperation;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Class LobbyFrame create lobby frame with tables list, users list and buttons.
 */
public class LobbyFrame extends JFrame {

    SocketClient client;

    private JPanel panel1;
    private JList tableList;
    private JList userList;
    private JButton createNewTableButton;
    private JButton refreshButton;
    private OpponentSignalObserver opponentObserver;

    public LobbyFrame(SocketClient newClient, OpponentSignalObserver opponentObserver) {

        super("Lobby window");

        this.opponentObserver = opponentObserver;
        client = newClient;

        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        createNewTableButton.addActionListener( new ButtonCreateTableAdapter(client,this, opponentObserver) );
        tableList.addMouseListener(new ListJoinToTableAdapter(client, this, tableList, opponentObserver));

        setVisible(true);
    }

    /**
     * Function gets all usernames from server and relays to the list on client
     * @param line
     */
    public void refreshUserList(String line) {

        ArrayList<String> userArray = SignalOperation.parseString(line);

        DefaultListModel<String> listModel = new DefaultListModel<>();

        if( line.substring(0, line.indexOf(';')).equals("Users") ) {
            for (String i : userArray) {
                listModel.addElement(i);
            }
        }

        userList.setModel(listModel);

    }

    /**
     * Function gets all table from server and relays to the list on client
     * @param line
     */
    public void refreshTableList(String line) {
        ArrayList<String> tableArray = SignalOperation.parseString(line);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        String text = "";

        if( line.substring(0, line.indexOf(';')).equals("Tables") && tableArray.size() >= 3) {
            for(int i = 0; i < tableArray.size(); i += 3) {
                text = tableArray.get(i);

                if(!tableArray.get(i+1).equals("")) {
                    text += " " + tableArray.get(i+1);
                }

                if(!tableArray.get(i+2).equals("")) {
                    text += " " + tableArray.get(i+2);
                }

                listModel.addElement( text );
            }
        }

        tableList.setModel(listModel);
    }

    /**
     * Function init add ActionListener to the refresh button.
     */
    public void init(){
        refreshButton.addActionListener(new ButtonRefreshAdapter(client, this));
    }
}