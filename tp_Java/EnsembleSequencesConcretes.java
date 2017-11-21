package tp_Java;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class EnsembleSequencesConcretes implements EnsembleSequences {

	private List<Sequence> list;
	private Map<String, Sequence> map;
	
	public EnsembleSequencesConcretes(){
		list = new ArrayList<Sequence>();
		map = new HashMap<String, Sequence>();
	}
	
	@Override
	public Iterator<Sequence> iterator() {
		return list.iterator();
	}

	@Override
	public int getNombre() {
		return list.size();
	}

	/**
	 * @param i a sequence index (0 <= i <= getNombre-1)
	 * @return a sequence
	 */
	
	@Override
	public Sequence get(int i) {
		return list.get(i);
	}

	@Override
	public Sequence get(String idf) {
		return map.get(idf);
	}

	@Override
	public void add(Sequence sequence) {
		if(sequence == null){
			return;
		}
		list.add(sequence);
		map.put(sequence.getId(), sequence);
	}

	@Override
	public boolean read(BufferedReader reader) throws InvalidSequenceException {
		return read(reader, new SequenceConcrete());
	}
	
	@Override
	public boolean read(BufferedReader reader, Sequence seqType)throws InvalidSequenceException {
		int n = getNombre();
		while (seqType.read(reader)){
			Sequence sequence = seqType.getSameTypeSequence(seqType.getId(), seqType.getOrganism(), seqType.getSequence());
			add(sequence);
		}
		return getNombre() - n > 0;
	}

	
	
	@Override
	public void write(PrintWriter writer) {
		for (Sequence sequence:this){
			sequence.write(writer);
		}
	}

	public static void main(String[] args) {
		try{
			BufferedReader in = new BufferedReader(new FileReader("sequences.fasta"));
			EnsembleSequences ensemble = new EnsembleSequencesConcretes();
			ensemble.read(in, new ProteineConcrete());
			for (Sequence seq:ensemble){
				System.out.println(seq.getId() + " " + seq.length());
			}
			System.out.println(ensemble.getNombre());
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
}
