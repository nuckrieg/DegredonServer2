/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.degredonserver.specifics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import com.mycompany.degredon.Calculator;
//import com.mycompany.degredon.Enemy;
//import com.mycompany.degredon.Player;
import com.nuckrieg.degredon.specifics.Enemy;
import com.nuckrieg.degredon.specifics.Player;
import com.nuckrieg.degredon.functions.Calculator;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * A server program which accepts requests from clients to capitalize strings.
 * When a client connects, a new thread is started to handle it. Receiving
 * client data, capitalizing it, and sending the response back is all done on
 * the thread, allowing much greater throughput because more clients can be
 * handled concurrently.
 */
public class Server {

    static Player player = null;
    static Enemy enemy = null;
    static Player[] allPlayers = new Player[2];
    static Socket socket = null;
    static ServerSocket listener = null;
    public boolean serverStarted = false;
    public static int serverPort = 12345;
    public static final String SERVERVERSION1 = "0.0.1-ALPHA";
    public static String SERVERVERSION2 = System.getProperty("SERVERVERSION",SERVERVERSION1);

    /**
     * Runs the server.When a client connects, the server spawns a new thread
 to do the servicing and immediately returns to listening. The application
 limits the number of threads via a thread pool (otherwise millions of
 clients could cause the server to run out of resources by allocating too
 many threads).
     * @param args
     */
    public static void main(String[] args) throws Exception {

    }

    public void stop() throws IOException {
        if (listener != null) {
            listener.close();
        }
        

    }

    public void start() throws Exception {
        listener = new ServerSocket(serverPort);
        System.out.println("Server Started");
        while (true) {
            try {
                socket = listener.accept();
            } catch (SocketException ex) {
                System.out.println("Server Stopped");
                serverStarted = false;
                break;
            }
            System.out.println("Connected: " + socket);
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            Object objectFromClient = ois.readObject();
            // System.out.println("closing ois");
            // ois.close();
            // socket.close();

            if (allPlayers[0] == null) {
                allPlayers[0] = (Player) objectFromClient;
            } else {
                allPlayers[1] = (Player) objectFromClient;
            }
            if (allPlayers[0] != null && allPlayers[1] != null) {

                final Player finalPlayer1 = allPlayers[0];
                final Player finalPlayer2 = allPlayers[1];
                allPlayers[0] = null;
                allPlayers[1] = null;
                finalPlayer1.setName("Nuckrieg");
                finalPlayer2.setName("O Mega Bot!");
                System.out.println("TEMOS JOGO BOYS!");
                Calculator game = new Calculator();
                game.fight(finalPlayer1, finalPlayer2);
                
            }

        }
    }

    private static class Capitalizer implements Runnable {

        private Socket socket;

        Capitalizer(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

        }
    }
}
