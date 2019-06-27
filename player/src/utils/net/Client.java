package utils.net;

import Model.Request;
import Model.User;
import View.FriendsActivityPanelsManager;
import View.Main;
import View.MainFrame;
import utils.IO.DatabaseConnection;
import utils.IO.DatabaseHandler;
import utils.IO.DatabaseHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Request outRequest;
    private Request inRequest;
    private User user;

    public Client(String serverAddress, User clientUser) {
        System.out.println("runned");
        this.user = clientUser;
        try {
            socket = new Socket(serverAddress, Server.PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            // reading inputs
            setupListen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(Request request) {
        try {
            outputStream.writeObject(request);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupListen() {
        new Thread(() -> {
            while (true) {
                try {
                    if (!((inRequest = (Request) inputStream.readObject()).getType() != -1)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                switch (inRequest.getType()){
                    // case 0 : user connected or profile updated
                    case 0:
                        User connectedFriend = inRequest.getUser();
                        System.out.println(connectedFriend.getCurrentSong().getTitle());
//                        DatabaseHandler handler = new DatabaseHelper(new DatabaseConnection(connectedFriend.getUsername()).getConnection());
                        if (Main.user.getFriends().contains(connectedFriend.getUsername())) {
                            MainFrame.getInstance().friendsActivityPanelsManager.updateFriendsList();
                            System.out.println("hooooooy : " + connectedFriend.getUsername());
                        }
                        break;
                }
            }
        }).start();
    }
    public void closeConnection(){
        try {
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
