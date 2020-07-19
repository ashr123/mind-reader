package cs235.mindreader;

import java.io.IOException;
import java.util.Scanner;

public class MindReaderGame
{
	public static void main(String... args) throws IOException
	{
		MindReader game = Factory.createMindReader();
		game.loadPlayerProfile("profile.txt");
		try (Scanner in = new Scanner(System.in))
		{
			printWelcome();
			while (true)
			{
				String prediction = game.getPrediction();
				String choice = readChoice(in);
				if (choice.equals("quit"))
					break;
				game.makeChoice(choice);
				printResult(prediction, choice);
				printScores(game);
			}
			game.savePlayerProfile("profile.txt");
		}
	}

	static void printWelcome()
	{
		System.out.println("\nWelcome to Mind Reader.\n");
	}

	static String readChoice(Scanner in)
	{
		String choice = null;
		while (true)
		{
			System.out.println("Please choose heads or tails.");
			System.out.print("What is your choice [h/t or quit]? ");
			String s = in.next();
			System.out.println();

			s = s.toLowerCase();
			if (s.startsWith("h"))
				choice = "heads";
			else if (s.startsWith("t"))
				choice = "tails";
			else if (s.startsWith("q"))
				choice = "quit";

			if (choice != null)
				break;
			System.out.println("Invalid choice.");
		}
		return choice;
	}

	static void printResult(String prediction, String choice)
	{
		System.out.println("My prediction was " + prediction + ".\nYour choice was " + choice + ".");
		if (prediction.equals(choice))
			System.out.println("I win!");
		else
			System.out.println("You win!");
		System.out.println();
	}

	static void printScores(MindReader game)
	{
		System.out.println("Your score: " + game.getPlayerScore() + "\nMy  score: " + game.getMindReaderScore());
	}
}
