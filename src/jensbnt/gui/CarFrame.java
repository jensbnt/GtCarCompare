package jensbnt.gui;

import java.awt.BorderLayout;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jensbnt.compareApp.Car;
import jensbnt.compareApp.Garage;
import jensbnt.util.CarClasses;
import jensbnt.util.CarStats;

@SuppressWarnings("serial")
public class CarFrame extends JFrame {
	
	/* CONSTANTS */
	private final int BUTTON_ROW_HEIGHT = 3;
	
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
	private JCheckBox selectAll;
	
	/* Car Option Items */
	private JSlider editOwn;
	
	/* Action Items */
	private JButton buttonSort;
	private JProgressBar progressBar;
	
	/* Car Panel Items */
	private JTable carTable;
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
			classCheckBoxes.get(carClass.getValue()).setEnabled(false);
		}
		
		/* Initialize Option Items */
		checkOwned = new JCheckBox("Only show owned cars");
		selectAll = new JCheckBox("Select all classes");
		
		/* Initialize Car Option Items */
		editOwn = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		editOwn.setMajorTickSpacing(1);
		editOwn.setPaintLabels(true);
		//editOwn.setPaintTicks(true);
		editOwn.setEnabled(false);
		
		/* Initialize Action Items */
		buttonSort = new JButton("Sort Cars");
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		
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
        setLocationRelativeTo(null);
	}
	
	private void initMenu() {
		setJMenuBar(menuBar);
		menuBar.add(menu1_tools);
		menu1_tools.add(item1_1_value);
	}
	
	private void layoutComponents() {
		/* Layout Sorting, Class and Option Items */
		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.PAGE_START);
		
		/* Sorting */
		JPanel sortPanel = new JPanel();
		sortPanel.setBorder(BorderFactory.createTitledBorder("Sort Cars"));
		sortPanel.setLayout(new GridLayout(BUTTON_ROW_HEIGHT, 0));
		for (StatRadioButton button : sortingRadioButtons) {
			sortPanel.add(button);
		}
		
		/* Class */
		JPanel classPanel = new JPanel();
		classPanel.setBorder(BorderFactory.createTitledBorder("Select Classes"));
		classPanel.setLayout(new GridLayout(BUTTON_ROW_HEIGHT, 0));
		for(ClassCheckBox checkBox : classCheckBoxes) {
			classPanel.add(checkBox);
		}
		
		/* Option */
		JPanel optionPanel = new JPanel();
		optionPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		optionPanel.setLayout(new GridLayout(BUTTON_ROW_HEIGHT, 0));
		optionPanel.add(checkOwned);
		optionPanel.add(selectAll);
		
		/* Car Option */
		JPanel carOptionPanel = new JPanel();
		carOptionPanel.setBorder(BorderFactory.createTitledBorder("Number of owned cars"));
		carOptionPanel.setLayout(new GridLayout(BUTTON_ROW_HEIGHT, 0));
		carOptionPanel.add(editOwn);
		
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
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(buttonSort);
		bottomPanel.add(progressBar);
	    
		add(bottomPanel, BorderLayout.PAGE_END);
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
		buttonSort.addActionListener(new SortButtonListener((CarTableModel) carTable.getModel(), sortingRadioButtons, classCheckBoxes, checkOwned));

		/* Initialize Car Option Listeners */
		editOwn.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				CarTableModel model = (CarTableModel) carTable.getModel();
				Car car = model.getCarAt(carTable.getSelectedRow());
				
				car.setOwned((int) editOwn.getValue());
				model.fireTableRowsUpdated(carTable.getSelectedRow(), carTable.getSelectedRow()+1);
			}

		});
		
		/* Initialize Car Panel Listeners */
		carTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(carTable.getSelectedRow() != -1) {
					editOwn.setEnabled(true);
					
					CarTableModel model = (CarTableModel) carTable.getModel();
					Car car = model.getCarAt(carTable.getSelectedRow());
					
					editOwn.setValue(car.getOwned());
				} else {
					editOwn.setEnabled(false);
				}
			}
		});
		
		/* Initialize Garage Listeners */
		
		class GarageUpdater implements GarageUpdateListener {

			@Override
			public void updateValueChanged(int percentage) {
				progressBar.setValue(percentage);
				
			}

			@Override
			public void newUpdate(Boolean b) {
				if (b) { /* Disable functions */
					buttonSort.setVisible(false);
					progressBar.setVisible(true);
					progressBar.setValue(0);
					
					for(CarClasses carClass : CarClasses.values()) {
						classCheckBoxes.get(carClass.getValue()).setEnabled(false);
					}
				} else { /* Enable functions */
					buttonSort.setVisible(true);
					progressBar.setVisible(false);
					
					for(CarClasses carClass : CarClasses.values()) {
						if (Garage.isLoaded(carClass)) {
							classCheckBoxes.get(carClass.getValue()).setEnabled(true);
						} else {
							classCheckBoxes.get(carClass.getValue()).setEnabled(false);
						}
					}
				}
				
			}
			
		}
		
		Garage.addListener(new GarageUpdater());
	}
}
