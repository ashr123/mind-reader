package cs235.mindreader;

import java.util.HashMap;
import java.util.LinkedList;

public class Factory
{
	/**
	 * Creates and returns an object that implements the MindReader interface
	 *
	 * @return A new object that implements the MindReader interface
	 */
	public static MindReader createMindReader()
	{
		return new MindReaderImpl(new HashMap<>(), new LinkedList<>(), 0, 0);
	}
}
