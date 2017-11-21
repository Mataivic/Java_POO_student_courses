package tp_Java;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;

public interface Sequence {
	String getId();
	
	String getOrganism();
	
	String getSequence();
	
	int length();
	
	char get(int pos); // 1<=pos<=length()
	
	boolean read(BufferedReader reader) throws InvalidSequenceException;
	
	Matcher matcher(String motif);
	
	boolean contains(String motif);
	
	String occurrence(String motif);
	
	String[] occurrences(String motif);
	
	String repetition();
	
	String repetition(int minSize);
	
	Sequence getSameTypeSequence(String id, String organisme,String sequence);
	
	void write(PrintWriter writer);
	
	Occurrence findFirst(Sequence sequence, int k, int delta, int scoreMin);
}
