package cs235.mindreader;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MindReaderImpl implements MindReader
{
	final static int THREE = 3;
	final static int FOUR = 4;
	final static int FIVE = 5;
	private final List<String> list;
	private final Map<List<String>, Counter> map;
	private int mindReaderScore = 0;
	private int playerScore = 0;

	/**
	 * Constructs a new MindReaderImpl Object with a map, list, and default starting scores.
	 *
	 * @param map
	 * @param list
	 * @param mindReaderScore
	 * @param playerScore
	 */
	public MindReaderImpl(Map<List<String>, Counter> map, List<String> list, int mindReaderScore,
	                      int playerScore)
	{
		this.map = map;
		this.list = list;
		this.mindReaderScore = mindReaderScore;
		this.playerScore = playerScore;
	}

	/**
	 * Saves the player's current list of choices in a file called "profile.txt".<br>
	 * This is formatted with a list of 4 choice sequences followed by the player's choices of heads or tails
	 *
	 * @param filename the name of the file in which to store the profile
	 * @return
	 */
	public boolean savePlayerProfile(String filename) throws IOException
	{
		try (PrintWriter out = new PrintWriter(new FileWriter(filename)))
		{
			final StringBuilder line = new StringBuilder().append(map.size()).append('\n');
			for (List<String> strings : map.keySet())
			{
				for (String s : strings)
					line.append(s).append(' ');
				Counter c = map.get(strings);
				line.append(c.getHeads()).append(' ').append(c.getTails()).append('\n');
			}
			out.print(line);
		}
		System.out.println("File saved successfully");
		return true;
	}

	/**
	 * Loads the player's "profile.txt" file and updates the map with it
	 *
	 * @param filename the name of the file to be loaded
	 * @return
	 */
	public boolean loadPlayerProfile(String filename) throws FileNotFoundException
	{
		File file = new File(filename);
		int numLines;
		try (Scanner in = new Scanner(file))
		{
			if (in.hasNext())
				numLines = Integer.parseInt(in.nextLine());
			else
				return false;

			for (int i = 0; i < numLines; i++)
			{
				List<String> tempList = new LinkedList<>();
				tempList.add(in.next());
				tempList.add(in.next());
				tempList.add(in.next());
				tempList.add(in.next());
				map.put(tempList, new Counter(in.nextInt(), in.nextInt()));
			}
			return true;
		}
	}

	/**
	 * Ensures only tails and heads as choices. Also compares the player's
	 * choice with the mindreader's prediction and increments the appropriate score
	 *
	 * @param choice the string "heads" if the choice is heads,
	 *               or the string "tails" if the choice is tails
	 */
	public void makeChoice(String choice)
	{
		if (choice.equals("heads") || choice.equals("tails"))
			if (choice.equals(getPrediction()))
				mindReaderScore++;
			else
				playerScore++;
		else
			throw new IllegalArgumentException("can be 'heads' or 'tails' only");

		if (choice.equals("heads"))
			list.add("heads");
		if (choice.equals("tails"))
			list.add("tails");
		addEntry();
	}

	/**
	 * If the list is 5 elements long, this calls entryDetermination and removes the last element of the list
	 *
	 * @return
	 */
	public List<String> addEntry()
	{
		if (list.size() != FIVE)
			return null;
		else
		{
			List<String> newList = new LinkedList<>(list);
			newList.remove(FOUR);

			entryDetermination(newList);
		}
		list.remove(0);
		return list;
	}

	/**
	 * Determines wether to increment an existing counter for a list or create a new entry in the map
	 *
	 * @param newList
	 */
	public void entryDetermination(List<String> newList)
	{
		// If the map already contains the key, increment the correct
		// counter
		if (map.containsKey(newList))
		{
			Counter tempCounter = map.get(newList);
			if (list.get(FOUR).equals("heads"))
				tempCounter.setHeads(tempCounter.getHeads() + 1);
			else
				tempCounter.setTails(tempCounter.getTails() + 1);
			map.put(newList, tempCounter);
		}

		// If the map doesn't contain the key, create a new one with
		// associated value (counter)
		else
		{
			Counter counter = new Counter(0, 0);
			if (list.get(FOUR).equals("heads"))
				counter.setHeads(1);
			else
				counter.setTails(1);
			map.put(newList, counter);
		}
	}

	/**
	 * If the player's choice sequence is stored in the map and if the tails counter is greater than the heads counter
	 * for that sequence, predict tails Otherwise predict heads
	 *
	 * @return
	 */
	public String getPrediction()
	{
		if (map.containsKey(list))
		{
			Counter tempCounter = map.get(list);
			if (tempCounter.getTails() > tempCounter.getHeads())
				return "tails";
		}
		return "heads";
	}

	/**
	 * Returns the player's score
	 *
	 * @return
	 */
	public int getPlayerScore()
	{
		return playerScore;
	}

	/**
	 * Returns the mindreader's score
	 *
	 * @return
	 */
	public int getMindReaderScore()
	{
		return mindReaderScore;
	}
}
