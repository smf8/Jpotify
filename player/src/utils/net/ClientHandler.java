package utils.net;

import Model.Request;
import Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private static HashMap<User, ObjectOutputStream> othersOutputStream = new HashMap<>();
    private static HashMap<User, ObjectInputStream> othersInputStream= new HashMap<>();
    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream output;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    @Override
    public void run() {
        try {
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            Request request = (Request) inputStream.readObject();
            User client = request.getUser();
            System.out.println("connected : " + client.getUsername());
            othersInputStream.put(client, inputStream);
            othersOutputStream.put(client, output);

            //listening to socket for input
            while (request.getType() != -1) {
                switch (request.getType()) {
                    case 0:
                        // connecting , tell all other sockets that this client has connected
                        for (Map.Entry<User, ObjectOutputStream> entry : othersOutputStream.entrySet()) {
                                if (!entry.getKey().equals(client)){
                                    entry.getValue().writeObject(request);
                                    entry.getValue().flush();
                                    System.out.println("sending request from [" + client.getUsername() + "] to [" + entry.getKey().getUsername() + "]");
                                }
                        }
                        break;
                }
                request = (Request) inputStream.readObject();
            }
            // if request type is -1 then it means user is logging out


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
