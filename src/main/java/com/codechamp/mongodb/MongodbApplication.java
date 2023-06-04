package com.codechamp.mongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MongodbApplication {

	public static void main(String[] args) {

		SpringApplication.run(MongodbApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate){
		return args -> {
			Address address  = new Address(
					"Kenya",
					"Nairobi",
					"Kanairo"
			);
			String email = "njunjimuchai@yahoo.com";
			Student student = new Student(
					"Njunji",
					"Muchai",
					"njunjimuchai@yahoo.com",
					Gender.MALE,
					address,
					List.of("Computer Science", "Biology"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);
			//usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);

			repository.findStudentByEmail(email)
					.ifPresentOrElse(s ->{
						System.out.println(s + "already exists");
					}, ()-> {
						System.out.println("Inserting student"+ student);
						repository.insert(student);
					});
		};
	}

	private static void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {

		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if(students.size()>1){
			throw new IllegalStateException("found many students with email" + email);
		}
		if (students.isEmpty()){
			System.out.println("Inserting student"+ student);
			repository.insert(student);
		} else {
			System.out.println(student + "already exists");
		}
	}

}
