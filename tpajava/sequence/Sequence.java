package tpajava.sequence;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;

public interface Sequence {
	String getId();	
	String getOrganism();	
	String getSequence();	
	int length();	
	char get(int pos); // 1 <= pos <= length()	
	boolean read(BufferedReader reader) throws InvalidSequenceException; // besoin d'import BufferedReader	
	void write(PrintWriter writer);
	Matcher matcher(String motif);
	public boolean contains(String motif);
	public String occurrence(String motif);
	public String[] occurrences(String motif);
	public String repetition();
	public String repetition(int minSize);
	Sequence getSameTypeSequence(String id, String organisme, String sequence);
	public Occurence findFirst(Sequence sequence, int k, int delta, int scoreMin);
	public List<Occurence> find(Sequence sequence, int k, int delta, int scoreMin);
	
}