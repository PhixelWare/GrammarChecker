import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.Insets;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;	
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSpinner;
import javax.swing.JComboBox;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.ghost4j.Ghostscript;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;


public class GrammarChecker {

	private JFrame frame;
	static Logger log = Logger.getLogger(Ghostscript.class);

	static Toolkit toolkit = Toolkit.getDefaultToolkit();
	static Dimension screenSize = toolkit.getScreenSize();
	
	public String strFontSelected;
	public int intFontSize;
	public String strFileNameOutput;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();												//used for configuring the log file shown in console
//		System.setProperty("jna.library.path", "C:\\Program Files (x86)\\gs\\gs9.05\\bin\\");	//specify path of gsdll32.dll 
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GrammarChecker window = new GrammarChecker();
					
					int x = (screenSize.width - window.frame.getWidth()) / 2;		//make window appear at center
					int y = (screenSize.height - window.frame.getHeight()) / 2;
					window.frame.setLocation(x, y);
					
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GrammarChecker() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Grammar Checker");										//set window caption
		frame.setMinimumSize(new Dimension(450, 300));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
//		~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//VIEWER PART
		
		JSplitPane pnlViews = new JSplitPane();										//set the main pane: Image VIewer and reports
		pnlViews.setResizeWeight(1.0);
		pnlViews.setMinimumSize(new Dimension(179, 100));
		pnlViews.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(pnlViews, BorderLayout.CENTER);
		
		JScrollPane scpImage = new JScrollPane();									//set scrolling pane for image viewer
		scpImage.setPreferredSize(new Dimension(2, 100));
		scpImage.setMinimumSize(new Dimension(23, 100));
		scpImage.getViewport().setBackground(Color.LIGHT_GRAY);
		pnlViews.setLeftComponent(scpImage);
		
		JLabel lblImageViewer = new JLabel("Image Viewer");							//set label for image viewer pane
		lblImageViewer.setFont(new Font("Arial", Font.BOLD, 12));
		lblImageViewer.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageViewer.setAlignmentX(Component.CENTER_ALIGNMENT);
		scpImage.setColumnHeaderView(lblImageViewer);
		
		JPanel pnlReport = new JPanel();											//create report panel
		pnlReport.setMinimumSize(new Dimension(10, 80));
		pnlViews.setRightComponent(pnlReport);
		pnlReport.setLayout(new BorderLayout(0, 0));
		
		JLabel lblReport = new JLabel("Report");									//create label
		lblReport.setHorizontalAlignment(SwingConstants.CENTER);
		lblReport.setFont(new Font("Arial", Font.BOLD, 12));
		pnlReport.add(lblReport, BorderLayout.NORTH);
		
		JScrollPane scpReport = new JScrollPane();									//scrolling pane for report
		pnlReport.add(scpReport, BorderLayout.CENTER);
		
		intFontSize = 12;															//text area for report
		JTextArea txtaReport = new JTextArea();
		txtaReport.setEditable(false);
		txtaReport.setFont(new Font("Arial", Font.PLAIN, intFontSize));
		txtaReport.setText("No image selected.");
		scpReport.setViewportView(txtaReport);
		
		JPanel pnlFont = new JPanel();												//create panel for font editing
		pnlFont.setPreferredSize(new Dimension(120, 10));
		pnlFont.setMinimumSize(new Dimension(120, 10));
		pnlFont.setMaximumSize(new Dimension(120, 32767));
		pnlReport.add(pnlFont, BorderLayout.EAST);
		
