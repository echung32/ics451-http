import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * References
 * - https://www.geeksforgeeks.org/socket-programming-in-java/
 * - https://javarevisited.blogspot.com/2015/06/how-to-create-http-server-in-java-serversocket-example.html
 * - https://www.naukri.com/code360/library/bufferedinputstream-and-bufferedoutputstream
 */

public class MyServer {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new Error("Missing port number in arguments.");
        }

        System.out.println("Starting a new HTTPServer...");
        HTTPServer server = new HTTPServer(Integer.parseInt(args[0]));
        server.start();
    }
}

class HTTPServer {
    int port;

    Socket socket = null;
    ServerSocket serverSocket = null;

    public DataOutputStream output = null;

    HTTPServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server has been started on port " + port);
        System.out.println("Waiting for connections...");

        socket = serverSocket.accept();
        System.out.println("Connection accepted");

        System.out.println("Creating an output stream...");
        output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        System.out.println("Flushing current output stream...");
        output.flush();

        System.out.println("Getting file contents of server_file.txt...");
        Scanner scan = new Scanner(new FileReader("server_file.txt"));
        String message = scan.nextLine();
        System.out.println("File contents: " + message);

        System.out.println("Writing to output stream");
        output.writeUTF(message);

        scan.close();
        System.out.println("Scanner closed");

        output.close();
        System.out.println("Output stream closed");

        socket.close();
        System.out.println("Connection closed");
    }
}
