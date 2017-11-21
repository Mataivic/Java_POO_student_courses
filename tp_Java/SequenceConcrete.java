package tp_Java;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SequenceConcrete implements Sequence {

	private String id;
	private String organism;
	private String sequence;
	
	public static int TAILLE_LIGNE = 60;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getOrganism() {
		return organism;
	}

	@Override
	public String getSequence() {
		return sequence;
	}

	@Override
	public int length() {
		return sequence.length();
	}

	@Override
	public char get(int pos) {
		return sequence.charAt(pos-1);
	}

	@Override
	public boolean read(BufferedReader reader) throws InvalidSequenceException{
		return rawRead(reader);
	}
	
	protected boolean rawRead(BufferedReader reader){
		try {
			String annotations = reader.readLine();
			String [] annot = annotations.split("\\|");
			id = annot[3];
			organism = annot[4];
			StringBuilder seq = new StringBuilder();
			String line = null;
			while ((line=reader.readLine()) != null && line.trim().length()>0){
				seq.append(line.trim().replaceAll(" ", ""));
			}
			sequence = seq.toString();
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
	private static boolean isValid(String sequence, String alphabet){
		int len = sequence.length();
		for (int i=0; i < len; i++){
			char c =sequence.charAt(i);
			if (alphabet.indexOf(c)<0){
				return false;
			}
		}
		return true;
	}
	
	protected boolean validatedRead(BufferedReader reader, String alphabet) throws InvalidSequenceException{
		boolean ret = rawRead(reader);
		if (! isValid(getSequence(),alphabet)){
			throw new InvalidSequenceException("invalid sequence");
		}
		return ret;
	}
	
	public Matcher matcher(String motif){
		Pattern p = Pattern.compile(motif);
		return p.matcher(getSequence());
	}
	
	public boolean contains(String motif){
		return matcher(motif).find();
	}
	
	public String occurrence(String motif){
		Matcher matcher = matcher(motif);
		if (matcher.find()){
			return matcher.group(0);
		}
		else{
			return null;
		}
		
	}
	
	@Override
	public String[] occurrences(String motif) {
		Pattern p = Pattern.compile(motif);
		List<String> tab = new ArrayList<String>();
		int end = -1;
		boolean fin = false;
		while (!fin){
			String chaine = getSequence().substring(end+1);
			Matcher m = p.matcher(chaine);
			if (m.find()){
				String occ = m.group(0);
				tab.add(occ);
				end += m.end();
			}else{
				fin = true;
			}
		}
		return tab.toArray(new String[0]);
	}
	
	@Override
	public String repetition() {
		Matcher matcher = matcher("(.{3,}).*\\1");
		if (matcher.find()){
			return matcher.group(1);
		}
		else{
			return null;
		}
	}
	
	@Override
	public String repetition(int minSize) {
		Matcher matcher = matcher("(.{"+minSize+",}?).*\\1");
		if (matcher.find()){
			return matcher.group(1);
		}
		else{
			return null;
		}
	}
	
	@Override
	public void write(PrintWriter writer) {
		try{
			String annotations = ">|||" + getId() + "|" + getOrganism(); 
			writer.println(annotations);
			String seq = getSequence();
			int len = seq.length();
			for (int i=0; i < len; i+=TAILLE_LIGNE){
				int end = Math.min(i+TAILLE_LIGNE, len);
				writer.println(seq.substring(i, end));
			}
			writer.println();
		}
		catch (Exception e){
			return;
		}

	}
	
	public Sequence getSameTypeSequence(String id, String organisme, String sequence) {
		return new SequenceConcrete(id, organisme, sequence);
	}


	public SequenceConcrete(){
		
	}
	
	public SequenceConcrete(String idf, String organism, String sequence){
		id=idf;
		this.organism=organism;
		this.sequence=sequence;
	}
	
	@Override
	public Occurrence findFirst(Sequence sequence, int k, int delta, int scoreMin) {
		KMers kmers = new KMersConcrets(k, sequence);
		String chaine = getSequence();
		int len = length();
		int lp = sequence.length();
		int last_i = len - (lp-delta);
		int min_l = lp + delta ;
		int max_l = Math.min(lp+delta, len);
		for (int i=0; i < last_i; i++){
			for (int l=min_l; l <= max_l && i+l < len ; l++) {
				String sousChaine = chaine.substring(i, i+l);
				KMers kmersSousChaine = new KMersConcrets (k, sousChaine);
				int score = kmers.nombreCommuns(kmersSousChaine);
				if (score >= scoreMin){
					Occurrence occ = new Occurrence();
					occ.start = i;
					occ.end = i+l;
					return occ; 
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		/*try{	
			EnsembleSequences sequences = new EnsembleSequencesConcretes();
			BufferedReader reader = new BufferedReader(new FileReader("proteines.fasta"));
			sequences.read(reader);
			reader.close();
			//PrintWriter writer = new PrintWriter( new FileWriter("out.fasta"));
			//sequence.write(writer);
			//writer.close();
			
			//for (int i=1; i<=sequence.length();i++){
				//System.out.print (sequence.get(i));
			//}
			//String[] tab = sequence.occurrences("L[IY]TT");
			//System.out.println(Arrays.toString(tab));
			//System.out.println(sequence.repetition(1));
			//String ps23 = "C..PF.[FYWIV].{7}C.{8,10}WC.{4}[DNSR][FYW].{3,5}[FYW].[FYWI]C";
			//for (Sequence sequence:sequences){
				//System.out.println(sequence.getId() + " motif : " + sequence.occurrence(ps23));
			//}
			
		}catch (Exception e){
			System.err.println("Impossible d'ouvrir le fichier");
		}*/
		Sequence seq = new SequenceConcrete("","","ATGCCATTCGACGATCAGGGACAT");
		Sequence requete = new SequenceConcrete("","","TCGGCGA");
		
		Occurrence occ = seq.findFirst(requete, 2, 1, 4);
		if (occ !=null){
			System.out.println(occ.start+" "+occ.end+" "+seq.getSequence().substring(occ.start, occ.end));
		}
		
		
	}
}
