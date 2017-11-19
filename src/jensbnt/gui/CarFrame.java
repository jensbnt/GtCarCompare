package jensbnt.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import jensbnt.compareApp.Garage;
import jensbnt.util.CarStats;

@SuppressWarnings("serial")
public class CarFrame extends JFrame {
	
	/* Menu Items */
	private JMenuBar menuBar;
	private JMenu menu1;
	private JMenuItem item1_1;
	private JMenuItem item1_2;
	private JMenu menu2;
	private JCheckBoxMenuItem item2_1;
	private JMenu item2_2;
	private JMenuItem item2_2_1;
	private JMenuItem item2_2_2;
	
	/* Sorting Items */
	private ButtonGroup sortingGroup;
	private List<JRadioButton> sortingRadioButtons;

	/* Class Items */
	private List<JCheckBox> classCheckBoxes;
	
	/* Action Items */
	private JButton buttonSort;
	private JCheckBox checkOwned;
	
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
		menu1 = new JMenu("Menu 1");
		item1_1 = new JMenuItem("Item 1_1");
		item1_2 = new JMenuItem("Item 1_2");
		menu2 = new JMenu("Menu 2");
		item2_1 = new JCheckBoxMenuItem("Item 2_1");
		item2_2 = new JMenu("Item 2_2");
		item2_2_1 = new JMenuItem("Item 2_2_1");
		item2_2_2 = new JMenuItem("Item 2_2_2");
		
		/* Init Sorting Items */
		sortingRadioButtons = new ArrayList<>();
		for(CarStats carStat : CarStats.values()) {
			sortingRadioButtons.add(new JRadioButton(carStat.toString()));
		}
		
		sortingGroup = new ButtonGroup();
		for (JRadioButton button : sortingRadioButtons) {
			sortingGroup.add(button);
		}
		
		sortingRadioButtons.get(0).setSelected(true);
		
		/* Init Class Items */
		classCheckBoxes = new ArrayList<JCheckBox>();
		
		String[] classNames = Garage.getClassNames();
		
		for (int index = 0; index < classNames.length; index++) {
			classCheckBoxes.add(new JCheckBox(classNames[index]));
		}
		
		/* Init Action Items */
		buttonSort = new JButton("Sort");
		checkOwned = new JCheckBox("Show only owned cars");
		
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
		menuBar.add(menu1);
		menu1.add(item1_1);
		menu1.add(item1_2);
		menuBar.add(menu2);
		item2_2.add(item2_2_1);
		item2_2.add(item2_2_2);
		menu2.add(item2_1);
		menu2.add(item2_2);
	}
	
	private void layoutComponents() {
		/* Layout Sorting Items */
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(1,5));
		for (JRadioButton button : sortingRadioButtons) {
			topPanel.add(button);
		}
		
		/* Layout Class Items */
		JPanel groupPanel = new JPanel();
		add(groupPanel, BorderLayout.WEST);
		groupPanel.setLayout(new GridLayout(classCheckBoxes.size(),1));
		
		for(JCheckBox checkBox : classCheckBoxes) {
			groupPanel.add(checkBox);
		}
		
		/* Layout Car Panel */
		add(scrollPane, BorderLayout.CENTER);
		carPanel.setLayout(new BoxLayout(carPanel, BoxLayout.Y_AXIS));
		
		/* Layout Action Items */
		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(buttonSort);
		bottomPanel.add(checkOwned);
	}
	
	private void initListeners() {
		buttonSort.addActionListener(new ButtonListener(carPanel, sortingRadioButtons, classCheckBoxes, checkOwned));
	}
}
