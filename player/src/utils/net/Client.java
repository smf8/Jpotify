package utils.net;

import Model.Request;
import Model.Song;
import Model.User;
import View.Main;
import View.MainFrame;
import utils.IO.FileIO;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

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
                switch (inRequest.getType()) {
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
                    case 1:
                       Request r = sendFileRequest(inRequest.getUser());
                        try {
                            outputStream.writeObject(r);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        }).start();
    }

    public void closeConnection() {
        try {
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveFileRequest(User targetUser){
        outRequest = new Request(1,Main.user);
        outRequest.addRequestedStringData(targetUser.getUsername());
        sendRequest(outRequest);
    }
    public Request sendFileRequest(User targetUser) {
        URL url = null;
        try {
            url = Main.user.getCurrentSong().getLocation().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        File file = new File(String.valueOf(url));
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fis.read(bytesArray); //read file into bytes[]
             fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outRequest = new Request(2,Main.user);
        outRequest.setDataToTransfer(bytesArray);
        outRequest.setSong(Main.user.getCurrentSong());
        outRequest.addRequestedStringData(targetUser.getUsername());
        return outRequest;
    }
    public void receiveAndSaveFile(Request request){
        Song requestSong = request.getSong();
        File newFile = new File(FileIO.RESOURCES_RELATIVE + "songs" + File.separator + requestSong.getHash() + ".mp3");
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(newFile));
            outputStream.write(request.getDataToTransfer());
            outputStream.close();
            requestSong.setLocation(newFile.toURI());
            ArrayList<Song> tempSong = new ArrayList<>();
            tempSong.add(requestSong);
            Main.databaseHandler.deepInsertSong(tempSong);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
