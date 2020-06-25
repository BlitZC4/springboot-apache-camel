package com.gzoul.springbootapachecamel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class SpringbootApacheCamelApplication extends RouteBuilder {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApacheCamelApplication.class, args);
	}

	@Override
	public void configure() throws Exception {
		System.out.println("Started");
//		moveAllFile();
//		moveSpecificFile();
//		moveSpecificFileWithBody();
//		fileProcess();
		multiFileProcessor();
		System.out.println("Ended");
	}

	public void moveAllFile(){
		from("file:C:/Users/Ghufr/OneDrive/Desktop/source1?noop=true").to("file:C:/Users/Ghufr/OneDrive/Desktop/target1");
	}

	public void moveSpecificFile(){
		from("file:C:/Users/Ghufr/OneDrive/Desktop/source1?noop=true").filter(header(Exchange.FILE_NAME).startsWith("Test Text")).to("file:C:/Users/Ghufr/OneDrive/Desktop/target1");
	}

	public void moveSpecificFileWithBody(){
		from("file:C:/Users/Ghufr/OneDrive/Desktop/source1?noop=true").filter(body().startsWith("Test Text")).to("file:C:/Users/Ghufr/OneDrive/Desktop/target1");
	}
	public void fileProcess(){
		from("file:C:/Users/Ghufr/OneDrive/Desktop/source1?noop=true").process( p-> {
			String body = p.getIn().getBody(String.class);
			StringBuilder sb = new StringBuilder();
			Arrays.stream(body.split(" ")).forEach( s -> { sb.append(s + ",");
			});
			p.getIn().setBody(sb);
		}).to("file:C:/Users/Ghufr/OneDrive/Desktop/target1?fileName=record.csv");  //fileName can be used if you want to
	}																				//store the record in a particular file

	public  void multiFileProcessor(){
		from("file:C:/Users/Ghufr/OneDrive/Desktop/source1?noop=true").unmarshal().csv().split(body().tokenize(",")).choice()
				.when(body().contains("closed")).to("file:C:/Users/Ghufr/OneDrive/Desktop/target1?fileName=Closed.csv")
				.when(body().contains("Pending")).to("file:C:/Users/Ghufr/OneDrive/Desktop/target1?fileName=Pending.csv")
				.when(body().contains("Interest")).to("file:C:/Users/Ghufr/OneDrive/Desktop/target1?fileName=Interest.csv");
	}
}
