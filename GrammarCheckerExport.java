import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class GrammarCheckerExport extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtfExportTo;
	
	static Toolkit toolkit = Toolkit.getDefaultToolkit();
	static Dimension screenSize = toolkit.getScreenSize();

	GrammarChecker WindowMain = new GrammarChecker();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GrammarCheckerExport frame = new GrammarCheckerExport();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GrammarCheckerExport() {
		setTitle("Grammar Checker Export");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 143);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblExportAs = new JLabel("Export As:");
		lblExportAs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblExportAs.setBounds(0, 17, 72, 14);
		contentPane.add(lblExportAs);
		
		JComboBox<String> cboExportAs = new JComboBox<String>();
		cboExportAs.addItem("MS Excel File");
		cboExportAs.addItem("MS Word File");
		cboExportAs.addItem("PDF File");
		cboExportAs.setBounds(82, 14, 160, 20);
		contentPane.add(cboExportAs);
		
		JLabel lblExportTo = new JLabel("Export To:");
		lblExportTo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblExportTo.setBounds(0, 42, 72, 14);
		contentPane.add(lblExportTo);
		
		txtfExportTo = new JTextField();
		txtfExportTo.setBackground(Color.WHITE);
		txtfExportTo.setEditable(false);
		txtfExportTo.setBounds(82, 39, 304, 20);
		contentPane.add(txtfExportTo);
		
		JButton btnBrowse = new JButton("...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfcFileChooser = new JFileChooser();					//create file chooser for directories
				jfcFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if(jfcFileChooser.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
					java.io.File filePath = jfcFileChooser.getSelectedFile();		//get selected file name
					String strFilePathInput = filePath.toString();
					txtfExportTo.setText(strFilePathInput);
				}
			}
		});
		btnBrowse.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBrowse.setBounds(396, 39, 28, 20);
		contentPane.add(btnBrowse);
		
		JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int intConfirmation = JOptionPane.showConfirmDialog(
						btnExport,
					    "Do you really want to Export?",
					    "Grammar Checker Export",
					    JOptionPane.YES_NO_OPTION);									//button confirmation
				if (intConfirmation == JOptionPane.YES_OPTION) {
					File filePath = new File(txtfExportTo.getText());
					if(filePath.isDirectory()) {
						String strFontSelected = (String) cboExportAs.getSelectedItem();
						switch (strFontSelected) {
							case "MS Excel File":  
								JOptionPane.showMessageDialog(btnExport,
									    "Excel selected",
									    "Grammar Checker Export",
									    JOptionPane.ERROR_MESSAGE);
								break;
							case "MS Word File":  
								JOptionPane.showMessageDialog(btnExport,
									    "Word Selected",
									    "Grammar Checker Export",
									    JOptionPane.ERROR_MESSAGE);
								break;
							case "PDF File":  
								JOptionPane.showMessageDialog(btnExport,
									    "PDF selected",
									    "Grammar Checker Export",
									    JOptionPane.ERROR_MESSAGE);
								break;
						}
						
					} else {
						JOptionPane.showMessageDialog(btnExport,
							    "Specified file path is not valid",
							    "Grammar Checker Export",
							    JOptionPane.ERROR_MESSAGE);
					}
				} else if (intConfirmation == JOptionPane.NO_OPTION) {
				   return;
				}
				
			}
		});
		btnExport.setBounds(335, 70, 89, 23);
		contentPane.add(btnExport);
	}
}
