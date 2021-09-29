package Controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.sun.tools.javac.util.List;

import Models.User;

import java.sql.Connection;

public class ServerController {
    private ServerSocket serverSocket;
    public static int port = 9090;
    private Connection con;

    public ServerController() throws ClassNotFoundException, SQLException, IOException{
        DBConnection();
        serverSocket = new ServerSocket(port);
        while (true){
            listening();
        }
        
    }

    private void DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/networkProgramming";
        String user = "root";
        String password = "1";
        con = DriverManager.getConnection(url,user,password);
        System.out.println("Connected to DB");
    }

    private void listening() throws IOException, ClassNotFoundException, SQLException{
        Socket clientSocket = serverSocket.accept();
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        Statement stmt = con.createStatement();
        Object data = in.readObject();
        if (data instanceof String){
            String filter = (String)data;
            //System.out.println("filter: " + filter);
            String query = "SELECT * FROM tbl_user WHERE username LIKE '%" + filter + "%'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next() == false){
                out.writeObject("Không tìm thấy user");
            } else {
                ArrayList<User> userList = new ArrayList<User>();
                while (rs.next()){
                    int userId = rs.getInt("userid");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String dob = rs.getString("dob");
                    String address = rs.getString("address");
                    String sex = rs.getString("sex");
                    String des = rs.getString("des");
                    User user = new User(userId, username, password, address, dob, sex, des);
                  //  System.out.println(userId + " " + username);
                    userList.add(user);
                }
                out.writeObject(userList);
            }

        }
        
    }
}
