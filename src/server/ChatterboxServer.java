package server;

import client.ChatterboxClient;
import common.ChatterboxProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * server runs on default port=4567 and is always waiting on a new connection
 */
public class ChatterboxServer
{
    /**
     * an infinite loop to accept any connections.
     * when accepted, creates a new thread to handle specific client
     *
     * @param args: unused
     */
    public static void main(String args[])
    {
        System.out.println("Waiting for connections on port "+ ChatterboxProtocol.PORT);
        Scanner sc = new Scanner(System.in);

        try(ServerSocket serverSocket = new ServerSocket(ChatterboxProtocol.PORT))
        {
            while(true)
            {
                try {
                    ClientThread client = new ClientThread(serverSocket.accept());
                    String comm = sc.nextLine();

                    client.start();
                }catch(IOException ex)
                {
                    ex.printStackTrace();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
