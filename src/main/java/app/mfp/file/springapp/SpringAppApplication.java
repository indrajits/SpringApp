package app.mfp.file.springapp;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class SpringAppApplication implements CommandLineRunner {

	@Value("${file.url}")
	private String fileUrl;
	
	@Value("${table.name}")
	private String tableName;
	
	@Value("${data.file.url}")
	private String dataFileUrl;
	
	private static final Logger log = LoggerFactory.getLogger(SpringAppApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		
        FileWriter fileWriter = new FileWriter(dataFileUrl);
        	
    	File file = new File(fileUrl);
    	Scanner sc = new Scanner(file);
		
		String line = new String();
		String[] words = sc.nextLine().split(",");
		
		String queryString = "insert into " + tableName + "(";
		for (String col : words) {
			queryString += col.trim().replaceAll("^\"|\"$", "") + ", ";
		}		
		queryString = queryString.substring(0, queryString.length() - 2) + ")";
		
		 
		while (sc.hasNextLine()) {
			line = sc.nextLine(); 
			
			StringBuffer query = new StringBuffer();
			query.append(queryString + " values (");
			
			words = line.split(",");
			for (String value : words) {
				query.append(value.trim().replaceAll("^\"|\"$", "'") + ", ");
			}
			
			String temp = query.substring(0, query.length() - 2) + ")";
			
			fileWriter.write(temp + "\n");
		}
		sc.close();
        fileWriter.close();
	}

}
