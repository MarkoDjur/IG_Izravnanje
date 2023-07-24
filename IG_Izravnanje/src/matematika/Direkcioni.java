package matematika;

public class Direkcioni {

	public static double sracunajDirekcioni(double saY, double saX, double naY, double naX) {
		double direkcioni = 0;
		double dy = naY - saY;
		double dx = naX - saX;
		
		if(dy > 0 && dx > 0) {
			direkcioni = Math.toDegrees(Math.atan(dy / dx));
		}else if(dy > 0 && dx < 0) {
			direkcioni = Math.toDegrees(Math.atan(dy / dx)) + 180;
		}else if(dy < 0 && dx < 0) {
			direkcioni = Math.toDegrees(Math.atan(dy / dx)) + 180;
		}else if(dy < 0 && dx > 0) {
			direkcioni = Math.toDegrees(Math.atan(dy / dx)) + 360;
		}else {
			direkcioni = 0;
		}
		
		return direkcioni;
	}
	
	public static double sracunajDuzinu(double odY, double odX, double doY, double doX) {
		double dy = doY - odY;
		double dx = doX - odX;
		double duzina = Math.sqrt(dy * dy + dx * dx);
		
		return duzina;
	}
}
