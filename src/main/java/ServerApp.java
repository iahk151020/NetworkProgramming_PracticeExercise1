import java.io.IOException;
import java.sql.SQLException;

import Controllers.ServerController;

public class ServerApp {
    public static void main(String[] args) {
        try {
            ServerController server = new ServerController();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
