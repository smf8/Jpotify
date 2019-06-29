package utils.net;

import Model.Playlist;
import Model.Request;
import Model.Song;
import Model.User;
import View.Main;
import View.MainFrame;
import utils.IO.FileIO;
import utils.TagReader;

import java.io.*;
import java.lang.reflect.Array;
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
            outputStream.reset();
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
                    if (!((inRequest = (Request) inputStream.readObject()).getType() != -1)){
                        overrideDatabase(inRequest);
                        break;
                    }
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
                            if (inRequest.getDataToTransfer() != null){
                                // contains profile photo. save it and update users database
                                saveProfilePhoto(inRequest);
                            }
                            MainFrame.getInstance().friendsActivityPanelsManager.updateFriendsList(connectedFriend);
                            System.out.println("hooooooy : " + connectedFriend.getCurrentSong());
                        }
                        break;
                    case 1:
                       Request r = sendFileRequest(Main.user.getCurrentSong(),inRequest.getUser());
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
                    case 3:
                        // handling playlist retrieval
                        Playlist playlist = inRequest.getAlteredPlaylist();
                        Path filePath = Paths.get(FileIO.RESOURCES_RELATIVE + "cache" + File.separator + "img" + File.separator +playlist.getTitle() +".jpg").toAbsolutePath();
                        try {
                            Files.write(filePath, inRequest.getDataToTransfer());
                            playlist.setImageURI(filePath.toUri());
                            Main.user.addPlaylist(playlist);
                            Main.usersHandler.removeUser(inRequest.getUser().getUsername());
                            Main.usersHandler.addUser(inRequest.getUser());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Main.databaseHandler.insertPlaylist(playlist);
                        System.out.println("Saved Playlist");
                        MainFrame.getInstance().updatePlaylists();
                        break;
                    case 4:
                        ArrayList<String> receivedHash =  inRequest.getRequestedStringData();
                        receivedHash.remove(0);
                        for (Song s : MainFrame.getAllSongs()){
                            if (receivedHash.contains(s.getHash())){
                                receivedHash.remove(s.getHash());
                            }
                        }
                        // receivedHash is the array list of not found songs
                        receivedHash.add(0, inRequest.getUser().getUsername());
                        Request answ = new Request(5, Main.user);
                        answ.setRequestedStringData(receivedHash);
                        try {
                            outputStream.writeObject(answ);
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("case 4 received");
                        break;
                    case 5:
                        User user = inRequest.getUser();
                        ArrayList<String> need = inRequest.getRequestedStringData();
                        need.remove(0);
                        for (String hash : need){
                            Song songToSend = Main.databaseHandler.getSongByHash(hash);
                            Request r1 = sendFileRequest(songToSend, user);
                            try {
                                outputStream.writeObject(r1);
                                outputStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }
        }).start();
    }


    public void saveProfilePhoto(Request r){
        System.out.println("Saving profile photo");
        Path filePath = Paths.get(FileIO.RESOURCES_RELATIVE + "cache" +File.separator + "img" + File.separator +  r.getUser().getUsername() +".jpg").toAbsolutePath();
        try {
            Files.write(filePath, r.getDataToTransfer());
            r.getUser().setProfileImage(filePath.toUri());
            Main.usersHandler.removeUser(r.getUser().getUsername());
            Main.usersHandler.addUser(r.getUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void overrideDatabase(Request r){
        System.out.println("Overwriting database");
        Path filePath = Paths.get(FileIO.RESOURCES_RELATIVE + r.getUser().getUsername() + ".db").toAbsolutePath();
        try {
            Files.write(filePath, r.getDataToTransfer());
            Main.usersHandler.removeUser(r.getUser().getUsername());
            Main.usersHandler.addUser(r.getUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public Request prepareSong(String hash, User target){
        Song s = Main.databaseHandler.getSongByHash(hash);
        File file = new File(s.getLocation());
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
        outRequest.setSong(s);
        outRequest.addRequestedStringData(target.getUsername());
        return outRequest;
    }
    public Request sendFileRequest(Song song, User targetUser) {

        File file = new File(song.getLocation());
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
        outRequest.setSong(song);
        outRequest.addRequestedStringData(targetUser.getUsername());
        return outRequest;
    }

    public void receiveAndSaveFile(Request request){
        Song requestSong = request.getSong();
//        System.out.println("saving file over network");
        Path filePath = Paths.get(FileIO.RESOURCES_RELATIVE + "songs" + File.separator + requestSong.getTitle().replaceAll("|", "").replaceAll(":", "") + "-" + requestSong.getArtist().replaceAll(":", "").replaceAll("|", "") + ".mp3").toAbsolutePath();
        try {
            Files.write(filePath, request.getDataToTransfer());
            TagReader reader = new TagReader();
            reader.getAdvancedTags(filePath.toUri().toURL());
            Song s = reader.getSong();
            ArrayList<Song> tempSong = new ArrayList<>();
            tempSong.add(s);
            Main.databaseHandler.deepInsertSong(tempSong);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Request createPlaylistSendRequest(Playlist playlist, User target){
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
        Request r = new Request(3, Main.user);
        ArrayList<String> s = new ArrayList<>();
        s.add(target.getUsername());
        r.setDataToTransfer(bytesArray);
        r.setAlteredPlaylist(playlist);
        return r;
    }

    public static Request tellSongsHash(Playlist p, User target){
        Request r = new Request(4, Main.user);
        ArrayList<String> strings = new ArrayList<>();
        strings.add(target.getUsername());
        for (Song s : p.getSongs()){
            strings.add(s.getHash());
        }
        r.setRequestedStringData(strings);
        return r;
    }

    public static Request sharePlaylistRequest(Playlist p, User target){
        Request r = new Request(3, Main.user);
        File file = new File(p.getImageURI());
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
        r.setDataToTransfer(bytesArray);
        r.addRequestedStringData(target.getUsername());
        r.setAlteredPlaylist(p);
        return r;
    }
}
