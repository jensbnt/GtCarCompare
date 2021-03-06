package jensbnt.util;

public enum CarClasses {
	N100(0, "N100", "N100.txt", "N100"),
	N200(1, "N200", "N200.txt", "N200"),
	N300(2, "N300", "N300.txt", "N300"),
	N400(3, "N400", "N400.txt", "N400"),
	N500(4, "N500", "N500.txt", "N500"),
	N600(5, "N600", "N600.txt", "N600"),
	N700(6, "N700", "N700.txt", "N700"),
	N800(7, "N800", "N800.txt", "N800"),
	N900(8, "N900", "N900.txt", "N900"),
	N1000(9, "N1000", "N1000.txt", "N1000"),
	GROUP4(10, "Group 4", "Group4.txt", "GROUP4"),
	GROUP3(11, "Group 3", "Group3.txt", "GROUP3"),
	GROUP1(12, "Group 1", "Group1.txt", "GROUP1"),
	GROUPB(13, "Group B", "GroupB.txt", "GROUPB"),
	GROUPX(14, "Group X", "GroupX.txt", "GROUPX");
	
	private final int value;
	private final String name;
	private final String fileName;
	private final String databaseName;
	
	CarClasses(int value, String name, String fileName, String databaseName) {
		this.value = value;
		this.name = name;
		this.fileName = fileName;
		this.databaseName = databaseName;
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
	
	public String getDatabaseName() {
		return databaseName;
	}
}
