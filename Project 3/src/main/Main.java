package main;

import java.util.Scanner;
import dataset.Dataset;
import query.AggregationFunctionType;
import query.Query;

public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		boolean blnExit = false;
	
		while(!blnExit) {
			
			System.out.println("This program implements the join and aggregation operators. \n"
					+ "You may exit at any point by entering 'Exit'. \n"
					+ "Enter your query:");
			String input = scanner.nextLine();
			
			if(input.equals("SELECT A.Col1, A.Col2, B.Col1, B.Col2 FROM A, B WHERE A.RandomV = B.RandomV")) {
				Query.performHashJoin();
			} else if(input.equals("SELECT count(*) FROM A, B WHERE A.RandomV > B.RandomV")) {
				Query.performBlockLevelNestedLoopJoin();
			} else if(input.equals("SELECT Col2, SUM(RandomV) FROM A GROUP BY Col2")) {
				Query.performHashBasedAggregationFunction(Dataset.A,AggregationFunctionType.SUM_RANDOMV);
			} else if(input.equals("SELECT Col2, SUM(RandomV) FROM B GROUP BY Col2")) {
				Query.performHashBasedAggregationFunction(Dataset.B,AggregationFunctionType.SUM_RANDOMV);
			} else if(input.equals("SELECT Col2, AVG(RandomV) FROM A GROUP BY Col2")) {
				Query.performHashBasedAggregationFunction(Dataset.A,AggregationFunctionType.AVG_RANDOMV);
			} else if(input.equals("SELECT Col2, AVG(RandomV) FROM B GROUP BY Col2")) {
				Query.performHashBasedAggregationFunction(Dataset.B,AggregationFunctionType.AVG_RANDOMV);
			} else if(input.equalsIgnoreCase("Exit")) {
				blnExit = true;
			}
			
		}
		
		scanner.close();
		
	}

}
