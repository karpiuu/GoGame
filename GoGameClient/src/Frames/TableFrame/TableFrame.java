package Frames.TableFrame;

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

    public TableFrame() {

        super("TableFrame");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocation(50, 50);
        setLayout(new GridLayout(4, 1));

        JLabel userLabel = new JLabel("USER", JLabel.CENTER);
        add(userLabel);


        JList<String> userList = new JList<String>();
        add(userList);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ButtonRefreshAdapter());
        add(refreshButton);

        setResizable(false);
        setVisible(true);

    }

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
                    ArrayOfUserName.add(line.substring(start, end - 1));
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

    public void refreshUserList() {

    }

    public void refreshTableList() {

    }
}
