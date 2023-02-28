package br.com.alura.school.matricula;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;

@Entity
public class Matricula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "course_code", referencedColumnName = "code")
	private Course course;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "user_username", referencedColumnName = "username")
	private User user;
	
    @Column(name = "data_matricula")
    private LocalDate dataMatricula;

	@Deprecated
	protected Matricula() { }

	public Matricula(Course course, User user) {
		this.course = course;
		this.user = user;
		this.dataMatricula = LocalDate.now();
	}
    
	public Course getCourse() {
		return course;
	}

	public User getUser() {
		return user;
	}

	public LocalDate getDataMatricula() {
		return dataMatricula;
	}	
}
