package Frames.LobbyFrame;

import Connection.OpponentSignalObserver;
import Connection.SocketClient;
import Frames.GameFrame.GameFrame;
import GameEngine.SignalOperation;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

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

    public void init(){
        refreshButton.addActionListener(new ButtonRefreshAdapter(client, this));
    }
}
