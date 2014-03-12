import java.net.*;
import java.io.*;
import java.util.*;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println("Client Initialized");
        try
        {
            //TODO: Change this to IP of server
            Socket sock = new Socket("127.0.0.1", 6013);

            PrintWriter serverOut = new PrintWriter(
                        sock.getOutputStream(), true);

            InputStreamReader in = new InputStreamReader(
                    sock.getInputStream());

            Scanner scan = new Scanner(System.in);

            while (true)
            {
                System.out.println("Waiting for user input...");
                String message = scan.nextLine();

                // Send the message
                System.out.println("\nSending message " + message);
                serverOut.println(message);

                // Read the response
                System.out.print("Response: ");
                int data = in.read();
                while (data != -1)
                {
                    char c = (char)data;
                    System.out.print(c);
                    if (c == '\n')
                        break;
                    data = in.read();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static enum ClientType
    {
        ANSWER, QUESTION
    }
}
