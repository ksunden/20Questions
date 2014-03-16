Hi! Welcome to 20 Questions!

This is a classic 20 Questions game, and here are the instructions for installation:

1. Run the server with any computer.
2. Run the clients. If the client runs on the server, just run the client without any additional arguments. If the client is run on another computer, an additional argument of the IP address of the server is required. For instance, from another with IP address 123.45.6.78, the command may be like "java server 123.456.7.89"


Here are the instructions for playing the game:

1. The first client that gets connected to the server provides the word and answers all questions.
2. The second client that gets connected to the server asks 20 questions.
3. The server displays the answer, and stops itself and both clients after 20 questions.
4. If its your turn to input anything, you will see "Waiting for user input...".
5. If it's not your turn to input anything, you will see "Waiting for response...".


Known limitations:
1. There is no restrictions on what the questions or answers are. 
2. There is no scheme to check the answer before all 20 questions are asked.
3. There is no way to choose to be questioning or answering.
4. The server and clients have to be manually restarted have each session.
5. There is no way to prevent a user from typing multiple lines. The contents on the second line will be the user input for the next round.


By Lucas Kushner, Fayang Pan, and Kyle Sunden.
A project for COMP 430 Operating Systems and Networking, Winter 2014, Kalamazoo College.
3/14/14.
