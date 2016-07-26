import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.commons.net.whois.WhoisClient;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

public class Whois {
	// EU //Afica //Asia //North America //Latin America and the Carribean
	String server[] = { "whois.ripe.net", "whois.afrinic.net", "whois.apnic.net", "whois.arin.net",
			"whois.lacnic.net" };
	WhoisClient wc = new WhoisClient();
	File database = new File("GeoLite2-Country.mmdb");
	File report = new File("report.txt");
	StringBuilder collectionResult; 
	
	public Whois() {

	}

	public String runWhois(String addr) throws SocketException, IOException {
		StringBuilder results = new StringBuilder();
		String data = null;
		DatabaseReader reader = new DatabaseReader.Builder(database).build();
		InetAddress ipAddress = InetAddress.getByName(addr);
		try {
			CountryResponse response = reader.country(ipAddress);
			response = reader.country(ipAddress);
			Country country = response.getCountry();
			String region = response.getContinent().getName();
			String code = country.getIsoCode();
			wc.connect(server[findDataBase(region)]);
			data = wc.query(addr);
			results.append("\n");
			results.append("IP ADDRESS: " + addr);
			results.append("\n");
			results.append("\n");
			results.append(data);

		} catch (GeoIp2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results.toString();

	}
	
	public void createReport(){
		try {
			BufferedWriter write  = new BufferedWriter(new FileWriter (report));
			write.write(collectionResult.toString());
			write.close();
			System.out.println("Report found at :"+report.getAbsolutePath());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public String runWhois(String [] collection) throws SocketException, IOException{
		collectionResult= new StringBuilder();
		for(int i=0; i<collection.length; i++){
			collectionResult.append(runWhois(collection[i]));
			System.out.println("Running whois on ip..."+collection[i]);
		}
		return collectionResult.toString();
	}

	private int findDataBase(String region) {
		switch (region) {
		case "Europe":
			return 0;
		case "North America":
			return 3;
		case "Asia":
			return 0;
		case "Africa":
			return 1;
		case "South America":
			return 4;
		}
		return 0;
	}

}
