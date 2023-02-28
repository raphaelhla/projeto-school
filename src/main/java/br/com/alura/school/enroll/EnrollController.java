package br.com.alura.school.enroll;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

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
import br.com.alura.school.user.UserRepository;

@RestController
class EnrollController {

	private final EnrollRepository enrollRepository;
	private final CourseRepository courseRepository;
	private final UserRepository userRepository;
	
	EnrollController(EnrollRepository enrollRepository, CourseRepository courseRepository, UserRepository userRepository) {
		this.enrollRepository = enrollRepository;
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
	}

	@PostMapping("/courses/{courseCode}/enroll")
	ResponseEntity<Void> newEnroll(@PathVariable("courseCode") String courseCode, @RequestBody @Valid NewEnrollRequest newEnrollRequest) {
		
		Course course = courseRepository.findByCode(courseCode).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("Course with code %s not found", courseCode)));
        User user = userRepository.findByUsername(newEnrollRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, format("User %s not found", newEnrollRequest.getUsername())));
        
        boolean isEnrolled = enrollRepository.existsByCourseAndUser(course, user);
        
		if (isEnrolled) {
			throw new ResponseStatusException(BAD_REQUEST, format("User %s is already enrolled in Curse %s", courseCode, newEnrollRequest.getUsername()));
		}
        
		enrollRepository.save(new Enroll(course, user));

        return ResponseEntity.created(null).build();
	}
	
	@GetMapping("/courses/enroll/report")
	ResponseEntity<List<EnrollUserReportResponse>> getEnrollReport() {
		
		List<EnrollUserReportResponse> enrolledUsers = enrollRepository.findEnrolledUsersReport();
		
		if (enrolledUsers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(enrolledUsers);
	}
	
}
