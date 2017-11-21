package tp_Java;

import java.io.*;

public interface EnsembleSequences extends Iterable<Sequence> {
	int getNombre();
	Sequence get(int i);
	Sequence get(String idf);
	void add(Sequence sequence);
	boolean read(BufferedReader reader)throws InvalidSequenceException ;
	void write(PrintWriter writer);
	boolean read(BufferedReader reader, Sequence sequence) throws InvalidSequenceException;
}
