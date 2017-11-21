package tpajava.sequence;

import java.util.List;

public interface Proteine extends Sequence {
	List<Occurence> find(SequenceADN sequence, int k, int delta, int scoreMin);
}
