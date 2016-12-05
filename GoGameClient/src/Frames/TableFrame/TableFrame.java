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
    JList<String> tableList;

    public TableFrame(SocketClient newclient) {

        super("TableFrame");

        client =  newclient;

        /* USER */

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridBagLayout());

        JLabel tableLabel = new JLabel("TABLE", JLabel.LEFT);
        userPanel.add(tableLabel);

        setTitle("Table");
        setBackground(Color.gray);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocation(50, 50);
        //setLayout(new GridLayout(3, 2));
        setResizable(false);
        setVisible(true);

//        JPanel topPanel = new JPanel();
//        JPanel btnPanel = new JPanel();
//
//        topPanel.setLayout(new BorderLayout());
//        getContentPane().add(topPanel, BorderLayout.CENTER);
//        getContentPane().add(btnPanel, BorderLayout.SOUTH);
//        JScrollPane scrollPane = new JScrollPane();
//        topPanel.add(scrollPane,BorderLayout.CENTER);
//        JButton addButton = new JButton("ADD");
//        JButton delButton = new JButton("DELETE");
//        JButton saveButton = new JButton("SAVE");
//
//        btnPanel.add(addButton);
//        btnPanel.add(delButton);

        /* USER */

//        JPanel usersPanel = new JPanel();
//        JPanel tablePanel = new JPanel();
//        JPanel buttonsPanel = new JPanel();

//        JPanel container = new JPanel();
//        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
//
//        JPanel panel1 = new JPanel();
//        JPanel panel2 = new JPanel();
//
////panel1.set[Preferred/Maximum/Minimum]Size()
//
//        container.add(panel1);
//        container.add(panel2);
//
//        JLabel userLabel = new JLabel("USER", JLabel.CENTER);
//        panel1.add(userLabel);
//
//        userList = new JList<String>();
//        usersPanel.add(userList);
//

//

//
//        tableList = new JList<String>();
//        add(tableLabel);
//
//        refreshButton = new JButton("Refresh");
//        add(refreshButton);



    }

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
