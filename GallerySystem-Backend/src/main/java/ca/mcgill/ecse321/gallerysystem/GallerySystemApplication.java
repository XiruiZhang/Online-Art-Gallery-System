package ca.mcgill.ecse321.gallerysystem;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class GallerySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(GallerySystemApplication.class, args);
	}
	
	@RequestMapping("/")
	  public String greeting(){
	    return "Welcome to Group 10 Gallery System!";
	  }

}