		JComboBox<String> cboFont = new JComboBox<String>();						//create combobox for font style
		cboFont.addItem("Arial");
		cboFont.addItem("Blackadder ITC");
		cboFont.addItem("Impact");
		cboFont.addItem("Monospaced");
		cboFont.addItem("Times New Roman");
		cboFont.setFont(new Font("Arial", Font.PLAIN, 11));
		cboFont.setPreferredSize(new Dimension(120, 23));
		cboFont.setMinimumSize(new Dimension(120, 23));
		cboFont.setMaximumSize(new Dimension(120, 23));		
		cboFont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				strFontSelected = (String) cboFont.getSelectedItem();
				txtaReport.setFont(new Font(strFontSelected, Font.PLAIN, intFontSize));
			}
		});
		pnlFont.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlFont.add(cboFont);
		
		SpinnerModel spnrModel =
			     new SpinnerNumberModel(12, //initial value
			        5, 						//min
			        50, 					//max
			        1);						//step
		JSpinner spnrFontSize = new JSpinner(spnrModel);							//create spinner for font size
		spnrFontSize.setPreferredSize(new Dimension(120, 23));
		spnrFontSize.setMinimumSize(new Dimension(120, 23));
		spnrFontSize.setMaximumSize(new Dimension(120, 23));
		spnrFontSize.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				intFontSize = (int) ((JSpinner)e.getSource()).getValue();
				txtaReport.setFont(new Font(strFontSelected, Font.PLAIN, intFontSize));
			}
		});
		pnlFont.add(spnrFontSize);
		
		
