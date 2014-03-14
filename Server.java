// Authors: Lucas Kushner, Fayang Pan, Kyle Sunden
// Server of a networked 20 Questions application
// Last Modified: 3/14/14

import java.net.*;
import java.io.*;

public class Server
{
    private static final String GAMEOVER = "gameover";
    private static final String DELIM = ";";
    private static final String DUMMY = "dummy";

	public static void main(String[] args)
    {
        final int MAX_QUESTIONS = 20;

        int counter = 0;
		System.out.println("Server Initialized");

        try
        {
            // Initialize the server and all client inputs and outputs
            ServerSocket sock = new ServerSocket(6013);

            Socket answerClient = null;
            Socket questionClient = null;
            InputStreamReader aIn = null;
            InputStreamReader qIn = null;
            PrintWriter aOut = null;
            PrintWriter qOut = null;

            StringBuffer answer = new StringBuffer();

            // Connect to two players, first the answerer, then the questioner
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

                    // Tell the client that it will be answering
                    aOut.println("a");
					aOut.flush();

                    // Read the answer from the client
                    int word = aIn.read();
                    while(word!=(int)'\n')
                    {
                        char c = (char)word;
                        answer.append(c);
                        word = aIn.read();
                    }
                }
                else if (questionClient == null)
                {
                    qIn = new InputStreamReader(client.getInputStream());
                    qOut = new PrintWriter(client.getOutputStream(), true);
                    questionClient = client;

                    // Tell the client that it will be asking
                    qOut.println("q");
                    qOut.flush();
                }
            }

            System.out.println("Both clients connected, Let the games begin!");
            
            // Run for 20 questions
			while(counter < MAX_QUESTIONS)
            {
                // Read a question
                System.out.printf("Waiting for question...\n",counter);
                int data = qIn.read();
                while (qIn.ready())
                {
                    char c = (char)data;

                    aOut.print(c);
                    System.out.print(c);
                    data = qIn.read();
                }
                System.out.println();
                
                // Write to the anserer
                aOut.println();
                aOut.flush();

                // Read an answer
                System.out.println("Waiting for answer...");

                // Add to message when the game will end
                StringBuffer message = new StringBuffer();
                if (counter == MAX_QUESTIONS - 1)
                {
                    message.append(GAMEOVER + DELIM);
                }
                
                // Read the answer from the answerer
                data = aIn.read();
                while (aIn.ready())
                {
                    char c = (char)data;

                    message.append(c);
                    System.out.print(c);
                    data = aIn.read();
                }

                // Add the answer when the game has concluded
                if (counter == MAX_QUESTIONS - 1)
                {
                    message.append(DELIM + answer.toString());
                }

                // Write to the questioner
                qOut.println(message);
                qOut.flush();

				counter++;
            }

            // Write the gameover message to the answerer
			aOut.println(GAMEOVER + DELIM + DUMMY + DELIM + answer.toString());

            // Close connections
			answerClient.close();
			questionClient.close();
			sock.close();
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
