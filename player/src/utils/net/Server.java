package utils.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {
    public static final int PORT = 20203;

    public Server(){
        try {
            ServerSocket socket = new ServerSocket(PORT);
            var threadPool = Executors.newFixedThreadPool(20);
            while(true){
                threadPool.execute(new ClientHandler(socket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server sv = new Server();
    }
}
