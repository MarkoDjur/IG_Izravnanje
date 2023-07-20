package matematika;

import org.apache.commons.math3.distribution.FDistribution;

public class Raspodjela {
	
public static double finv(double p, double f1, double f2) {
		
		FDistribution fDist = new FDistribution(f1, f2);
		double fValue = fDist.inverseCumulativeProbability(1 - p);
		
		
		return fValue;
		
	}

}
