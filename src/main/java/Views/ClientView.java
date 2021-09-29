package Views;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
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
    private JTable table;
    private DefaultTableModel dm;
  
    
    public ClientView(){
        super("QUản lý user");
        controller = new ClientController();
        content = new JPanel();
        filterField = new JTextField(15);
        userList = new JTextArea();
        userList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel filterName = new JLabel("Nhập tên người dùng: ");
        submitBtn = new JButton("Tìm");
        String[] cols = new String[]{"STT", "ID", "USERNAME", "BIRTHDAY", "ADDRESS", "SEX", "DESCRIPTION"};
        dm = new DefaultTableModel(new Object[][]{}, cols);
        table = new JTable();
        table.setModel(dm);
        JScrollPane scrollPane = new JScrollPane(table);
        content.setPreferredSize(new Dimension(600,500));
        content.add(filterName);
        content.add(filterField);
        content.add(submitBtn);
        content.add(scrollPane);
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

            String filter = filterField.getText();
            System.out.println("Clicked btn");
            try {
                dm.setRowCount(0);
                userList.setText("ID | USERNAME | DOB | ADDRESS | SEX | DESCRIPTION\n");
                controller.openConnection();
                controller.sendData(filter);
                Object reply = controller.getData();
                if (reply instanceof String){
                    JOptionPane.showMessageDialog(this,(String)reply);
                } else if (reply instanceof ArrayList){
                    int index = 0;
                    ArrayList<User> dataSet = (ArrayList<User>)reply;
                    for (User user : dataSet) {
                        index++;
                        dm.addRow(new Object[]{index,user.getUserId(), user.getUserName(), user.getBirthday(), user.getAddress(), user.getSex(), user.getDes()});
                    }
                }
                
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            

        }
        
    }
}
