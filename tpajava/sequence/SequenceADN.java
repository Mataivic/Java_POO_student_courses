package tpajava.sequence;

import java.util.List;

public interface SequenceADN extends SequenceNucleique {
	SequenceARN transcript();
	public List<Occurence> find(Proteine sequence, int k, int delta, int scoreMin);

}