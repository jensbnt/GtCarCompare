package jensbnt.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import jensbnt.compareApp.Car;

@SuppressWarnings("serial")
public class CarTableModel extends DefaultTableModel {
	
	private List<Car> listCars;
    
    public CarTableModel() {
    	for(CarStats stat : CarStats.values()) {
    		addColumn(stat);
    	}
    	
        this.listCars = new ArrayList<Car>();
    }
    
    public void addRow(Car car) {
    	listCars.add(car);
    	setRowCount(getRowCount()+1);
    }
    
    @Override
    public Object getValueAt(int row, int column) {
    	Car car = listCars.get(row);
    	CarStats stat = CarStats.getName(column);
    	
    	switch (stat) {
    	case ID:
    	case NAME:
    	case MAKE:
    	case MAXSPEED:
    	case ACCELERATION:
    	case BRAKING:
    	case CORNERING:
    	case STABILITY:
    	case TOTAL_SCORE:
    		return car.getStringByStat(stat);
    	case WEIGHT:
    		return car.getStringByStat(stat) + " kg";
    	case POWER:
    		return car.getStringByStat(stat) + " BHP";
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
