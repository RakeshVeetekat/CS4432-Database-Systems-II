package main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
	
		Scanner scanner = new Scanner(System.in);
		DatabaseManager db = new DatabaseManager();
		boolean stop = false;
	
		while(!stop) {
			
			System.out.println("Build out the indexes of the database. "
					+ "If they are already built, enter your query. "
					+ "You may also stop this program by printing exit. \n");
			String input = scanner.nextLine();
			System.out.println(input);
			
			if(input.equalsIgnoreCase("CREATE INDEX ON RANDOMV")) {
				db.createIndexes();
				System.out.println("The hash-based and array-based indexes are built");
			} else if(input.startsWith("SELECT * FROM Project2Dataset WHERE")) {
				db.executeQuery(input);
			} else if(input.equalsIgnoreCase("Exit")){
				stop = true;
			} else {
				System.out.println("Invalid command");
			}
			System.out.println("-------------------------------------------------------");
			
		}
		
		
	}

}
