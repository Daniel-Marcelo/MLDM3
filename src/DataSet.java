import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class DataSet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int trainingSetSize;
	private int testSetSize;
	private double entropy;
	private List<Sample> sampleList,trainingSamples,testSamples;
	private static int numberOfAttributes;
	private HashMap<String,List<Double>> attributevaluesMap;

	public HashMap<String, List<Double>> getAttributevaluesMap() {
		return attributevaluesMap;
	}

	public void setAttributevaluesMap(HashMap<String, List<Double>> attributevaluesMap) {
		this.attributevaluesMap = attributevaluesMap;
	}

	public static int getNumberOfAttributes() {
		return numberOfAttributes;
	}

	public static void setNumberOfAttributes(int numberOfAttributes) {
		DataSet.numberOfAttributes = numberOfAttributes;
	}

	public DataSet() {


		attributevaluesMap = new HashMap<String,List<Double>> ();
		sampleList = new ArrayList<Sample>();		
		trainingSamples = new ArrayList<Sample>();
		testSamples = new ArrayList<Sample>();

	}

	public DataSet deepClone() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (DataSet) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getTrainingSetSize() {
		return trainingSetSize;
	}

	public void setTrainingSetSize(int trainingSetSize) {
		this.trainingSetSize = trainingSetSize;
	}

	public int getTestSetSize() {
		return testSetSize;
	}

	public void setTestSetSize(int testSetSize) {
		this.testSetSize = testSetSize;
	}

	public List<Sample> getSampleList() {
		return sampleList;
	}

	public void setSampleList(List<Sample> sampleList) {
		this.sampleList = sampleList;
	}

	public void addSample(Sample sample) {

		sampleList.add(sample);
	}

	public void setTrainingSetAndTestSetSize() {

		int dataSetSize = this.getSampleList().size();
		double twoThirds = 2 / (double) 3;
		trainingSetSize = (int) (dataSetSize * twoThirds);
		testSetSize = dataSetSize - trainingSetSize;

		System.out.println("\n\nNumber of samples to be created in test data = " + testSetSize);
		System.out.println("Number of samples to be created in training data = " + trainingSetSize+"\n\n");
	}

	public void createTrainingAndTestData() {

		setTrainingSetAndTestSetSize();
		DataSet dataSetCopy = deepClone();

		for (int i = 0; i < testSetSize; i++) {

			int randomSampleIndex = dataSetCopy.getRandomSampleIndex();
			Sample testSample = dataSetCopy.getSampleAtIndex(randomSampleIndex);
			
			dataSetCopy.removeSample(testSample);
			testSamples.add(testSample);
		}
		trainingSamples = dataSetCopy.getSampleList();
		
		
		convertStringAttributesToDoubles();
	}
	
	public List<Sample> getTrainingSamples() {
		return trainingSamples;
	}

	public void setTrainingSamples(List<Sample> trainingSamples) {
		this.trainingSamples = trainingSamples;
	}

	public List<Sample> getTestSamples() {
		return testSamples;
	}

	public void setTestSamples(List<Sample> testSamples) {
		this.testSamples = testSamples;
	}

	public void removeSample(Sample s){
		
		this.sampleList.remove(s);

	}
	
	public int getRandomSampleIndex(){
		return ThreadLocalRandom.current().nextInt(0, this.sampleList.size());
	}
	
	public Sample getSampleAtIndex(int index){
		return this.sampleList.get(index);
	}

	public double Entropy() {


		HashMap<String, Integer> classesAndFrequencies = new HashMap<String, Integer>();
		classesAndFrequencies = getClassesAndFrequency();
		System.out.println("Calculating entropy..");


		
		EntropyCalc entropyCalc = new EntropyCalc(classesAndFrequencies, sampleList.size());
		entropy = entropyCalc.execute();
		return entropy;

	}
	
	public void sortAttributeColumn(int columnIndex){
		List<Double> attributeValueRange = new ArrayList<Double>();
		
			
			for(Sample sample: trainingSamples){
				
				double attributeValueForThisSample = Double.parseDouble(sample.getAttribute(columnIndex));
				sample.printSample();
				System.out.println("Adding attribute "+(columnIndex+1)+" values to new list.");
				System.out.println("Inserting "+attributeValueForThisSample+ " into the list\n******************************************************\n");
				attributeValueRange.add(attributeValueForThisSample);
			}
			Collections.sort(attributeValueRange);
			printList(attributeValueRange,columnIndex);
			
			attributevaluesMap.put(("Attribute "+(columnIndex+1)).toString(), attributeValueRange);
		
	}
	
	public void convertStringAttributesToDoubles(){
		
		Sample sample = trainingSamples.get(0);
		for(int i = 0 ; i < numberOfAttributes;i++){
			
			String attributeValue = sample.getAttribute(i);
			
			if(isDouble(attributeValue))
				sortAttributeColumn(i);
			
		}
		for(String key: attributevaluesMap.keySet()){
			System.out.println("\nKEY: "+key);
			System.out.print (attributevaluesMap.get(key)+" ");
			System.out.print (attributevaluesMap.get(key).size()+" - Size\n");

		}
		
		//calculateHighestGainForEachAttribute();
	}
	
	public Set<String> possibleClassValues(){
		Set<String> classNames = new HashSet<String>();

		for (Sample sample : sampleList) {

			List<String> attributes = sample.getAttributes();
			String owlType = attributes.get(Sample.getNumberOfAttributes() - 1);
			if (!classNames.contains(owlType)) {
				classNames.add(owlType);
			}

		}
		return classNames;
	}
	
	private static void printList(List<Double> attributeValues, int attributeNumber) {

		System.out.println("Printing Samples of attribute: "+(attributeNumber+1));
		for(Double d: attributeValues){
			System.out.print(d+", ");
		}
		System.out.println();
		
	}
	
	public HashMap<String, Integer> getClassesAndFrequency() {
		
		Set<String> classNames = possibleClassValues();
		HashMap<String, Integer> classesAndFrequencies = new HashMap<String, Integer>();
		printSet(classNames);

		for (String className : classNames) {
			
			classesAndFrequencies.put(className, 0);
			
			for (Sample sample : sampleList) {
				if (sample.getClassification().equals(className)) {

						int frequency = classesAndFrequencies.get(className);
						int newFrequency = frequency + 1;
						System.out.println("New frequency for: " + className + " is " + newFrequency);
						classesAndFrequencies.put(className, newFrequency);
				}
			}
		}
		printMap(classesAndFrequencies);
		return classesAndFrequencies;
	}

	public void printMap(HashMap<String, Integer> classes) {

		System.out.println("Calculating hashMap");
		// TODO Auto-generated method stub
		for (String key : classes.keySet()) {
			System.out.println(key + " number of instances = " + classes.get(key));
		}

	}
	
	public void printDoubleListMap(HashMap<String, List<Double>> attributeValueMap) {

		System.out.println("Calculating hashMap");
		// TODO Auto-generated method stub
		for (String key : attributeValueMap.keySet()) {
			System.out.print(key + ": ");
			for(Double d: attributeValueMap.get(key)){
				System.out.print( " "+d);
			}
		}
System.out.println();
	}

	public void printDataSetSamples() {

		for (Sample s : this.getSampleList()) {
			System.out.println("HERE");
			s.printSample();
		}
	}

	public void printSet(Set<String> classNames) {
		// TODO Auto-generated method stub
		System.out.println("Printing Set");
		for (String s : classNames) {
			System.out.println("Class: " + s);
		}

	}
	
	public boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
