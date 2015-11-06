import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFileChooser;


public class Test {

	private static int NUMTHREADS;

	public static void main(String[] args) throws IOException{
		
		DataSet dataSet = new DataSet();
		List<Sample> trainingSampleList , testSampleList;
		boolean hasSelectedFile = false;
		JFileChooser fileChooser = new JFileChooser();
		GUI gui = new GUI(fileChooser);
		long startTime =0;
		
		
		hasSelectedFile = gui.checkIfFileChosen(gui.open());
		
		if(hasSelectedFile){
			 startTime = System.currentTimeMillis();

			String filepath = gui.getSelectedFile().getAbsolutePath();
			ReadCSV readCSV = new ReadCSV(filepath);
			dataSet = readCSV.run();
			
			dataSet.createTrainingAndTestData();
			trainingSampleList = dataSet.getTrainingSamples();
			testSampleList = dataSet.getTestSamples();
			printList(trainingSampleList);

			System.out.println("OVERALL DATA SET has number of samples: "+dataSet.getSampleList().size());
		}
		
		
		convertNumericalAttributesToDiscrete(dataSet);
		
		
		
		
		long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) +"ms");
	}

	@SuppressWarnings("static-access")
	private static void convertNumericalAttributesToDiscrete(DataSet dataSet) {

		double entropy = dataSet.Entropy();
		HashMap<String,List<Double>> attributesValuesMap = dataSet.getAttributevaluesMap();
		NUMTHREADS = dataSet.getNumberOfAttributes();
		ExecutorService executor = Executors.newFixedThreadPool(NUMTHREADS);

		int i = 1; 
		for (String attName: attributesValuesMap.keySet()) {
			
			List<Double> thisAttributeValues = attributesValuesMap.get(attName);
			Runnable worker = new Threading(thisAttributeValues,i,entropy);
			executor.execute(worker);
			i++;
		}
		
		
		executor.shutdown();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {
 
		}
		System.out.println("\nFinished all threads: "+(i-1));
	}

	private static void printList(List<Sample> trainingSampleList) {

		System.out.println("Printing Samples in list");
		int i = 1 ;
		for(Sample s: trainingSampleList){
			System.out.print((i++) +" - ");
			s.printSample();
		}
		
	}

}
