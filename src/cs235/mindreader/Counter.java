package cs235.mindreader;

public class Counter
{
	private int heads;
	private int tails;

	public Counter(int heads, int tails)
	{
		setHeads(heads);
		setTails(tails);
	}

	public int getHeads()
	{
		return heads;
	}

	public void setHeads(int heads)
	{
		if (heads >= 0)
			this.heads = heads;
		else
			throw new IllegalArgumentException("Must be a positive number");
	}

	public int getTails()
	{
		return tails;
	}

	public void setTails(int tails)
	{
		if (tails >= 0)
			this.tails = tails;
		else
			throw new IllegalArgumentException("Must be a positive number");
	}

	public String toString()
	{
		return this.getHeads() + " " + this.getTails();
	}
}
