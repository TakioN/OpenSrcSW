package jblee.midterm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class genSnippet {
	public static void main(String args[]) throws FileNotFoundException {
		String query = "��� ���� �ұ� �ʹ� ä��";
		File file = new File("C:/SimpleIR/OpenSourceSW/src/jblee/midterm/input.txt");
		String[] token = query.split(" ");
		String[] inputStr = new String[10];
		
		//���� �� ������ �б�
		Scanner scan = new Scanner(file);
		int i = 0;
		while(scan.hasNextLine()) {
			inputStr[i] = scan.nextLine();
			i++;		
		}
		//���� �ܾ������ �ɰ���
		String inputToken[][] = new String[10][10];
		for(int k = 0; k < i; k++) {
			inputToken[k] = inputStr[i].split(" ");
		}
		for(int j = 0; j < i; j++) {
			
		}
		
	}
}

