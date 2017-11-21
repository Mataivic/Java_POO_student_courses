package tp_Java;

public interface SequenceNucleique extends Sequence {
	Proteine traduction();
	Proteine traduction(boolean mitochondrial);
}
