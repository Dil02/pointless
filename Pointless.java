// Pointless Quiz

/** Program Credentials:

 @Author	Dilkush Punja
 @Date		29 November 2020
 @Version	7.0

The purpose of the program is to resemble the Pointless game show where the player is 
awarded points based on the most correct unique answer. The aim is to choose an answer 
that is correct but is the least popular response. To win the game you need to gain as 
few points as possible.
**/

import java.util.Scanner; // Import the scanner class
import java.util.Random; // Import the random number class
import java.io.*; // Import file input and output class


/** Below is a record called 'Data' which includes fields containing questions, possible 
answers and their scores.
**/

class Data
{
	String questions;
	String [] potentialAnswers;
	int [] answerScores;
}

class Player
{
	int numberOfPlayers;
	String [] playerNames;
	int [] playerScores;
}

// Below the class 'Pointless' has been defined. It contains all the programs methods. 
public class Pointless
{

/** The main method uses function and procedure calls to ask the user set questions in a
random order. The method prints suitable answers and the user is prompted to enter
their "pointless" answer.
**/
public static void main(String[] args) throws IOException
{

// Below is the start menu:
//***************************************************************************
print("Welcome to Pointless!");
String response=inputString("Would you like to start? Enter Y/N");

String filename="Random.csv";

if(response.equals("N") || response.equals("n"))
{
	print("It looks like you do not want to play Pointless!");
	print("Goodbye!");
	System.exit(0);
}
else if(response.equals("TARDIS"))   // If the user enters 'TARDIS' they are given access to admin mode.
{
	print("Your administrator options are below:");
	print("A- Create new questions");
	print("B- Enter a filename");
	print("C- Play Pointless!");
	String response2=inputString("");
	
	if(response2.equals("A"))
	{
		filename=designQuestions();
	}
	else if (response2.equals("B"))
	{
		filename=inputString("Please enter a file name, remember to include '.csv'.");
	}
	else if (response2.equals("C"))
	{
		
	}
	else 
	{
		System.exit(0);
	}
	
}

//***************************************************************************

/** Declaration of the variable 'question1' of the 'Data' type. Values are passed to 
function as an argument in the function call.
**/ 	
	Random randomQuestion = new Random(); // Variable of the Random type is created.
	int score=0;
	int totalScore=0;
	final int numberOfQuestions=2;
	
	Player players=createPlayers();
	
	for (int k=1; k<=getNumberOfPlayers(players); k++)
	{
	
		for (int i=1; i<=numberOfQuestions; i++) // Counter controlled for loop is used to ask multiple questions.
		{
			int questionNumber = randomQuestion.nextInt(5)+0; // The variable 'questionNumber' is assigned a randomly generated number to randomise the order of questions.
			Data question=initialisation(questionNumber,filename);
			print("\n" + getPlayerNames(players,k)+ ", here is question number " + i + ":");
			print(getQuestion(question));
		
			print("\n"+"Your options are below:" + "\n"); // The 4 potential answers are printed.
		
				for (int j=1; j<=4; j++)
				{
					print(getPotentialAns(question,j));	
				}
			
			print("\n");
			String answer=inputAnswer();
			score = marking(question, answer); // This variable contains the score awarded to the player's answer.
			print("\n"+"You received " + score + " points."); // The user's score is printed.
			print("");
			totalScore=totalScore + score; // This variable contains the player's total score.
		
		
		}
		
		setPlayerScores(players,k,totalScore); // The player's total score is saved in the player record.
		totalScore=0;
	}
	
	printPlayerScores(players);
/** An exit command has been used below to quit the program after the instructions within
the main method have been completed.
**/
	System.exit(0);
}



//*************************************************************************************

//Primitive Operations-'Data' Abstract Data Type:

/**The 'Data' abstract data type includes operations such as:
 Returning records of the 'Data' type.
 Creating records of the 'Data' type.
 Accessing the question field of a question record.
 Accessing the potentialAnswers field of a question record.
 Accessing the answerScores field of a question record.
**/

/** This function accesses the csv file from the given filename and is used to create an
array of question records. Once the records have been made, the function returns a record
based on the randomly generated number passed to it.
**/

public static Data initialisation(int questionNumber,String filename) throws IOException
{
	Data [] qBank= new Data [6]; // An array of question records.
	
	BufferedReader inputStream = new BufferedReader (new FileReader(filename));
	for (int i=0; i<qBank.length; i++)
	{
		
		String question= inputStream.readLine();
	
		String s= inputStream.readLine();
		String [] answers=s.split(",");
		
		s= inputStream.readLine();
		
		String [] scoresInString=s.split(",");
		
		int [] answerScores= new int [4];
		
		for(int j=0; j<4; j++)
		{
			answerScores[j]=Integer.parseInt(scoresInString[j]);
		}
				
		qBank[i]=createQuestion(question,answers,answerScores);
	}
	
	inputStream.close();
	return qBank[questionNumber];
}

// This function is used to create a record for a question. It is passed array arguments.
public static Data createQuestion(String question, String possibleAnswers [], int answerPoints []) // Arguments passed to the method in the function call are used to assign values in the appropriate array.
{
	Data x = new Data();
		
	x.questions = question;
	x.potentialAnswers = possibleAnswers;
	x.answerScores = answerPoints;
	
	return x;	
}

//*************************************************************************************

// This function is used to 'mark' the user's response.
public static int marking(Data question, String answer)
{	
	int score=0;

/** If the user's response matches a answer in the record they are given the score
associated with that answer.
**/
	
	if (answer.equals(getPotentialAns(question,1))) /** Boolean expression used to verify 
										the user's answer.**/
		{
			score=getAnswerScores(question,1);
		}
	
	else if (answer.equals(getPotentialAns(question,2)))
		{
			score=getAnswerScores(question,2);
		}
	
	else if (answer.equals(getPotentialAns(question,3)))
		{
			score=getAnswerScores(question,3);
		}
	
	else if (answer.equals(getPotentialAns(question,4)))
		{
			score=getAnswerScores(question,4);
		}
	
	else	
		{
			score=100; // If the given answer is incorrect the user's score is set to 100.
			print("Unfortunately your response was not a correct answer.");
			
			return score; // The score is returned to the function call.
		}
	
	if (score==0) // If a users enters a pointless answer they are awarded 0 points.
	{
		print("Congratulations, you entered a Pointless answer!");
	}
		
	return score; // The score is returned to the function call.
}

//*************************************************************************************

// Primitive Operations-'Player' Abstract Data Type:

/** Below are the set of primitive operations which will be performed on the Player data 
type. The operations include:

Firstly, creating a player which involves: Storing the number of players and setting the name of players.
 
 Getting the number of players.
 Getting the name of players.
 
 Setting player scores.
 Getting player scores.
 
 Ordering the array containing the player scores from lowest to highest.

These accessor methods are the only way for the rest of the program to access values
in the array fields. **/

// This function is used to create a record for player information.
public static Player createPlayers()
{
	Player players=new Player(); // A new variable called 'player' is declared of the Player type.
	
	players.numberOfPlayers=inputInt("Please enter the number of players.");
	
	if (players.numberOfPlayers <1) // Input validation
	{
		print("\n" + "It seems like you do not want to play Pointless!");
		print("Goodbye!");
		System.exit(0);	
	}
	
	players.playerNames= new String [players.numberOfPlayers];
	players.playerScores= new int [players.numberOfPlayers];

/** The counter controlled loop below is used to allow players to enter their names, the 
loop is repeated until all players have done so. **/	
	for (int i=0; i<=players.numberOfPlayers-1; i++)
	{
		players.playerNames[i]=inputString("Enter player " + (i+1) + "'s name.");	
	}
	
	return players;
}

// This function is an accessor method to access the total number of players.
public static int getNumberOfPlayers(Player players)
{
	return players.numberOfPlayers;
}

// This function is an accessor method to access a player's name.
public static String getPlayerNames(Player players, int playerNumber)
{
	playerNumber=playerNumber-1;
	return players.playerNames[playerNumber];
}

// This function is an accessor method to access a player's total score.
public static int getPlayerScores(Player players, int playerNumber)
{
	playerNumber=playerNumber-1;
	return players.playerScores[playerNumber];
}

// This function stores a player's total score into the array in the record player.
public static Player setPlayerScores(Player players, int playerNumber, int score)
{
	playerNumber=playerNumber-1;
	players.playerScores[playerNumber]=score;
	
	return players;
}

/** This function uses 'bubble sort' to order player scores so that they can be printed
in a leaderboard, the scores are arranged from low to high within the playerScores array.
**/
public static Player arraySort(Player players)
{
	for (int pass=1; pass <=getNumberOfPlayers(players)-1; pass++)
	{
		for (int i=0; i< getNumberOfPlayers(players)-pass; i++)
		{
			if (players.playerScores[i] > players.playerScores[i+1])
			{
					int tmp1= players.playerScores[i+1];
					players.playerScores[i+1]=players.playerScores[i];
					players.playerScores[i]= tmp1;
					
					String tmp2=players.playerNames[i+1];
					players.playerNames[i+1]=players.playerNames[i];
					players.playerNames[i]=tmp2;
			}
		}
	}
	
	return players;
}

//*************************************************************************************

// Primitive Operations-'Data' Abstract Data Type:

/**Below are the set of primitive operations which will be performed on the 'Data' data type.
The operations include, getting a question, potential answers and answer scores. **/

// This function is an accessor method to access a question stored in the question record.
public static String getQuestion(Data question)
{
	return question.questions;
}

// This function is an accessor method to access appropriate answers to a particular question.
public static String getPotentialAns(Data question, int ansNumber)
{
	ansNumber=ansNumber-1;
	
	return question.potentialAnswers[ansNumber];
}

// This function is an accessor method to access answer scores to a particular question.
public static int getAnswerScores(Data question, int scoreNumber)
{
	scoreNumber=scoreNumber-1;
	return question.answerScores[scoreNumber];
}

//*************************************************************************************

// This procedure is used to print out the scores for each player at the end.
public static void printPlayerScores(Player players)
{
	print("\n" + "Pointless Leaderboard: \n");
	arraySort(players);
	
	for (int j=1; j<=getNumberOfPlayers(players); j++)
	{
		print(getPlayerNames(players,j)+ " " + getPlayerScores(players,j));
	}
}

/** This function enables the user to enter their own questions and answers which are
saved in a csv file. The filename is then returned so subsequent methods can 
access the file and print accordingly.
**/
public static String designQuestions() throws IOException
{
	
	String filename=inputString("What topic would you like the questions to be on?")+ ".csv";
	PrintWriter outputStream= new PrintWriter(new FileWriter(filename));
		
	for(int i=1; i<=6; i++)
	{	
		outputStream.println(inputString("\nPlease enter a question you would like to ask."));

		outputStream.println(inputString("Enter the suitable answers. In this format: Blue,Red,Green,Yellow"));
		outputStream.println(inputString("Please assign the answers a score. In this format: 34,76,23,41"));
	}
	outputStream.close();
	
	return filename;
}


// This function is used to enable a user to enter their answer.
public static String inputAnswer()
{
	String text_input;
	Scanner scanner= new Scanner(System.in);
	
	text_input=scanner.nextLine();
	
	return text_input;
}

// This function enables the user to enter String values.
public static String inputString(String message)
{
	String text_input;
	Scanner scanner= new Scanner(System.in);
	
	System.out.println(message);
	text_input=scanner.nextLine();
	
	return text_input;
}

// This function enables the user to enter integer values.
public static int inputInt(String message)
{
	System.out.println(message);
	String text_input=inputAnswer();
	int x=Integer.parseInt(text_input);
	
	return x;
}

// This procedure uses abstraction, to simplify the use of 'System.out.println'.
public static void print(String message)
{
	System.out.println(message);
	return;
	
}

} // End of the 'Pointless' class.
