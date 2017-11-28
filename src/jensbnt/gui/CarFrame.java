package jensbnt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jensbnt.compareApp.Car;
import jensbnt.compareApp.Garage;
import jensbnt.util.CarClasses;
import jensbnt.util.CarStats;
import jensbnt.util.CarTableModel;

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
	private JCheckBox selectAll;
	
	/* Car Option Items */
	private JButton buttonEditCar;
	private JButton buttonToggleOwn;
	
	/* Action Items */
	private JButton buttonSort;
	
	/* Car Panel Items */
	private JTable carTable;
	private JScrollPane scrollPane;
	
	public CarFrame() {
		super("GT Sport" + (Garage.offlineModeActivated() ? " - Offline mode" : " - Online mode"));
		initComponents();
		initMenu();
		layoutComponents();
		initListeners();
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
	}
	
	private void initComponents() {
		/* Initialize Menu Items */
		menuBar = new JMenuBar();
		menu1_tools = new JMenu("Tools");
		item1_1_value = new JMenuItem("Calculate garage value");
		
		/* Initialize Sorting Items */
		sortingRadioButtons = new ArrayList<>();
		for(CarStats carStat : CarStats.values()) {
			sortingRadioButtons.add(new StatRadioButton(carStat));
		}
		
		sortingGroup = new ButtonGroup();
		for (StatRadioButton button : sortingRadioButtons) {
			sortingGroup.add(button);
		}
		
		sortingRadioButtons.get(0).setSelected(true);
		
		/* Initialize Class Items */
		classCheckBoxes = new ArrayList<>();
		
		for(CarClasses carClass : CarClasses.values()) {
			classCheckBoxes.add(new ClassCheckBox(carClass));
		}
		
		/* Initialize Option Items */
		checkOwned = new JCheckBox("Only show owned cars");
		checkFocus = new JCheckBox("Focus in filtered fields");
		selectAll = new JCheckBox("Select all classes");
		
		/* Initialize Car Option Items */
		buttonEditCar = new JButton("Edit selected car");
		buttonEditCar.setEnabled(false);
		buttonToggleOwn = new JButton("Toggle selected car's ownership");
		buttonToggleOwn.setEnabled(false);
		
		/* Initialize Action Items */
		buttonSort = new JButton("Sort Cars");
		
		/* Initialize Car Panel Items */
		carTable = new JTable(new CarTableModel());
		//carTable.setAutoCreateRowSorter(true);
		carTable.getTableHeader().setReorderingAllowed(false);
		scrollPane = new JScrollPane(carTable);
		carTable.setFillsViewportHeight(true);
		carTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		/* Initialize window */
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
		optionPanel.add(selectAll);
		
		/* Car Option */
		JPanel carOptionPanel = new JPanel();
		carOptionPanel.setBorder(BorderFactory.createTitledBorder("Car Selection Options"));
		carOptionPanel.setLayout(new GridLayout(2,0));
		carOptionPanel.add(buttonEditCar);
		carOptionPanel.add(buttonToggleOwn);
		
		/* Parent */
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(sortPanel);
		topPanel.add(classPanel);
		topPanel.add(optionPanel);
		topPanel.add(carOptionPanel);
		
		/* Layout Car Panel */
		add(scrollPane, BorderLayout.CENTER);
		
		/* Layout Action Items */
		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(buttonSort);
	}
	
	private void initListeners() {
		/* Initialize Menu Listeners */
		item1_1_value.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Your garage is worth " +  NumberFormat.getIntegerInstance().format(Garage.getValue()) + " Credits.", "GT Sport - Garage Value", JOptionPane.PLAIN_MESSAGE);
			}
			
		});
		
		/* Initialize Option Listeners */
		selectAll.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (selectAll.isSelected()) {
					for (ClassCheckBox check : classCheckBoxes) {
						check.setSelected(true);
					}
				} else {
					for (ClassCheckBox check : classCheckBoxes) {
						check.setSelected(false);
					}
				}
			}
		});
		
		/* Initialize Action Listeners */
		buttonSort.addActionListener(new ButtonListener((CarTableModel) carTable.getModel(), sortingRadioButtons, classCheckBoxes, checkOwned, checkFocus));

		/* Initialize Car Option Listeners */
		buttonToggleOwn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CarTableModel model = (CarTableModel) carTable.getModel();
				Car car = model.getCarAt(carTable.getSelectedRow());
				
				car.toggleOwned();
				
				if (car.getOwned()) {
					buttonToggleOwn.setBackground(Color.GREEN);
				} else {
					buttonToggleOwn.setBackground(Color.RED);
				}
			}
			
		});
		
		/* Initialize Car Panel Listeners */
		carTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(carTable.getSelectedRow() != -1) {
					buttonEditCar.setEnabled(true);
					buttonToggleOwn.setEnabled(true);
					
					CarTableModel model = (CarTableModel) carTable.getModel();
					Car car = model.getCarAt(carTable.getSelectedRow());
					
					if (car.getOwned()) {
						buttonToggleOwn.setBackground(Color.GREEN);
					} else {
						buttonToggleOwn.setBackground(Color.RED);
					}
				} else {
					buttonEditCar.setEnabled(false);
					buttonToggleOwn.setEnabled(false);
				}
			}
		});
	}
}
