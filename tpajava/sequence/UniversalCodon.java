package tpajava.sequence;

public class UniversalCodon extends AbstractCodon { //pas besoin de implements codon car déjà fait par AbstractCodon
	
	final String UNIVERSAL_ACIDES_AMINES = "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG";

	public UniversalCodon() {
		setMap(UNIVERSAL_ACIDES_AMINES);
	}

	public static void main(String[] args) {
		UniversalCodon uc = new UniversalCodon();
		System.out.println(uc.getAcideAmine("ttt"));
	}
}