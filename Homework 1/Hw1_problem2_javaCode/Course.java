public class Course {

	public String courseID;

	public String courseName;

	private String instructor;

	private int maxCapacity;

	private ArrayList<String> enrolledStudents;

	private Student[] student;

	private Department department;

	public boolean addStudent() {
		return false;
	}

	public boolean removeStudent() {
		return false;
	}

	public int getAvailableSeats() {
		return 0;
	}

}
