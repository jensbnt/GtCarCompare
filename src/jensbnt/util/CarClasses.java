package jensbnt.util;

public enum CarClasses {
	N100(1, "N100", "N100.txt"),
	N200(2, "N200", "N200.txt"),
	N300(3, "N300", "N300.txt"),
	N400(4, "N400", "N400.txt"),
	N500(5, "N500", "N500.txt"),
	N600(6, "N600", "N600.txt"),
	N700(7, "N700", "N700.txt"),
	N800(8, "N800", "N800.txt"),
	N1000(9, "N1000", "N1000.txt"),
	GROUP4(10, "Group 4", "Group4.txt"),
	GROUP3(11, "Group 3", "Group3.txt"),
	GROUP1(12, "Group 1", "Group1.txt"),
	GROUPB(13, "Group B", "GroupB.txt"),
	GROUPX(14, "Group X", "GroupX.txt");
	
	private final int value;
	private final String name;
	private final String fileName;
	
	CarClasses(int value, String name, String fileName) {
		this.value = value;
		this.name = name;
		this.fileName = fileName;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return name;
	}
	
	public String getFileName() {
		return fileName;
	}
}
