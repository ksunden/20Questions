// Authors: Lucas Kushner, Fayang Pan, Kyle Sunden
// Client for a networked two player 20 Questions game
// Last modified: 3/14/2014

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
            // Connect to the server
            String ip = args.length == 0 ? "127.0.0.1" : args[0];
            Socket sock = new Socket(ip, 6013);

            // Sending messages to the server
            PrintWriter serverOut = new PrintWriter( 
                    sock.getOutputStream(), true);

            // Recieving data from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            Scanner serverIn = new Scanner(sock.getInputStream());

            // Recieving data from the user
            Scanner userIn = new Scanner(System.in);

            // Determine whether this client is asking or answering questions
            System.out.print("Status Check: ");
            boolean turn = (char)in.read() == 'q' ? true : false;
            if (turn)
                System.out.println("You will be questioning");
            else
			{
                // Get the correc anwer from the answerer
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
                    // Get string from user
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
                // Change turns
                turn = !turn;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
