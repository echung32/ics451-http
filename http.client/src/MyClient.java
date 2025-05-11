import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * References
 * - https://www.geeksforgeeks.org/socket-programming-in-java/
 * - https://javarevisited.blogspot.com/2015/06/how-to-create-http-server-in-java-serversocket-example.html
 * - https://www.naukri.com/code360/library/bufferedinputstream-and-bufferedoutputstream
 */

public class MyClient {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new Error("Missing port number in arguments.");
        }

        System.out.println("Starting a new HTTPClient...");
        HTTPClient client = new HTTPClient(Integer.parseInt(args[0]));
        client.start();
    }
}

class HTTPClient {
    int port;

    Socket socket = null;

    public DataInputStream input = null;

    HTTPClient(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        socket = new Socket("localhost", this.port);
        System.out.println("Client has been started on port " + port);

        System.out.println("Creating an input stream...");
        input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        System.out.println("Reading message received from server...");
        String message = input.readUTF();
        System.out.println("Received message from server: " + message);

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter("client_file.txt"));
        fileWriter.write(message);
        System.out.println("Message saved to file client_file.txt");

        fileWriter.close();
        System.out.println("File writer closed");

        input.close();
        System.out.println("Input stream closed");

        socket.close();
        System.out.println("Connection closed");
    }
}
