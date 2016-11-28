package Frames.TableFrame;

import Connection.SocketClient;
import Frames.LoginFrame.ButtonlogButtonAdapter;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.omg.PortableInterceptor.USER_EXCEPTION;

import javax.swing.*;
import java.awt.*;

import java.awt.*;
import java.util.ArrayList;

import Exception.PartUserNameException;

import static com.sun.glass.ui.Cursor.setVisible;

/**
 * Created by SZYMON on 28.11.2016.
 */
public class TableFrame extends JFrame {

    JButton refreshButton;
    SocketClient client;
    JList<String> userList;

    public TableFrame(SocketClient newclient) {

        super("TableFrame");

        client =  newclient;


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocation(50, 50);
        setLayout(new GridLayout(4, 1));

        JLabel userLabel = new JLabel("USER", JLabel.CENTER);
        add(userLabel);

        userList = new JList<String>();
        add(userList);

        refreshButton = new JButton("Refresh");
        add(refreshButton);

        setResizable(false);
        setVisible(true);

    }
/*
    public ArrayList<String> partUserName(String line) throws PartUserNameException {
        ArrayList<String> ArrayOfUserName = new ArrayList<>();

        if (line.charAt(0) == 'U')
        {
            if (line.length() > 2)
            {
                int start = 2;
                int end;

                do
                {
                    end = line.indexOf(';', start);
                    ArrayOfUserName.add(line.substring(start, end));
                    start = end + 1;
                }
                while (start < line.length() || end == -1);

                return ArrayOfUserName;
            }
            else
            {
                throw new PartUserNameException();
            }
        }
        else
            throw new PartUserNameException();
    }
*/
    private ArrayList<String> parseString(String line) {

        if( line.length() > 0 ) {
            ArrayList<String> argv = new ArrayList<>();

            int start = line.indexOf(';',0);
            int end = 1;

            while (start < line.length() || end == -1) {
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

    public void refreshTableList() {

    }

    public void init(){

        refreshButton.addActionListener(new ButtonRefreshAdapter(this, client));

    }
}
