// Problem 3 - Improved cohesion (split responsibilities so LCOM4 <= 1)

class EmployeeDetails {
    private String name;
    private int id;

    public EmployeeDetails(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void printEmployeeDetails() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
    }
}

class EmployeeSalary {
    private double salary;

    public EmployeeSalary(double salary) {
        this.salary = salary;
    }

    public void giveRaise(double percent) {
        salary += salary * percent / 100.0;
    }

    public double getSalary() {
        return salary;
    }
}

class EmployeeDepartment {
    private String department;

    public EmployeeDepartment(String department) {
        this.department = department;
    }

    public void changeDepartment(String newDepartment) {
        department = newDepartment;
    }

    public String getDepartment() {
        return department;
    }
}

class EmployeeAddress {
    private String address;

    public EmployeeAddress(String address) {
        this.address = address;
    }

    public void updateAddress(String newAddress) {
        address = newAddress;
    }

    public String getAddress() {
        return address;
    }
}

public class Main {
    public static void main(String[] args) {
        EmployeeDetails details = new EmployeeDetails("John Doe", 101);
        EmployeeSalary salary = new EmployeeSalary(50000);
        EmployeeDepartment dept = new EmployeeDepartment("IT");
        EmployeeAddress addr = new EmployeeAddress("123 Main St");

        details.printEmployeeDetails();

        salary.giveRaise(10);
        dept.changeDepartment("HR");
        addr.updateAddress("456 Park Ave");

        System.out.println("Salary: " + salary.getSalary());
        System.out.println("Department: " + dept.getDepartment());
        System.out.println("Address: " + addr.getAddress());
    }
}
