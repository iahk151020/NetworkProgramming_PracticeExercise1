package Controllers;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientController {
    private Socket clientSocket;

    public ClientController() {}

    public void openConnection() throws UnknownHostException, IOException{
        clientSocket = new Socket("localhost", ServerController.port);
    }

    public void closeConnection() throws IOException{
        clientSocket.close();
    }

    public void sendData(String filter) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.writeObject(filter);
    }

    public Object getData() throws IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        Object data = in.readObject();
        return data;
    }
}
