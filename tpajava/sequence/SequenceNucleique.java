package tpajava.sequence;

public interface SequenceNucleique extends Sequence {
	Proteine traduction();
	Proteine traduction(boolean mitochondrial);
}
