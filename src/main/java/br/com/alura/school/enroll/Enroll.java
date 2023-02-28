package br.com.alura.school.enroll;

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
class Enroll {

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
    private LocalDate enrollDate;

	@Deprecated
	protected Enroll() { }

	Enroll(Course course, User user) {
		this.course = course;
		this.user = user;
		this.enrollDate = LocalDate.now();
	}
    
	Course getCourse() {
		return course;
	}

	User getUser() {
		return user;
	}

	LocalDate getEnrollDate() {
		return enrollDate;
	}	
}
