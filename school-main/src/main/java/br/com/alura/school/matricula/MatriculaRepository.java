package br.com.alura.school.matricula;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;

interface MatriculaRepository extends JpaRepository<Matricula, Long> {

	boolean existsByCourseAndUser(Course course, User user);
	
	@Query("SELECT m.user FROM Matricula m")
	Set<User> findUsersMatriculados();

}
