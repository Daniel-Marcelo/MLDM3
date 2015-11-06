
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSV {

	private String filepath;

	public ReadCSV(String filepath) {

		this.filepath = filepath;

	}

	public DataSet run() throws IOException {

		Sample sample;
		DataSet dataSet = new DataSet();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int rowNum = 1;
	

		try {

			br = new BufferedReader(new FileReader(filepath));

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] rowData = line.split(cvsSplitBy);
				
				if(rowNum == 1){
					Sample.setNumberOfAttributes(rowData.length);
					DataSet.setNumberOfAttributes(rowData.length);
				}
				

				rowNum++;
				sample = new Sample(rowData);
				dataSet.addSample(sample);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return dataSet;

	}
}
