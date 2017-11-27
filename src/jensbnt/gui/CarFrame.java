package jensbnt.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jensbnt.compareApp.Garage;
import jensbnt.util.CarClasses;
import jensbnt.util.CarStats;

@SuppressWarnings("serial")
public class CarFrame extends JFrame {
	
	/* Menu Items */
	private JMenuBar menuBar;
	private JMenu menu1_tools;
	private JMenuItem item1_1_value;
	
	/* Sorting Items */
	private ButtonGroup sortingGroup;
	private List<StatRadioButton> sortingRadioButtons;

	/* Class Items */
	private List<ClassCheckBox> classCheckBoxes;
	
	/* Option Items */
	private JCheckBox checkOwned;
	private JCheckBox checkFocus;
	
	/* Action Items */
	private JButton buttonSort;
	
	/* Car Panel Items */
	private JPanel carPanel;
	private JScrollPane scrollPane;
	
	public CarFrame() {
		super("GT Sport");
		initComponents();
		initMenu();
		layoutComponents();
		initListeners();
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
	}
	
	private void initComponents() {
		/* Init Menu Items */
		menuBar = new JMenuBar();
		menu1_tools = new JMenu("Tools");
		item1_1_value = new JMenuItem("Calculate garage value");
		item1_1_value.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Your garage is worth " +  NumberFormat.getIntegerInstance().format(Garage.getValue()) + " Credits.", "GT Sport - Garage Value", JOptionPane.PLAIN_MESSAGE);
			}
			
		});
		
		/* Init Sorting Items */
		sortingRadioButtons = new ArrayList<>();
		for(CarStats carStat : CarStats.values()) {
			sortingRadioButtons.add(new StatRadioButton(carStat));
		}
		
		sortingGroup = new ButtonGroup();
		for (StatRadioButton button : sortingRadioButtons) {
			sortingGroup.add(button);
		}
		
		sortingRadioButtons.get(0).setSelected(true);
		
		/* Init Class Items */
		classCheckBoxes = new ArrayList<>();
		
		for(CarClasses carClass : CarClasses.values()) {
			classCheckBoxes.add(new ClassCheckBox(carClass));
		}
		
		/* Init Option Items */
		checkOwned = new JCheckBox("Only show owned cars");
		checkFocus = new JCheckBox("Focus in filtered fields");
		
		/* Init Action Items */
		buttonSort = new JButton("Sort Cars");
		
		/* Init Car Panel Items */
		carPanel = new JPanel();
		scrollPane = new JScrollPane(carPanel);
		
		/* Init window */
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(750, 500);
		setLocation(20, 20);
	}
	
	private void initMenu() {
		setJMenuBar(menuBar);
		menuBar.add(menu1_tools);
		menu1_tools.add(item1_1_value);
	}
	
	private void layoutComponents() {
		/* Layout Sorting, Class and Option Items */
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		
		/* Sorting */
		JPanel sortPanel = new JPanel();
		sortPanel.setBorder(BorderFactory.createTitledBorder("Sort Cars"));
		sortPanel.setLayout(new GridLayout(2, 7));
		for (StatRadioButton button : sortingRadioButtons) {
			sortPanel.add(button);
		}
		
		/* Class */
		JPanel classPanel = new JPanel();
		classPanel.setBorder(BorderFactory.createTitledBorder("Select Classes"));
		classPanel.setLayout(new GridLayout(2, 7));
		for(ClassCheckBox checkBox : classCheckBoxes) {
			classPanel.add(checkBox);
		}
		
		/* Option */
		JPanel optionPanel = new JPanel();
		optionPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		optionPanel.setLayout(new GridLayout(2,0));
		optionPanel.add(checkOwned);
		optionPanel.add(checkFocus);
		
		/* Parent */
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(sortPanel);
		topPanel.add(classPanel);
		topPanel.add(optionPanel);
		
		/* Layout Car Panel */
		add(scrollPane, BorderLayout.CENTER);
		carPanel.setLayout(new BoxLayout(carPanel, BoxLayout.Y_AXIS));
		
		/* Layout Action Items */
		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(buttonSort);
	}
	
	private void initListeners() {
		buttonSort.addActionListener(new ButtonListener(carPanel, sortingRadioButtons, classCheckBoxes, checkOwned, checkFocus));
	}
}
