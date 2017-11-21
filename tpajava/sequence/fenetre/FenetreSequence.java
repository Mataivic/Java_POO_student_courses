package tpajava.sequence.fenetre;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import tpajava.sequence.*;

public class FenetreSequence extends JFrame {
	
	private JTextArea textArea;
	private JTextField motif;
	private EnsembleSequences sequencesCourantes; //stocke la derniere séquence lue
	private String [] types = {"Proteine", "ADN", "ARN", "Nucleique"};
	private Sequence [] sequencesTypes = {new ProteineConcrete(), new SequenceADNConcrete(), 
			new SequenceARNConcrete(), new SequenceNucleiqueConcrete()};
	private JComboBox<String> typesBox;
	private Sequence sequenceType;
	
	public FenetreSequence() {
		super("Fenêtre Séquence");
		setPreferredSize(new Dimension(600, 600));
		setLayout(new BorderLayout());
		
		textArea = new JTextArea(); //mettre "rien" affichera "rien"
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane, BorderLayout.CENTER); // on le met au centre - on le voit pas au début, il faut descendre	
		
		// ajout d'un panel au nord
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		add(panel, BorderLayout.NORTH);
		
		typesBox = new JComboBox<String>(types);
		panel.add(typesBox);
		typesBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionTypesSequences();
			}
		});
		typesBox.setSelectedIndex(0);
		sequenceType = sequencesTypes[0];
		
		// ajout d'un bouton ouvrir une séquence dans le panel nord
		JButton ouvrir = new JButton("Ouvrir");
		panel.add(ouvrir);
		// ajout d'une action pour le bouton + classe à la volée
		ouvrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("clic"); //ce que fait le bouton
				actionOuvrir();
			}
		});
		
		// ajout d'un bouton pour enregistrer
		JButton enregistrer = new JButton("Enregistrer");
		panel.add(enregistrer);
		// ajout d'une action pour le bouton + classe à la volée
		enregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("clic"); //ce que fait le bouton
				actionEnregistrer();
			}
		});
		
		// ajout d'un bouton de recherche de motif
		motif = new JTextField();
		motif.setPreferredSize(new Dimension(120, 30));
		JButton recherche = new JButton("Recherche");
		panel.add(motif);
		panel.add(recherche);
		recherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionRecherche();
			}
		});
		
		setVisible(true);		
		pack();		
	}	

	// ouverture d'un fichier, action envoyée par le bouton
	private void actionOuvrir(){
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(this); //this : la fenêtre même
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				BufferedReader in = new BufferedReader(new FileReader(file));
				EnsembleSequences sequences = new EnsembleSequencesConcretes();
				sequences.read(in, sequenceType);
				sequencesCourantes = sequences; // stocke la derniere sequence courante
				in.close();
				textArea.append(sequences.getNombre() + "\n"); //setText : efface et remplace, append : ajout à la suite
				for (Sequence sequence:sequences) {
					textArea.append(sequence.getId()+"\n");
				}
				textArea.append("\n");
			} catch(Exception e) {
				JOptionPane.showMessageDialog(this, "Invalid sequence type", "sequence error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void actionTypesSequences() {
		int selected = typesBox.getSelectedIndex();
		if (selected != -1) {
			sequenceType = sequencesTypes[selected];
		}
	}
	
	private void actionEnregistrer() {
		JFileChooser chooser = new JFileChooser(".");
		int returnVal = chooser.showSaveDialog(this); //this : la fenêtre même
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				PrintWriter out = new PrintWriter(file);
				out.write(textArea.getText());
				out.close();
			} catch (Exception e) {				
			}
		}
	}
	
	private void actionRecherche() {
		if (sequencesCourantes == null) {
			return;
		}
		String m = motif.getText().trim(); //trim enlève les espaces
		if (m.length() == 0) {
			return;
		}
		for (Sequence sequence:sequencesCourantes) {
			String occ = sequence.occurrence(m);
			if (occ != null) {
				textArea.append("Séquence : " + sequence.getId() + "\n");
				textArea.append("position: " + sequence.getSequence().indexOf(occ) + "\n");
				textArea.append("motif trouvé : " + occ + "\n\n");
			}
		}
		
	}
	
	private void setPreferedSize() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new FenetreSequence();

	}

}
