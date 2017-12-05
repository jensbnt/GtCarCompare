package jensbnt.gui;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import jensbnt.compareApp.Car;
import jensbnt.util.CarStats;

@SuppressWarnings("serial")
public class CarTableModel extends DefaultTableModel {
	
	private List<Car> listCars;
	private List<CarStats> columns;
    
    public CarTableModel() {
        listCars = new ArrayList<>();
        columns = new ArrayList<>();
        
    	for(CarStats stat : CarStats.values()) {
    		if (stat != CarStats.ID) {
    			columns.add(stat);
    			addColumn(stat);
    		}
    	}
    }
    
    public void addRow(Car car) {
    	listCars.add(car);
    	setRowCount(getRowCount()+1);
    }
    
    public Car getCarAt(int row) {
    	return listCars.get(row);
    }
    
    @Override
    public Object getValueAt(int row, int column) {
    	Car car = listCars.get(row);
    	CarStats stat = columns.get(column);
    	
    	switch (stat) {
    	case ID:
    	case NAME:
    	case MAKE:
    	case MAXSPEED:
    	case ACCELERATION:
    	case BRAKING:
    	case CORNERING:
    	case STABILITY:
    		return car.getStat(stat);
    	case TOTAL_SCORE:
    		return String.format("%.1f", car.getTotalScore());
    	case WEIGHT:
    		return car.getStat(stat) + " kg";
    	case POWER:
    		return car.getStat(stat) + " BHP";
    	case PRICE:
    		return "Cr. " + NumberFormat.getIntegerInstance().format(car.getPrice());
		default:
			return null;	
    	}
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
    	return false;
    }

	public void clear() {
		listCars.clear();
		setRowCount(0);
	}
}
