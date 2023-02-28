package br.com.alura.school.matricula;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MatriculaControllerTest {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    
    // TESTES ABAIXO REFERENTES À MATRICULA
    
    @Test
    void should_enroll_user_in_course() throws Exception {
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
        userRepository.save(new User("ana", "ana@email.com"));

        NewMatriculaRequest newMatriculaRequest = new NewMatriculaRequest();
        newMatriculaRequest.setUsername("ana");
        
        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newMatriculaRequest)))
                .andExpect(status().isCreated());
    }
    
    @Test
    void should_not_enroll_duplicate_user_in_course() throws Exception {
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
        userRepository.save(new User("ana", "ana@email.com"));

        NewMatriculaRequest newMatriculaRequest = new NewMatriculaRequest();
        newMatriculaRequest.setUsername("ana");
        
        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newMatriculaRequest)));
        
        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newMatriculaRequest)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void should_not_enroll_user_in_course_when_user_nonexistent() throws Exception {
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));

        NewMatriculaRequest newMatriculaRequest = new NewMatriculaRequest();
        newMatriculaRequest.setUsername("anaaaaaa");
        
        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newMatriculaRequest)))
        		.andExpect(status().isNotFound());
    }
    
    @Test
    void should_not_enroll_user_in_course_when_course_nonexistent() throws Exception {
        userRepository.save(new User("ana", "ana@email.com"));

        NewMatriculaRequest newMatriculaRequest = new NewMatriculaRequest();
        newMatriculaRequest.setUsername("ana");
        
        mockMvc.perform(post("/courses/javaaaaaaaa/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newMatriculaRequest)))
        		.andExpect(status().isNotFound());
    }
    
    @Test
    void should_not_enroll_user_in_course_when_username_null() throws Exception {
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
        userRepository.save(new User("ana", "ana@email.com"));
        
        NewMatriculaRequest newMatriculaRequest = new NewMatriculaRequest();
        newMatriculaRequest.setUsername(null);
        
        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newMatriculaRequest)))
        		.andExpect(status().isBadRequest());
    }
    
    @Test
    void should_not_enroll_user_in_course_when_username_empty() throws Exception {
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
        userRepository.save(new User("ana", "ana@email.com"));
        
        NewMatriculaRequest newMatriculaRequest = new NewMatriculaRequest();
        newMatriculaRequest.setUsername("");
        
        mockMvc.perform(post("/courses/java-1/enroll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newMatriculaRequest)))
        		.andExpect(status().isBadRequest());
    }
    
    
    
    // TESTES ABAIXO REFERENTES AO RELATÓRIO DE MATRICULA
    
    @Test
    void should_not_report_users_and_enrollment_when_noexists_users_enrolled() throws Exception {
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
        userRepository.save(new User("ana", "ana@email.com"));

        mockMvc.perform(get("/courses/enroll/report")
                        .accept(MediaType.APPLICATION_JSON))
		                .andExpect(status().isNoContent());
    }
    
    @Test
    void should_return_report_users_and_enrollment() throws Exception {
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
        userRepository.save(new User("ana", "ana@email.com"));

        NewMatriculaRequest newMatriculaRequest = new NewMatriculaRequest();
        newMatriculaRequest.setUsername("ana");

        mockMvc.perform(post("/courses/java-1/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newMatriculaRequest)));

        mockMvc.perform(get("/courses/enroll/report")
                        .accept(MediaType.APPLICATION_JSON))
        				.andExpect(status().isOk())
		                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		                .andExpect(jsonPath("$.length()", is(1)))
		                .andExpect(jsonPath("$[0].email", is("ana@email.com")))
		                .andExpect(jsonPath("$[0].quantidade_matriculas", is(1)));
    }
    
    @Test
    void should_return_report_users_and_enrollment_2() throws Exception {
        courseRepository.save(new Course("java-1", "Java OO", "Java and Object Orientation: Encapsulation, Inheritance and Polymorphism."));
        courseRepository.save(new Course("spring-1", "Spring Basics", "Spring Core and Spring MVC."));
        courseRepository.save(new Course("spring-2", "Spring Boot", "Spring Boot"));
        
        userRepository.save(new User("ana", "ana@email.com"));
        userRepository.save(new User("alex", "alex@email.com"));
        userRepository.save(new User("raphael", "raphael@email.com"));

        NewMatriculaRequest newMatriculaRequest1 = new NewMatriculaRequest();
        newMatriculaRequest1.setUsername("ana");

        // Matriculando ana em 3 cursos
        mockMvc.perform(post("/courses/java-1/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(newMatriculaRequest1)));
        mockMvc.perform(post("/courses/spring-1/enroll")
        				.contentType(MediaType.APPLICATION_JSON)
        				.content(jsonMapper.writeValueAsString(newMatriculaRequest1)));
        mockMvc.perform(post("/courses/spring-2/enroll")
		                .contentType(MediaType.APPLICATION_JSON)
		                .content(jsonMapper.writeValueAsString(newMatriculaRequest1)));
        
        
        NewMatriculaRequest newMatriculaRequest2 = new NewMatriculaRequest();
        newMatriculaRequest2.setUsername("alex");

        // Matriculando alex em 1 curso
        mockMvc.perform(post("/courses/spring-2/enroll")
		                .contentType(MediaType.APPLICATION_JSON)
		                .content(jsonMapper.writeValueAsString(newMatriculaRequest2)));
        
        
        
        NewMatriculaRequest newMatriculaRequest3 = new NewMatriculaRequest();
        newMatriculaRequest3.setUsername("raphael");
        
        // Matriculando raphael em 2 cursos
        mockMvc.perform(post("/courses/spring-1/enroll")
		                .contentType(MediaType.APPLICATION_JSON)
		                .content(jsonMapper.writeValueAsString(newMatriculaRequest3)));
        mockMvc.perform(post("/courses/spring-2/enroll")
		                .contentType(MediaType.APPLICATION_JSON)
		                .content(jsonMapper.writeValueAsString(newMatriculaRequest3)));

        
        
        mockMvc.perform(get("/courses/enroll/report")
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$[0].email", is("alex@email.com")))
                .andExpect(jsonPath("$[0].quantidade_matriculas", is(1)))
                .andExpect(jsonPath("$[1].email", is("raphael@email.com")))
                .andExpect(jsonPath("$[1].quantidade_matriculas", is(2)))
                .andExpect(jsonPath("$[2].email", is("ana@email.com")))
                .andExpect(jsonPath("$[2].quantidade_matriculas", is(3)));
    }
}
