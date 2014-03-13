import java.net.*;
import java.io.*;
import java.util.*;

public class Client
{
    private static final String GAMEOVER = "gameover";
    private static final String DELIM = ";";
    private static final String DUMMY = "dummy";

    public static void main(String[] args)
    {
        System.out.println("Client Initialized");
        try
        {
            //TODO: Change this to IP of server
            String ip = args.length == 0 ? "127.0.0.1" : args[0];
            Socket sock = new Socket(ip, 6013);

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
                    //System.out.println("\n\tSending message " + message);
                    serverOut.println(message);
                }
                else
                {
                    // Read the response
                    System.out.println("Waiting for response...");
                    String data = serverIn.nextLine();
                    
                    // Check if the game is over
					String[] parts = data.split(DELIM);
					if (parts.length > 1 && parts[0].equals(GAMEOVER))
					{
                        if (!parts[1].equals(DUMMY))
                        {
                            System.out.println(parts[1]);
                        }
						System.out.println("The answer was " + parts[2]);
						break;
					}

                    System.out.println(data);
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
