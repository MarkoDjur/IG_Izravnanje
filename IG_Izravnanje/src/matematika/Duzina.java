package matematika;

public class Duzina {
	
	public static double sracunajDuzinu(double odY, double odX, double doY, double doX) {
		double dy = doY - odY;
		double dx = doX - odX;
		double duzina = Math.sqrt(dy * dy + dx * dx);
		
		return duzina;
	}

}
