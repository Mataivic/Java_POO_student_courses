/**
 * 
 */
package tpajava.sequence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author mataivic
 *
 */
public class EnsembleSequencesConcretes implements EnsembleSequences {

	private List<Sequence> list;
	private Map<String, Sequence> map; //map : correspondance entre objets
	
	public EnsembleSequencesConcretes() {
		list = new ArrayList<Sequence>();
		map = new HashMap<String, Sequence>();
	}
	
	/**
	 * @return an iterator on the sequence set
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Sequence> iterator() {		
		return list.iterator();
	}

	/**
	 * @return the number of sequences
	 * @see tpajava.sequence.EnsembleSequences#getNombre()
	 */
	@Override
	public int getNombre() {		
		return list.size();
	}

	/**
	 * @param i a sequence index(0 <= i <= getNombre() - 1)
	 * @return a sequence
	 * @see tpajava.sequence.EnsembleSequences#get(int)
	 */
	@Override
	public Sequence get(int i) {		
		return list.get(i);
	}

	/**
	 * @param idf the sequence idf
	 * @return the sequence with seq.getIdf() == idf
	 * @see tpajava.sequence.EnsembleSequences#get(java.lang.String)
	 */
	@Override
	public Sequence get(String idf) {		
		return map.get(idf);
	}

	/**
	 * Commentaire libre : adds a sequence to the set
	 * @param sequence a sequence
	 * @see tpajava.sequence.EnsembleSequences#add(tpajava.sequence.Sequence)
	 */
	@Override
	public void add(Sequence sequence) {
		if (sequence == null) {
			return;
		}
		list.add(sequence);
		map.put(sequence.getId(), sequence);
	}	
	
	/**
	 * @param reader input reader
	 * @return true if success
	 * @see tpajava.sequence.EnsembleSequences#read(java.io.BufferedReader)
	 */
	@Override
	public boolean read(BufferedReader reader) throws InvalidSequenceException {
		Sequence seq = new SequenceConcrete();
		int n = getNombre();
		while (seq.read(reader)) {
			Sequence sequence = new SequenceConcrete(seq.getId(), seq.getOrganism(), seq.getSequence());
			add(sequence);
		}
		return getNombre() - n > 0;
	} // A raccourcir

	/**
	 * @param writer output writer
	 * @see tpajava.sequence.EnsembleSequences#write(java.io.PrintWriter)
	 */
	@Override
	public void write(PrintWriter writer) {
		for (Sequence sequence : this) {
			sequence.write(writer);
		}
	}
	
	public boolean read(BufferedReader reader, Sequence seqType) throws InvalidSequenceException {
		Sequence seq = new SequenceConcrete();
		int n = getNombre();
		while (seqType.read(reader)) {
			Sequence sequence = seqType.getSameTypeSequence(seqType.getId(), seqType.getOrganism(), seqType.getSequence());
			add(sequence);
		}
		return getNombre() - n > 0;
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader in = new BufferedReader(new FileReader("proteine.fasta"));
			EnsembleSequences ensemble = new EnsembleSequencesConcretes();
			ensemble.read(in, new ProteineConcrete());
			for (Sequence seq:ensemble) {
				System.out.println(seq.getId()+" "+seq.length());
			}
			System.out.println(ensemble.getNombre());
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}