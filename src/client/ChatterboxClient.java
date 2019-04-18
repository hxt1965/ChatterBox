package client;

import common.ChatterboxProtocol;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client socket that connects to the server and handles the interface on the
 * client's side and accepts any messages incoming from the server
 *
 * @author: harsh tagotra
 */

public class ChatterboxClient implements ChatterboxProtocol
{
    //default localhost address
    public static final String LOCAL_HOST = "127.0.0.1";
    //default port number
    public static final int LOCAL_PORT = 4567;
    //default username
    public static final String LOCAL_NAME = "default";

    /** the network incoming connection */
    private Scanner netIn;
    /** the network outgoing connection */
    private PrintStream netOut;
    /** host name */
    private String hostName;
    /** port number */
    private int port;

    private boolean isConnected;

    /**
     * constructor to initialize wrapper classes for reading and printing
     * and the host name and port number
     *
     * @param netIn: Input Stream
     * @param netOut: OutputStream
     * @param hostName: HostName/IP Address
     * @param port: port number
     */
    public ChatterboxClient(Scanner netIn, PrintStream netOut, String hostName, int port)
    {
        this.netIn = netIn;
        this.netOut = netOut;
        this.hostName = hostName;
        this.port = port;
    }

    /**
     * getter for host name
     *
     * @return: the host name
     */
    public String getHostName()
    {
        return hostName;
    }

    /**
     * getter for port number
     *
     * @return: the port number
     */
    public int getPort()
    {
        return port;
    }

    /**
     * reads message recieved from server and prints the output accordingly to the client
     */
    public void read()
    {
        while(true) {
            String msg = this.netIn.nextLine();
            String mes[] = msg.split(ChatterboxProtocol.SEPARATOR);
            switch(mes[0])
            {
                case ChatterboxProtocol.CONNECTED:
                    System.out.println("Chatterbox Server host: "+hostName);
                    System.out.println("Chatterbox server port: "+port);
                    break;
                case ChatterboxProtocol.DISCONNECTED:
                    System.out.println("Goodbye!");
                    break;
                case ChatterboxProtocol.ERROR:
                    System.out.println(mes[1]);
                    break;
                case ChatterboxProtocol.CHAT_RECEIVED:
                    System.out.println(mes[1]+" said: "+mes[2]);
                    break;
                case ChatterboxProtocol.FATAL_ERROR:
                    System.out.println(mes[1]);
                    break;
                case ChatterboxProtocol.WHISPER_RECEIVED:
                    System.out.println(mes[1]+" whispers to you: "+mes[2]);
                    break;
                case ChatterboxProtocol.WHISPER_SENT:
                    System.out.println("You whispered to "+mes[1]+": "+mes[2]);
                    break;
                case ChatterboxProtocol.USERS:
                    System.out.println("The following users are connected:");
                    for(int i=1; i<mes.length; i++)
                        System.out.println(mes[i]);
                    break;
                case ChatterboxProtocol.USER_LEFT:
                    System.out.println("A user has left the server: "+mes[1]);
                    break;
                case ChatterboxProtocol.USER_JOINED:
                    System.out.println("A user has joined the Chatterbox server: "+mes[1]);
                    break;
            }
            if(msg.equals(ChatterboxProtocol.DISCONNECTED) || msg.equals(ChatterboxProtocol.FATAL_ERROR))
                isConnected = false;
        }
    }

    /**
     * sends connect message to server
     *
     * @param userName: username which connects to the server
     */
    public void connect(String userName)
    {
        this.netOut.println(ChatterboxProtocol.CONNECT+ChatterboxProtocol.SEPARATOR+userName);
    }

    /**
     *  sends disconnect message to server
     */
    public void disconnect()
    {
        this.netOut.println(ChatterboxProtocol.DISCONNECT);
    }

    /**
     * sends normal chat to server
     *
     * @param message: the message to be sent
     */
    public void chat(String message)
    {
        this.netOut.println(ChatterboxProtocol.SEND_CHAT+ChatterboxProtocol.SEPARATOR+message);
    }

    /**
     * sends whisper to a certain user connected to the server
     *
     * @param recipient: user to receive the message
     * @param message: the message to be sent
     */
    public void whisper(String recipient, String message)
    {
        this.netOut.println(ChatterboxProtocol.SEND_WHISPER+ChatterboxProtocol.SEPARATOR+recipient+
            ChatterboxProtocol.SEPARATOR+message);
    }

    /**
     * sends command to server to print the list of users
     */
    public void printList()
    {
        this.netOut.println(ChatterboxProtocol.LIST_USERS);
    }

    /**
     * prints the host name, port number and username, and then prompts the user for input
     * until the user types the command to disconnect or if an error occurs
     */
    public void mainLoop()
    {
        Scanner sc = new Scanner(System.in);
        String comm = sc.nextLine();
        String c[] = comm.split("::", 2);

        String prompt = "Welcome to Chatterbox! Type '/help' to see a list of commands.";
        String helpMenu = "/help - displays this message\n" +
                "/quit - quit Chatterbox\n" +
                "/c <message> - send a message to all currently connected users\n" +
                "/w <recipient> <message> - send a private message to the recipient\n" +
                "/list - display a list of currently connected users";
        String userName = c[1];
        String msg;
        boolean isConnected=true;

        if(c[0].equals(ChatterboxProtocol.CONNECT)) {
            connect(c[1]);
        }
        else {
            System.out.println("Invalid command!");
            return;
        }

        new Thread(() -> read()).run();



            while (isConnected)
            {
                System.out.println(prompt);
                String input = sc.nextLine();

                String inp[] = input.split(" ", 2);

                switch (inp[0]) {
                    case "/help":
                        System.out.println(helpMenu);
                        break;
                    case "/quit":
                        disconnect();
                        break;
                    case "/c":
                        chat(inp[1]);
                        break;
                    case "/w":
                        String mes[] = inp[1].split(" ", 2);
                        whisper(mes[0], mes[1]);
                        break;
                    case "/list":
                        printList();
                        break;
                    default:
                        System.out.println("Invalid command entered!");
                        break;
                }
            }
    }

    /**
     * reads the data provided in args, if any, and creates socket and the input and print streams,
     * before creating a client object
     * @param args
     */
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        String host;
        int p;

        if(args.length==0)
        {
            host = LOCAL_HOST;
            p = LOCAL_PORT;
        }
        else if(args.length==2)
        {
            host = args[0];
            p = Integer.parseInt(args[1]);
        }
        else
        {
            System.out.println("Usage: $ java ChatterBoxClient [hostname] [port]");
            return;
        }

        try(
                Socket socket = new Socket(host, p);
                Scanner netIn = new Scanner(new InputStreamReader(socket.getInputStream()));
                PrintStream netOut = new PrintStream(socket.getOutputStream())
                )
        {
            ChatterboxClient client = new ChatterboxClient(netIn, netOut, host, p);
            client.mainLoop();
        }
        catch(IOException ex)
        {
            System.out.println("Connection failed!");
            ex.printStackTrace();
        }
    }
}
