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

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    sock.getInputStream()));

            Scanner serverIn = new Scanner(sock.getInputStream());

            Scanner userIn = new Scanner(System.in);

            System.out.print("Status Check: ");
            boolean turn = (char)in.read() == 'q' ? true : false;
            if (turn)
                System.out.println("You will be questioning");
            else
			{
				System.out.println("You will be answering");
				System.out.println("Please enter the word in your head");
				String answer = userIn.nextLine();
				System.out.printf("Your word is: %s\n",answer);
				serverOut.println(answer);
			}
            while (!sock.isClosed())
            {
                if (turn)
                {
                    System.out.println("Waiting for user input...");
                    String message = userIn.nextLine();

                    // Send the message
                    System.out.println("\n\tSending message " + message);
                    serverOut.println(message);
                }
                else
                {
                    // Read the response
                    System.out.println("Waiting for response...");
                    String data = serverIn.nextLine();
					String[] parts = data.split(";");
					if (parts[0].equals("gameover"))
					{
						System.out.println("The answer was " + parts[1]);
						break;
					}

                    System.out.println(data);
                    System.out.println("Response received!");
                }
                turn = !turn;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
