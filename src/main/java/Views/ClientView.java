package Views;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import Controllers.ClientController;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import Models.User;
import java.awt.Dimension;

public class ClientView extends JFrame implements ActionListener {

    private JButton submitBtn;
    private JTextField filterField;
    private JPanel content;
    private ClientController controller;
    private JTextArea userList;
  
    
    public ClientView(){
        super("QUản lý user");
        controller = new ClientController();
        content = new JPanel();
        filterField = new JTextField(15);
        userList = new JTextArea();
        userList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel filterName = new JLabel("Nhập tên người dùng: ");
        submitBtn = new JButton("Tìm");
        content.setPreferredSize(new Dimension(400,300));
        content.add(filterName);
        content.add(filterField);
        content.add(submitBtn);
        content.add(userList);
        submitBtn.addActionListener(this);

        this.setSize(new Dimension(1200, 1000));
        this.setContentPane(content);
        this.pack();
        this.setResizable(true);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
                }
            });
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(submitBtn)){
            //createNewTable();
            String filter = filterField.getText();
            System.out.println("Clicked btn");
            try {
                userList.setText("ID | USERNAME | DOB | ADDRESS | SEX | DESCRIPTION\n");
                controller.openConnection();
                controller.sendData(filter);
                Object reply = controller.getData();
                System.out.println("reply:" + reply);
                if (reply instanceof String){
                    JOptionPane.showMessageDialog(this,(String)reply);
                } else if (reply instanceof ArrayList){
                    ArrayList<User> dataSet = (ArrayList<User>)reply;
                    for (User user : dataSet) {
                        System.out.println(user);
                         userList.append(String.valueOf(user.getUserId()) + " | " + user.getUserName() + " | " + user.getBirthday()
                        + " | " + user.getAddress() + " | " + user.getSex() + " | " + user.getDes() + "\n" );
                    }
                }
                
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            

        }
        
    }
}
