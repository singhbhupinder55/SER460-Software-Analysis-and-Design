public class Student extends Person implements Gradable {

	public String studentID;

	private double gpa;

	private ArrayList<String> enrolledCourses;

	private Course[] course;

	public void enrollInCourse() {

	}

	public double calculateGPA() {
		return 0;
	}

	public void dropCourse() {

	}


	/**
	 * @see Gradable#calculateGrade()
	 */
	public double calculateGrade() {
		return 0;
	}

}
