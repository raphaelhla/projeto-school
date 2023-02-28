package br.com.alura.school.matricula;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

	boolean existsByCourseAndUser(Course course, User user);

}
