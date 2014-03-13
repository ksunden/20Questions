import java.net.*;
import java.io.*;

public class Server
{
	public static void main(String[] args)
    {
        final int MAX_QUESTIONS = 2;
        int counter = 0;
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
					aOut.flush();
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
			StringBuffer answer = new StringBuffer();
			int word = aIn.read();
			while(word!=(int)'\n')
			{
				char c = (char)word;
				answer.append(c);
				word = aIn.read();
			}
            
			while(counter < MAX_QUESTIONS)
            {
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
                
                aOut.println();
                aOut.flush();

                System.out.println("Waiting for answer...");

                StringBuffer message = new StringBuffer();
                if (counter == MAX_QUESTIONS - 1)
                {
                    message.append("gameover;");
                }

                data = aIn.read();
                while (aIn.ready())
                {
                    char c = (char)data;

                    message.append(c);
                    System.out.print(c);
                    data = aIn.read();
                }

                if (counter == MAX_QUESTIONS - 1)
                {
                    message.append(";" + answer.toString());
                }

                
                qOut.println(message);
                qOut.flush();

				counter++;
            }

			aOut.println("gameover;" + answer.toString());

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
