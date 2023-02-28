package br.com.alura.school.matricula;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.school.course.Course;
import br.com.alura.school.course.CourseRepository;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserEnrollsReportResponse;
import br.com.alura.school.user.UserRepository;

@RestController
class MatriculaController {

	private final MatriculaRepository matriculaRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	
	MatriculaController(MatriculaRepository matriculaRepository, CourseRepository courseRepository, UserRepository userRepository) {
		this.matriculaRepository = matriculaRepository;
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
	}

	@PostMapping("/courses/{courseCode}/enroll")
	ResponseEntity<Void> newMatricula(@PathVariable("courseCode") String courseCode, @RequestBody @Valid NewMatriculaRequest newMatriculaRequest) {
		
		Course course = courseRepository.findByCode(courseCode).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("Course with code %s not found", courseCode)));
        User user = userRepository.findByUsername(newMatriculaRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("User %s not found", newMatriculaRequest.getUsername())));
        
        boolean estaMatriculado = matriculaRepository.existsByCourseAndUser(course, user);
        
		if (estaMatriculado) {
			throw new ResponseStatusException(BAD_REQUEST, format("User %s is already enrolled in Curse %s", courseCode, newMatriculaRequest.getUsername()));
		}
        
        matriculaRepository.save(new Matricula(course, user));

        return ResponseEntity.created(null).build();
	}
	
	@GetMapping("/courses/enroll/report")
	ResponseEntity<List<UserEnrollsReportResponse>> getRelatorioMatriculas() {
		
		Set<User> usersMatriculados = matriculaRepository.findUsersMatriculados();
		
		if (usersMatriculados.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		List<UserEnrollsReportResponse> response = usersMatriculados
				.stream()
				.map(user -> new UserEnrollsReportResponse(user))
				.collect(Collectors.toList());
		
		Collections.sort(response); // Ordena pela quantidade de matricula.
		
		return ResponseEntity.ok(response);
	}
	
}
