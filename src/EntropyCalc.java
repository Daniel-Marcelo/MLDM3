import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntropyCalc {
	
	private HashMap<String,Integer> classesAndFrequencies;
	private int numberOfSamples;
	
	public EntropyCalc(HashMap<String,Integer> classesAndFrequencies, int numberOfSamples){
		
		this.classesAndFrequencies = classesAndFrequencies;
		this.numberOfSamples = numberOfSamples;

		
		
	}
	
	public double execute(){
		
		double entropyForDataSet = 0;
		
		for(String classification: classesAndFrequencies.keySet()){
			
			System.out.println("\n\n"+classification+"\n****************");
			int classFrequency = classesAndFrequencies.get(classification);
			double fraction = (classFrequency/ (double) numberOfSamples);
			double division = -fraction;
			System.out.println("Division Fraction "+ division);
			double logBaseTwoCalc = (Math.log(fraction))/(Math.log(2));
			System.out.println("Log BASE 2  = "+logBaseTwoCalc);
			double entropyForClass = division*logBaseTwoCalc;
			System.out.println("Entropy = "+entropyForClass);
			entropyForDataSet += entropyForClass;
			System.out.println("Entropy For DataSet: "+entropyForDataSet);

		}
		return entropyForDataSet;
		
	}

}
