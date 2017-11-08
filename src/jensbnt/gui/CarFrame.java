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

@SuppressWarnings("serial")
public class CarFrame extends JFrame {
	
	private JMenuBar menuBar;
	private JMenu menu1;
	private JMenuItem item1_1;
	private JMenuItem item1_2;
	private JMenu menu2;
	private JCheckBoxMenuItem item2_1;
	private JMenu item2_2;
	private JMenuItem item2_2_1;
	private JMenuItem item2_2_2;
	
	private ButtonGroup radioGroup;
	private JRadioButton radioMaxSpeed;
	private JRadioButton radioAcceleration;
	private JRadioButton radioBraking;
	private JRadioButton radioCornering;
	private JRadioButton radioStability;
	private JRadioButton radioMake;
	private JRadioButton radioName;
	private JRadioButton radioBhp;
	private JRadioButton radioWeight;
	private JRadioButton radioPrice;

	private List<JCheckBox> checkGroup;
	private JCheckBox checkN600;
	private JCheckBox checkGroup4;
	private JCheckBox checkGroup3;
	private JCheckBox checkGroupB;
	
	private JButton buttonSort;
	private JCheckBox checkOwned;
	private JButton addOwned;
	
	private JPanel carPanel;
	private JScrollPane scrollPane;
	
	public CarFrame() {
		super("GT Sport");
		initComponents();
		initMenu();
		layoutComponents();
		initListeners();
		
		setVisible(true);
	}
	
	private void initComponents() {
		/* Init menubar */
		menuBar = new JMenuBar();
		menu1 = new JMenu("Menu 1");
		item1_1 = new JMenuItem("Item 1_1");
		item1_2 = new JMenuItem("Item 1_2");
		menu2 = new JMenu("Menu 2");
		item2_1 = new JCheckBoxMenuItem("Item 2_1");
		item2_2 = new JMenu("Item 2_2");
		item2_2_1 = new JMenuItem("Item 2_2_1");
		item2_2_2 = new JMenuItem("Item 2_2_2");
		
		/* Init radio sort buttons */
		radioGroup = new ButtonGroup();
		radioMaxSpeed = new JRadioButton("Max Speed");
		radioMaxSpeed.setActionCommand("MaxSpeed");
		radioAcceleration = new JRadioButton("Acceleration");
		radioAcceleration.setActionCommand("Acceleration");
		radioBraking = new JRadioButton("Braking");
		radioBraking.setActionCommand("Braking");
		radioCornering = new JRadioButton("Cornering");
		radioCornering.setActionCommand("Cornering");
		radioStability = new JRadioButton("Stability");
		radioStability.setActionCommand("Stability");
		radioMake = new JRadioButton("Make");
		radioMake.setActionCommand("Make");
		radioName = new JRadioButton("Name");
		radioName.setActionCommand("Name");
		radioBhp = new JRadioButton("BHP");
		radioBhp.setActionCommand("BHP");
		radioWeight = new JRadioButton("Weight");
		radioWeight.setActionCommand("Weight");
		radioPrice = new JRadioButton("Price");
		radioPrice.setActionCommand("Price");

		radioGroup.add(radioMaxSpeed);
		radioGroup.add(radioAcceleration);
		radioGroup.add(radioBraking);
		radioGroup.add(radioCornering);
		radioGroup.add(radioStability);
		radioGroup.add(radioMake);
		radioGroup.add(radioName);
		radioGroup.add(radioBhp);
		radioGroup.add(radioWeight);
		radioGroup.add(radioPrice);
		
		/* init group buttons */
		checkGroup = new ArrayList<JCheckBox>();
		checkN600 = new JCheckBox("N600");
		checkN600.setActionCommand("0");
		checkGroup4 = new JCheckBox("Group 4");
		checkGroup4.setActionCommand("1");
		checkGroup3 = new JCheckBox("Group 3");
		checkGroup3.setActionCommand("2");
		checkGroupB = new JCheckBox("Group B");
		checkGroupB.setActionCommand("3");

		checkGroup.add(checkN600);
		checkGroup.add(checkGroup4);
		checkGroup.add(checkGroup3);
		checkGroup.add(checkGroupB);
		
		/* other */
		buttonSort = new JButton("Sort");
		checkOwned = new JCheckBox("Show only owned cars");
		addOwned = new JButton("Toggle car own");
		
		/* Car Panel */
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
		JPanel bottomPanel = new JPanel();
		JScrollPane listPane = new JScrollPane();
		JPanel topPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);
		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		
		carPanel.setLayout(new BoxLayout(carPanel, BoxLayout.Y_AXIS));
		
		JPanel groupPanel = new JPanel();
		add(groupPanel, BorderLayout.WEST);
		groupPanel.setLayout(new GridLayout(11,1));
		groupPanel.add(checkN600);
		groupPanel.add(checkGroup4);
		groupPanel.add(checkGroup3);
		groupPanel.add(checkGroupB);
		
		topPanel.setLayout(new GridLayout(2,5));
		topPanel.add(radioMaxSpeed);
		topPanel.add(radioAcceleration);
		topPanel.add(radioBraking);
		topPanel.add(radioCornering);
		topPanel.add(radioStability);
		topPanel.add(radioMake);
		topPanel.add(radioName);
		topPanel.add(radioBhp);
		topPanel.add(radioWeight);
		topPanel.add(radioPrice);
		
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(buttonSort);
		bottomPanel.add(checkOwned);
		bottomPanel.add(addOwned);
	}
	
	private void initListeners() {
		buttonSort.addActionListener(new ButtonListener(carPanel, radioGroup, checkOwned, checkGroup));
		//addOwned.addActionListener(new CarOwnedListener(listArea));
	}
}
