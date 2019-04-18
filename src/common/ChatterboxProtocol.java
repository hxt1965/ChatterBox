package common;

/**
 * Interface meant to be implemented by any classes that need to understand
 * the Chatterbox protocol.
 */
public interface ChatterboxProtocol
{
    int PORT=4567;


    /**
     * Used to separate tokens in protocol messages.
     */
     String SEPARATOR = "::";

    //
    // CLIENT MESSAGES - sent from client to server
    //

    /**
     * The protocol message sent from the client to the Chatterbox server to
     * initially connect to the chat room.
     */
     String CONNECT = "connect";

    /**
     * The protocol message sent from the client to the Chatterbox server to
     * disconnect from the chat room.
     */
     String DISCONNECT = "disconnect";

    /**
     * The protocol message sent from the client to the Chatterbox server to
     * send a message to the chat room.
     */
     String SEND_CHAT = "send_chat";

    /**
     * The protocol message sent from the client to the Chatterbox server to
     * send a whisper to another user in the chat room.
     */
     String SEND_WHISPER = "send_whisper";

    /**
     * The protocol message sent from the client to the Chatterbox server to
     * list the users currently connected to the chat room.
     */
     String LIST_USERS = "list_users";

    //
    // SERVER MESSAGES - sent from server to client
    //

    /**
     * The protocol message sent from the Chatterbox server to client to
     * indicate that the connection was successful.
     */
     String CONNECTED = "connected";

    /**
     * The protocol message sent from the Chatterbox server to client to
     * indicate that the disconnection was successful.
     */
     String DISCONNECTED = "disconnected";

    /**
     * The protocol message sent from the Chatterbox server to client to
     * indicate that the chat message was successfully received.
     */
     String CHAT_RECEIVED = "chat_received";

    /**
     * The protocol message sent from the Chatterbox server to client to
     * indicate that the whisper was successfully received.
     */
     String WHISPER_RECEIVED = "whisper_received";

    /**
     * The protocol message sent from the Chatterbox server to client to
     * respond to a request for a list of users.
     */
     String USERS = "users";

    /**
     * The protocol message sent from the Chatterbox server to client to
     * notify the client that a new user has joined the chat room.
     */
     String USER_JOINED = "user_joined";

    /**
     * The protocol message sent from the Chatterbox server to client to
     * notify the client that a user has left the chat room.
     */
     String USER_LEFT = "user_left";

    /**
     * The protocol message sent from the Chatterbox server to client to
     * notify the client that an error occurred when the server received the
     * most recent request.
     */
     String ERROR = "error";

    /**
     * The protocol message sent from the ChatterBoc server to the client to
     * notify the client that a fatal error has occurred; this will be
     * followed by a termination of the connection between the client and the
     * server.
     */
     String FATAL_ERROR = "fatal_error";

     String WHISPER_SENT = "whisper_sent";
}
