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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        receiveAndSaveFile(inRequest);
                        break;
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

        File file = new File(Main.user.getCurrentSong().getLocation());
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
        System.out.println("saving file over network");
        Path filePath = Paths.get(FileIO.RESOURCES_RELATIVE + "songs" + File.separator + requestSong.getTitle() + "-" + requestSong.getArtist() + ".mp3").toAbsolutePath();
        try {
            Files.write(filePath, request.getDataToTransfer());
            requestSong.setLocation(filePath.toUri());
            ArrayList<Song> tempSong = new ArrayList<>();
            tempSong.add(requestSong);
            Main.databaseHandler.deepInsertSong(tempSong);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
