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

            Socket answerClient = null;
            Socket questionClient = null;
            InputStreamReader aIn = null;
            InputStreamReader qIn = null;
            PrintWriter aOut = null;
            PrintWriter qOut = null;

            while(answerClient == null || questionClient == null)
            {
                System.out.println("waiting...");

                Socket client = sock.accept();
                System.out.println("client accepted");

                if (answerClient == null)
                {
                    aIn = new InputStreamReader(client.getInputStream());
                    aOut = new PrintWriter(client.getOutputStream(), true);
                    answerClient = client;

                    aOut.println("a");
                }
                else if (questionClient == null)
                {
                    qIn = new InputStreamReader(client.getInputStream());
                    qOut = new PrintWriter(client.getOutputStream(), true);
                    questionClient = client;

                    qOut.println("q");
                    qOut.flush();
                }
            }

            System.out.println("Both clients connected, Let the games begin!");
            while(true)
            {
                System.out.println("Waiting for question...");
                int data = qIn.read();
                while (qIn.ready())
                {
                    char c = (char)data;

                    aOut.print(c);
                    System.out.print(c);
                    data = qIn.read();
                }
                System.out.println();
                
                aOut.println();
                aOut.flush();

                System.out.println("Waiting for answer...");
                data = aIn.read();
                while (aIn.ready())
                {
                    char c = (char)data;

                    qOut.print(c);
                    System.out.print(c);
                    data = aIn.read();
                }
                System.out.println();
                
                qOut.println();
                qOut.flush();
            }
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
