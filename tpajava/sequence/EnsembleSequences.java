package tpajava.sequence;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface EnsembleSequences extends Iterable<Sequence> {

	int getNombre();
	
	Sequence get(int i);
	
	Sequence get(String idf);
	
	void add(Sequence sequence);
	
	boolean read(BufferedReader reader) throws InvalidSequenceException;
	
	boolean read(BufferedReader reader, Sequence sequence ) throws InvalidSequenceException;
	
	void write(PrintWriter writer);
	
}