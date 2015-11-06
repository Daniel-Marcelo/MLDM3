import java.io.File;

import javax.swing.JFileChooser;


public class GUI {
	
	private JFileChooser fileChooser;
	private File selectedFile;
	
	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	public void setFileChooser(JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	/*Constructor*/
	public GUI(JFileChooser fileChooser){
		
		this.fileChooser = fileChooser;
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")+"/Desktop"));

	}
	
	public int open(){
		return fileChooser.showOpenDialog(fileChooser);
	}
	
	public boolean checkIfFileChosen(int result){
		

		if (result == JFileChooser.APPROVE_OPTION) {
		    selectedFile = fileChooser.getSelectedFile();
		    System.out.println("\nSelected file: " + selectedFile.getAbsolutePath());
			return true;

		}
		return false;
	}
	


}