//		~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//COMMANDS PART		
				
		JPanel pnlCommands = new JPanel();											//set the commands pane
		pnlCommands.setFont(new Font("Arial", Font.PLAIN, 11));
		pnlCommands.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlCommands.setPreferredSize(new Dimension(140, 10));	
		pnlCommands.setMinimumSize(new Dimension(140, 10));
		pnlCommands.setMaximumSize(new Dimension(200, 32767));
		frame.getContentPane().add(pnlCommands, BorderLayout.WEST);
		pnlCommands.setLayout(new BoxLayout(pnlCommands, BoxLayout.Y_AXIS));
		
		JLabel lblCommands = new JLabel("Commands");								//create label for commands panel
		lblCommands.setHorizontalAlignment(SwingConstants.CENTER);
		lblCommands.setMaximumSize(new Dimension(115, 25));
		lblCommands.setMinimumSize(new Dimension(115, 25));
		lblCommands.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblCommands.setPreferredSize(new Dimension(115, 25));
		lblCommands.setAlignmentY(Component.TOP_ALIGNMENT);
		lblCommands.setFont(new Font("Arial", Font.BOLD, 12));
		pnlCommands.add(lblCommands);
		
		JLabel lblImage = new JLabel();												//create the label that will contain the image
		lblImage.setHorizontalAlignment(JLabel.CENTER);								//set alignment to center
		scpImage.setViewportView(lblImage);
		
		JButton btnOpen = new JButton("Open Image");								//create button: Open Image
		btnOpen.setMinimumSize(new Dimension(115, 23));
		btnOpen.setMaximumSize(new Dimension(115, 23));
		btnOpen.setPreferredSize(new Dimension(115, 23));
		btnOpen.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfcFileChooser = new JFileChooser();					//create file chooser
				jfcFileChooser.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "pdf");		//filter Pdf files only
				jfcFileChooser.addChoosableFileFilter(filter);
				
				if(jfcFileChooser.showOpenDialog(pnlCommands) == JFileChooser.APPROVE_OPTION) {
					java.io.File fileName = jfcFileChooser.getSelectedFile();		//get selected file name
					String strFileNameInput = fileName.toString();
					strFileNameOutput = FilenameUtils.removeExtension(strFileNameInput) + ".jpg";
					
					try {
				         PDFDocument document = new PDFDocument();					//load PDF document
				         document.load(new File(strFileNameInput));
				 
				         SimpleRenderer renderer = new SimpleRenderer();			//create renderer
				         renderer.setResolution(300);								//set resolution (in DPI)
				         List<Image> images = renderer.render(document);			//render
				 
				         try {
//				        	 for (int i = 0; i < images.size(); i++) {
				        	 ImageIO.write((RenderedImage) images.get(0), "jpg", new File(strFileNameOutput));	// write images to files to disk as JPG
//				        	 }
				         } catch (IOException e2) {
				        	 System.out.println("ERROR: " + e2.getMessage());
				         }
				 
				       } catch (Exception e3) {
				         System.out.println("ERROR: " + e3.getMessage());
				       }
					
					lblImage.setIcon(new ImageIcon(strFileNameOutput));				//set file into icon
//					lblImage.setIcon(new ImageIcon(FitImageToViewer(strFileNameOutput, scpImage.getWidth())));	//fit the image to imageviewer width
					txtaReport.setText(fileName + " file was chosen.");
					
					
				}
			}
		});
		pnlCommands.add(btnOpen);
		
		JButton btnExecute = new JButton("Execute Checker");						//create button: Execute Checker
		btnExecute.setMargin(new Insets(2, 5, 2, 5));
		btnExecute.setPreferredSize(new Dimension(115, 23));
		btnExecute.setMinimumSize(new Dimension(115, 23));
		btnExecute.setMaximumSize(new Dimension(115, 23));
		btnExecute.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlCommands.add(btnExecute);
		
		JButton btnClearImage = new JButton("Clear Image");							//create button: Clear Image
		btnClearImage.setPreferredSize(new Dimension(115, 23));
		btnClearImage.setMinimumSize(new Dimension(115, 23));
		btnClearImage.setMaximumSize(new Dimension(115, 23));
		btnClearImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnClearImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int intConfirmation = JOptionPane.showConfirmDialog(
						btnClearImage,
					    "Do you really want to Clear the Image?",
					    "Grammar Checker",
					    JOptionPane.YES_NO_OPTION);									//confirmation: Clear Image
				if (intConfirmation == JOptionPane.YES_OPTION) {
					lblImage.setIcon(null);											//remove image from the label
					txtaReport.setText("No image selected");						//blank report message
				} else if (intConfirmation == JOptionPane.NO_OPTION) {
					return;
				}
			}
		});
		pnlCommands.add(btnClearImage);
		
		JButton btnClearReport = new JButton("Clear Report");						//create button: Clear Report
		btnClearReport.setPreferredSize(new Dimension(115, 23));
		btnClearReport.setMinimumSize(new Dimension(115, 23));
		btnClearReport.setMaximumSize(new Dimension(115, 23));
		btnClearReport.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnClearReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int intConfirmation = JOptionPane.showConfirmDialog(
						btnClearReport,
					    "Do you really want to Clear the Report?",
					    "Grammar Checker",
					    JOptionPane.YES_NO_OPTION);									//confirmation: Clear Report
				if (intConfirmation == JOptionPane.YES_OPTION) {
					txtaReport.setText("");											//Clear Report
				} else if (intConfirmation == JOptionPane.NO_OPTION) {
					return;
				}
			}
		});
		pnlCommands.add(btnClearReport);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmGrammarChecker = new JMenuItem("About Grammar Checker");
		mntmGrammarChecker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GrammarCheckerAbout fraAbout = new GrammarCheckerAbout();
				fraAbout.setModal(true);
				int x = (screenSize.width - fraAbout.getWidth()) / 2;				//make window appear at center
				int y = (screenSize.height - fraAbout.getHeight()) / 2;
				fraAbout.setLocation(x, y);
				fraAbout.setVisible(true);											//show About window
			}
		});
		mnHelp.add(mntmGrammarChecker);
	}
	
	
	
//	==============================================================================================
	
	private Image FitImageToViewer(String strImgFile, int intNewWidth) {
		BufferedImage biRawImage = null;
		try {
			biRawImage = ImageIO.read(new File(strImgFile));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		int intNewHeight = (intNewWidth * (biRawImage.getHeight() / biRawImage.getWidth()));
		Image biNewImage = biRawImage.getScaledInstance(intNewWidth, intNewHeight, Image.SCALE_SMOOTH);
		
		return biNewImage;
	}
	
	public void testing() {
		
	}
	

}
