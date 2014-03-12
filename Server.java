import java.net.*;
import java.io.*;

public class Server
{
    public static void main(String[] args)
    {
        System.out.println("Server Initialized");
        try
        {
            ServerSocket sock = new ServerSocket(6013);

            Socket client = sock.accept();
            System.out.println("client accepted");

            InputStreamReader in = new InputStreamReader(
                    client.getInputStream());

            PrintWriter out = new PrintWriter(
                    client.getOutputStream(), true);

            while(true)
            {
                System.out.println("waiting...");
                // Read in message from the client
                int data = in.read();
                while (in.ready())
                {
                    char c = (char)data;

                    // Echo back the message
                    out.print(c);
                    System.out.print(c);
                    data = in.read();
                }
                
                out.println();
                out.flush();
            }
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
