package br.com.alura.school.enroll;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;

interface EnrollRepository extends JpaRepository<Enroll, Long> {

	boolean existsByCourseAndUser(Course course, User user);
	
	@Query("SELECT new br.com.alura.school.enroll.EnrollUserReportResponse(e.user.email, COUNT(e.user)) FROM Enroll e GROUP BY e.user ORDER BY COUNT(e.user) DESC")
	List<EnrollUserReportResponse> findEnrolledUsersReport();

}
