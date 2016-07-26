import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String [] args){
		Whois ip = new Whois ();
		try {
			Scanner sc  = new Scanner (System.in);
			System.out.println("Please enter the list of ips. Seprate with each ip with  \",\" with no spaces");
			String ips [] = sc.next().split(",");
			ip.runWhois(ips);
			ip.createReport();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
