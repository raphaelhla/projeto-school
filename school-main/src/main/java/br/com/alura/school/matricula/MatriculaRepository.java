package br.com.alura.school.matricula;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;
import br.com.alura.school.user.UserEnrollsReportResponse;

interface MatriculaRepository extends JpaRepository<Matricula, Long> {

	boolean existsByCourseAndUser(Course course, User user);
	
	@Query("SELECT new br.com.alura.school.user.UserEnrollsReportResponse(m.user.email, COUNT(m.user)) FROM Matricula m GROUP BY m.user ORDER BY COUNT(m.user) DESC")
	List<UserEnrollsReportResponse> findUsersEnrolled();

}
