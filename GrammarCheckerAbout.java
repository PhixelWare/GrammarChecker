import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Font;
import java.awt.Cursor;


public class GrammarCheckerAbout extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GrammarCheckerAbout dialog = new GrammarCheckerAbout();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GrammarCheckerAbout() {
		setTitle("Grammar Checker About");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		
		
		String strAboutLabel = new String();										//specify row label text
		strAboutLabel = "Grammar Checker   " +
				"\nv1.0.0" +
				"\nSY: 2015~2016" +
				"\n" +
				"\nAll rights reserved.";
		JTextPane txtpAbout = new JTextPane();
		txtpAbout.setFocusable(false);
		txtpAbout.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txtpAbout.setEditable(false);
		txtpAbout.setFont(new Font("Arial", Font.BOLD, 12));
		txtpAbout.setOpaque(false);
		txtpAbout.setText(strAboutLabel);
		getContentPane().add(txtpAbout, BorderLayout.WEST);
		
		JScrollPane scpAbout = new JScrollPane();
		getContentPane().add(scpAbout, BorderLayout.CENTER);
		
		String strAboutDetails = new String();										//specify about text
		strAboutDetails = "Description:" +
				"\n   A tool for checking the grammar of a pdf document." +
				"\n   A tool for checking the grammar of a pdf document." +
				"\n   A tool for checking the grammar of a pdf document." +
				"\n   A tool for checking the grammar of a pdf document." +
				"\n   A tool for checking the grammar of a pdf document." +
				"\n   A tool for checking the grammar of a pdf document." +
				"\n   A tool for checking the grammar of a pdf document." +
				"\n   A tool for checking the grammar of a pdf document." +
				"\n" +
				"\nPurpose:" +
				"\n" +
				"\n" +
				"\nProponents:" +
				"\n   Member 1" +
				"\n   Member 2" +
				"\n   Member 3" +
				"\n" +
				"\nReferences:" +
				"\n" +
				"\n";
		
		JTextArea txtaAbout = new JTextArea();
		txtaAbout.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		txtaAbout.setFont(new Font("Arial", Font.PLAIN, 12));
		txtaAbout.setEditable(false);
		txtaAbout.setText(strAboutDetails);
		txtaAbout.setCaretPosition(1);
		scpAbout.setViewportView(txtaAbout);
	}

}
