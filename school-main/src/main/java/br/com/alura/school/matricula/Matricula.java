package br.com.alura.school.matricula;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
    @Column(nullable = false)
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
