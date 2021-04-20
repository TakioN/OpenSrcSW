package jblee.midterm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class genSnippet {
	public static void main(String args[]) throws FileNotFoundException {
		String query = "라면 반죽 소금 초밥 채소";
		File file = new File("C:/SimpleIR/OpenSourceSW/src/jblee/midterm/input.txt");
		String[] token = query.split(" ");
		String[] inputStr = new String[10];
		
		//파일 줄 단위로 읽기
		Scanner scan = new Scanner(file);
		int i = 0;
		while(scan.hasNextLine()) {
			inputStr[i] = scan.nextLine();
			i++;		
		}
		//쿼리 단어단위로 쪼개기
		String inputToken[][] = new String[10][10];
		for(int k = 0; k < i; k++) {
			inputToken[k] = inputStr[i].split(" ");
		}
		for(int j = 0; j < i; j++) {
			
		}
		
	}
}

