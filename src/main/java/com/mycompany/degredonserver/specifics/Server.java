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
import com.nuckrieg.degredon.specifics.DamageDealt;
import com.nuckrieg.degredon.specifics.DamageTaken;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

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
    public static String SERVERVERSION2 = System.getProperty("SERVERVERSION", SERVERVERSION1);
    ObjectOutputStream sendToPlayerOne = null;
    ObjectOutputStream sendToPlayerTwo = null;
    String gameResult = "UNKNOWN!";
    JFrame player1Frame;
    JFrame player2Frame;
    final Lock lock = new ReentrantLock(true);

    /**
     * Runs the server.When a client connects, the server spawns a new thread to
     * do the servicing and immediately returns to listening. The application
     * limits the number of threads via a thread pool (otherwise millions of
     * clients could cause the server to run out of resources by allocating too
     * many threads).
     *
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
            OutputStream os = socket.getOutputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            Object objectFromClient = ois.readObject();
            // System.out.println("closing ois");
            // ois.close();
            // socket.close();

            if (allPlayers[0] == null) {

                sendToPlayerOne = oos;
                System.out.println(sendToPlayerOne);
                System.out.println("RECEIVED A PLAYER ONE");
                allPlayers[0] = (Player) objectFromClient;
            } else {

                sendToPlayerTwo = oos;
                System.out.println(sendToPlayerTwo);
                System.out.println("RECEIVED A PLAYER TWO");
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
//                sendToPlayerOne.writeObject("###############################");
//                sendToPlayerOne.writeObject("STARTING GAME!");
                boolean whatBackground = new Random().nextBoolean();
                //    System.out.println(finalPlayer1.getBackground());
                //   System.out.println(finalPlayer2.getBackground());
                String background = whatBackground == true ? finalPlayer1.getBackground() : finalPlayer2.getBackground();

//                player1Frame = new JFrame();
//                player1Frame.add(new GamePanel(finalPlayer1, finalPlayer2, background));
//
//                player2Frame = new JFrame();
//                player2Frame.add(new GamePanel(finalPlayer2, finalPlayer1, background));
                //  sendToPlayerOne.writeObject(player1Frame);
                sendToPlayerOne.writeObject(finalPlayer1);
                sendToPlayerTwo.writeObject(finalPlayer2);
                sendToPlayerOne.writeObject(finalPlayer2);
                sendToPlayerTwo.writeObject(finalPlayer1);
                sendToPlayerOne.writeObject(background);
                sendToPlayerTwo.writeObject(background);
                sendToPlayerOne.writeObject(game);
                sendToPlayerTwo.writeObject(game);
                //  sendToPlayerTwo.writeObject(player2Frame);

                sendToPlayerOne.writeObject("###############################");
                sendToPlayerTwo.writeObject("###############################");
                sendToPlayerOne.writeObject("STARTING GAME!");
                sendToPlayerTwo.writeObject("STARTING GAME!");
//                do {
//                    new Timer((int) game.getAttackDelay(finalPlayer1), new ActionListener() {
//
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//
//                            try {
//                                System.out.println("DELAYING PLAYER 1 FOR: " + (long) game.getAttackDelay(finalPlayer1));
//                                Thread.sleep((long) game.getAttackDelay(finalPlayer1));
//                                float p1DamageDealt = game.fight(finalPlayer1, finalPlayer2);
//                                game.setCurrentHp(finalPlayer2, p1DamageDealt);
//
//                                sendToPlayerTwo.writeObject(/*"YOU TOOK " + */new DamageTaken(String.valueOf(p1DamageDealt))/* + "!"*/);
//                                sendToPlayerTwo.writeObject("YOU HAVE " + game.getCurrentHp(finalPlayer2) + " HP LEFT!");
//                                if (game.getCurrentHp(finalPlayer2) <= 0) {
//                                    ((Timer) e.getSource()).stop();
//                                }
//                                sendToPlayerOne.writeObject(/*"YOU DEALT " + */new DamageDealt(String.valueOf(p1DamageDealt))/* + "!"*/);
//                                sendToPlayerOne.writeObject("ENEMY HAS " + game.getCurrentHp(finalPlayer2) + " HP LEFT!");
//
//                                if (game.getCurrentHp(finalPlayer1) <= 0) {
//                                    ((Timer) e.getSource()).stop();
//                                }
//                            } catch (InterruptedException ex) {
//                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//                            } catch (IOException ex) {
//                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                    }).start();
//                    break;
//                } while (finalPlayer1.getCurrentHp() > 0 && finalPlayer2.getCurrentHp() > 0);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        do {
                            try {
                                System.out.println("p1-1");
                                System.out.println("DELAYING PLAYER 1 FOR: " + (long) game.getAttackDelay(finalPlayer1));
                                System.out.println("p1-2");
                                lock.lock();
                                Thread.sleep((long) game.getAttackDelay(finalPlayer1));
                                lock.unlock();
                                System.out.println("p1-3");
                                lock.lock();
                                float p1DamageDealt = game.fight(finalPlayer1, finalPlayer2);
                                lock.unlock();
                                System.out.println("p1-4");
                                lock.lock();
                                game.setCurrentHp(finalPlayer2, p1DamageDealt);
                                lock.unlock();
                                System.out.println("p1-5");
                                lock.lock();
                                sendToPlayerTwo.writeObject(/*"YOU TOOK " + */new DamageTaken(String.valueOf(p1DamageDealt))/* + "!"*/);
                                lock.unlock();
                                System.out.println("p1-6");
                                lock.lock();
                                sendToPlayerTwo.writeObject("YOU HAVE " + game.getCurrentHp(finalPlayer2) + " HP LEFT!");
                                lock.unlock();
                                System.out.println("p1-7");
                                
                                if (game.getCurrentHp(finalPlayer2) <= 0) {
                                    System.out.println("p1-8");
                                    
                                    break;
                                }
                                System.out.println("p1-9");
                                lock.lock();
                                sendToPlayerOne.writeObject(/*"YOU DEALT " + */new DamageDealt(String.valueOf(p1DamageDealt))/* + "!"*/);
                                lock.unlock();
                                System.out.println("p1-10");
                                lock.lock();
                                sendToPlayerOne.writeObject("ENEMY HAS " + game.getCurrentHp(finalPlayer2) + " HP LEFT!");
                                lock.unlock();

                                if (game.getCurrentHp(finalPlayer1) <= 0) {
                                    System.out.println("p1-11");
                                    break;
                                }

                                //sendToPlayerOne.writeObject(p1DamageDealt);
                            } catch (InterruptedException | IOException ex) {
                                System.out.println("p1-12");
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } while (finalPlayer1.getCurrentHp() > 0 && finalPlayer2.getCurrentHp() > 0);
                        if (finalPlayer1.getCurrentHp() <= 0 && finalPlayer2.getCurrentHp() > 0) {
                            System.out.println("p1-13");
                            gameResult = "YOU LOSE!";
                        } else if (finalPlayer2.getCurrentHp() <= 0 && finalPlayer1.getCurrentHp() > 0) {
                            System.out.println("p1-14");
                            gameResult = "YOU WIN!";
                        } else {
                            System.out.println("p1-15");
                            gameResult = "IT'S A TIE!";

                        }
                        try {
                            System.out.println("p1-16");
                            lock.lock();
                            try {
                                sendToPlayerOne.writeObject(gameResult);
                            } finally {
                                lock.unlock();
                            }
                        } catch (IOException ex) {
                            System.out.println("p1-17");
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }).start();
//                do {
//                    new Timer((int) game.getAttackDelay(finalPlayer2), new ActionListener() {
//
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//
//                            try {
//                                System.out.println("DELAYING PLAYER 2 FOR: " + (long) game.getAttackDelay(finalPlayer2));
//                                Thread.sleep((long) game.getAttackDelay(finalPlayer2));
//                                float p2DamageDealt = game.fight(finalPlayer2, finalPlayer1);
//                                game.setCurrentHp(finalPlayer1, p2DamageDealt);
//                                   sendToPlayerTwo.writeObject(game);
//
//                                sendToPlayerOne.writeObject(/*"YOU TOOK " + */new DamageTaken(String.valueOf(p2DamageDealt))/* + "!"*/);
//                                sendToPlayerOne.writeObject("YOU HAVE " + game.getCurrentHp(finalPlayer1) + " HP LEFT!");
//                                if (game.getCurrentHp(finalPlayer1) <= 0) {
//                                    ((Timer) e.getSource()).stop();
//                                }
//
//                                sendToPlayerTwo.writeObject(/*"YOU DEALT " + */new DamageDealt(String.valueOf(p2DamageDealt))/* + "!"*/);
//                                sendToPlayerTwo.writeObject("ENEMY HAS " + game.getCurrentHp(finalPlayer1) + " HP LEFT!");
//
//                                if (game.getCurrentHp(finalPlayer2) <= 0) {
//                                    ((Timer) e.getSource()).stop();
//                                }
//                            } catch (InterruptedException ex) {
//                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//                            } catch (IOException ex) {
//                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                    }).start();
//                    break;
//                } while (finalPlayer1.getCurrentHp() > 0 && finalPlayer2.getCurrentHp() > 0);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        do {
                            try {
                                System.out.println("p2-1");
                                Thread.sleep(1000);
                                System.out.println("p2-2");
                                System.out.println("DELAYING PLAYER 2 FOR: " + (long) game.getAttackDelay(finalPlayer2));
                                System.out.println("p2-3");
                                Thread.sleep((long) game.getAttackDelay(finalPlayer2));
                                System.out.println("p2-4");
                                float p2DamageDealt = game.fight(finalPlayer2, finalPlayer1);
                                System.out.println("p2-5");
                                game.setCurrentHp(finalPlayer1, p2DamageDealt);
                                //   sendToPlayerTwo.writeObject(game);

                                System.out.println("p2-6");
                                sendToPlayerOne.writeObject(/*"YOU TOOK " + */new DamageTaken(String.valueOf(p2DamageDealt))/* + "!"*/);
                                System.out.println("p2-7");
                                sendToPlayerOne.writeObject("YOU HAVE " + game.getCurrentHp(finalPlayer1) + " HP LEFT!");
                                if (game.getCurrentHp(finalPlayer1) <= 0) {
                                    System.out.println("p2-8");
                                    break;
                                }

                                System.out.println("p2-9");
                                sendToPlayerTwo.writeObject(/*"YOU DEALT " + */new DamageDealt(String.valueOf(p2DamageDealt))/* + "!"*/);
                                System.out.println("p2-10");
                                sendToPlayerTwo.writeObject("ENEMY HAS " + game.getCurrentHp(finalPlayer1) + " HP LEFT!");

                                if (game.getCurrentHp(finalPlayer2) <= 0) {
                                    System.out.println("p2-11");
                                    break;
                                }

                                //sendToPlayerOne.writeObject(p1DamageDealt);
                            } catch (InterruptedException | IOException ex) {
                                System.out.println("p2-12");
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } while (finalPlayer1.getCurrentHp() > 0 && finalPlayer2.getCurrentHp() > 0);
                        if (finalPlayer1.getCurrentHp() <= 0 && finalPlayer2.getCurrentHp() > 0) {
                            System.out.println("p2-13");
                            gameResult = "YOU WIN!";
                        } else if (finalPlayer2.getCurrentHp() <= 0 && finalPlayer1.getCurrentHp() > 0) {
                            System.out.println("p2-14");
                            gameResult = "YOU LOSE!";
                        } else {
                            System.out.println("p2-15");
                            gameResult = "IT'S A TIE!";
                        }
                        try {
                            System.out.println("p2-16");
                            sendToPlayerTwo.writeObject(gameResult);
                        } catch (IOException ex) {
                            System.out.println("p2-17");
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();

//                oos.flush();
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
