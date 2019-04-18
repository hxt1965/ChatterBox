package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Represents a single server to client interaction. This is a seperate independent
 * thread of execution, which handles all messages received and sends back messages
 * accordingly
 *
 * @author: Harsh Tagotra
 */
public class ClientThread extends Thread
{
    //client socket from accept
    private Socket socket;
    //client's incoming stream
    private Scanner netIn;
    //server's outgoing stream
    private PrintStream netOut;

    /**
     * Create the client thread.  Takes the socket and binds the incoming and output
     * connections.
     *
     * @param socket: the client socket
     * @throws IOException: issa exception
     */
    public ClientThread(Socket socket) throws IOException
    {
        this.socket = socket;
        this.netIn = new Scanner(new InputStreamReader(socket.getInputStream()));
        this.netOut = new PrintStream(socket.getOutputStream());

    }


    /**
     * handles all messages recieved and runs the chatterbox
     */
    public void run()
    {
        Scanner sc = new Scanner(System.in);
        while(socket.isConnected())
        {
            String comm = sc.nextLine();

        }
    }
}