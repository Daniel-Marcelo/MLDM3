import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sample implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int id = -1; 
	private int ID = 0;
	private static int numberOfAttributes;
	private  List<String> attributes;

	public Sample(String[] rowOfData){
		
		id++;
		System.out.println("\n\nCreating new sample with ID: "+id);
		attributes = new ArrayList<String>();
		
		for (int i = 0 ; i < numberOfAttributes ; i++)
			attributes.add(rowOfData[i]);

		ID = id;
	}
	
	public String getClassification(){
		return attributes.get(attributes.size()-1);
	}
	public String getAttribute(int index){
		
		return attributes.get(index);
	}
	
	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public static int getNumberOfAttributes() {
		return numberOfAttributes;
	}

	public static void setNumberOfAttributes(int numberOfAttributes) {
		System.out.println("Going through first row of file, setting the number of attributes to: "+numberOfAttributes);
		Sample.numberOfAttributes = numberOfAttributes;
	}

	public void printSample() {

		System.out.print("ID = "+ID+" - ");
		for(String attribute : attributes)			
			System.out.print(attribute+",");
		
		System.out.println();
	}
	
}
