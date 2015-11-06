import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.rmi.CORBA.Util;

public class Threading implements Runnable{

	private List<Double> attributeValues;
	private int attributeNumber;
	private Set<Double> attributeValuesSet;
	private List<Double> tempList;
	private double entropy;
	
	public Threading(List<Double> attributeValues, int attributeNumber,double entropy){
		
		this.attributeValues=attributeValues;
		this.attributeNumber = attributeNumber;
		tempList = new ArrayList<Double>();
		attributeValuesSet = new HashSet<Double>();
		this.entropy = entropy;

	}
	
	
	public void convertvalueListToSet(){
		
		for(Double attValue: attributeValues)			
			attributeValuesSet.add(attValue);

		tempList = new ArrayList<Double>(attributeValuesSet);
		Collections.sort(tempList);	
		System.out.println("Attribute : "+ attributeNumber+ " - Set");
		System.out.println(tempList.toString());
		System.out.println("Size - "+tempList.size());
		
	}
	@Override
	public void run() {
		
		convertvalueListToSet();
		calculateOptimalGainForThisAttribute();
	}


	private void calculateOptimalGainForThisAttribute() {

		System.out.println("CALCULATING GAIN FOR: Attribute: "+attributeNumber);
		double value1;
		double value2;
		for(int i = 0; i < tempList.size() ; i++){
			
			int j = i+1;
			if(j==tempList.size())
				break;
			value1 = tempList.get(i);
			value2 = tempList.get(j);
			double attributeValueForGain = (value1+value2)/2;
			System.out.println("\nComparing element: "+i+"("+value1+") and element: "+j+"("+value2+")");
			System.out.println("Using: "+attributeValueForGain +" - ( "+value1+" + "+value2+ " divided by 2");
			
			computeGainForAttribute(attributeValueForGain,j);
			
		}
		System.out.println("Entropy value: "+entropy+"\n");
	}


	private void computeGainForAttribute(double gainThresholdForCurrentIteration, int indexMarker) {

		int numberOfTrues = tempList.size()-indexMarker;
		int numberOfFalse = indexMarker-1;
		System.out.println("Trues: "+numberOfTrues);
		System.out.println("False: "+numberOfFalse);

	}
	
	
	
	

}
