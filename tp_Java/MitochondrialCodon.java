package tp_Java;

public class MitochondrialCodon extends AbstractCodon {
	
	final String MITOCHONDRIAL_ACIDES_AMINES = "FFLLSSSSYY**CCWWLLLLPPPPHHQQRRRRIIMMTTTTNNKKSS**VVVVAAAADDEEGGGG";
	
	public MitochondrialCodon() {
		setMap(MITOCHONDRIAL_ACIDES_AMINES);
	}

	public static void main(String[] args) {
		MitochondrialCodon mc = new MitochondrialCodon();
		System.out.println(mc.getAcideAmine("TXT"));

	}

}
