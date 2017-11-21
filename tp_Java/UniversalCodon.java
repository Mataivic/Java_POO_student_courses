package tp_Java;

public class UniversalCodon extends AbstractCodon implements Codon {
	
	final String UNIVERSAL_ACIDES_AMINES = "FFLLSSSSYY**CC*WLLLLPPPPHHQQRRRRIIIMTTTTNNKKSSRRVVVVAAAADDEEGGGG";

	public UniversalCodon() {
		setMap(UNIVERSAL_ACIDES_AMINES);
	}

	public static void main(String[] args) {
		UniversalCodon uc = new UniversalCodon();
		System.out.println(uc.getAcideAmine("TXT"));
	}

}
