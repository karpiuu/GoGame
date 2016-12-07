package Frames.LobbyFrame;

import Connection.SocketClient;
import Frames.GameFrame.GameFrame;

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

    public LobbyFrame(SocketClient newClient) {
        super("Lobby window");

        client = newClient;

        setContentPane(panel1);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setResizable(false);

        createNewTableButton.addActionListener( new ButtonCreateTableAdapter(client) );

        setVisible(true);

        tableList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getClickCount() == 2 )
                {
                    int index = tableList.locationToIndex(e.getPoint());

                    client.out.println("SitDown;"+index);

                    String line;

                    try {
                        line = client.in.readLine();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        return;
                    }

                    if(line.equals("OK")) {
                        GameFrame gameFrame = new GameFrame(client);
                    }

                }
            }
        });
    }

    public ArrayList<String> parseString(String line) {

        if( line.length() > 0 ) {
            ArrayList<String> argv = new ArrayList<>();

            int start = line.indexOf(';',0)+1;
            int end = 1;

            while (start < line.length() && end != -1) {
                end = line.indexOf(';', start);
                if( end != -1 ) {
                    argv.add(line.substring(start, end));
                    start = end + 1;
                }
            }
            return argv;
        }
        return null;
    }

    public void refreshUserList(String line) {

        ArrayList<String> userArray = parseString(line);

        DefaultListModel<String> listModel = new DefaultListModel<>();

        if( line.substring(0, line.indexOf(';')).equals("Users") ) {
            for (String i : userArray) {
                listModel.addElement(i);
            }
        }

        userList.setModel(listModel);

    }

    public void refreshTableList(String line) {
        ArrayList<String> tableArray = parseString(line);

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
