package com.example.studentmanagement;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StudentManagementApp {
    private final Scanner scanner = new Scanner(System.in);
    private final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        new StudentManagementApp().run();
    }

    private void run() {
        try {
            studentDAO.initialize();
        } catch (SQLException exception) {
            System.err.println("Could not initialize the H2 database: " + exception.getMessage());
            return;
        }

        System.out.println("H2 database ready.");
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInteger("Choose an option: ");

            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> listStudents();
                    case 3 -> findStudent();
                    case 4 -> updateStudent();
                    case 5 -> deleteStudent();
                    case 0 -> running = false;
                    default -> System.out.println("Please choose a number from 0 to 5.");
                }
            } catch (SQLException exception) {
                System.out.println("Database error: " + exception.getMessage());
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Student Management System ===");
        System.out.println("1. Add a student");
        System.out.println("2. List all students");
        System.out.println("3. Find a student by ID");
        System.out.println("4. Update a student");
        System.out.println("5. Delete a student");
        System.out.println("0. Exit");
    }

    private void addStudent() throws SQLException {
        String name = readNonBlank("Name: ");
        String email = readNonBlank("Email: ");
        int id = studentDAO.create(name, email);
        System.out.println("Student added with ID " + id + ".");
    }

    private void listStudents() throws SQLException {
        List<Student> students = studentDAO.findAll();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.printf("%-5s %-30s %s%n", "ID", "NAME", "EMAIL");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void findStudent() throws SQLException {
        int id = readPositiveId();
        Optional<Student> student = studentDAO.findById(id);
        student.ifPresentOrElse(
                found -> {
                    System.out.printf("%-5s %-30s %s%n", "ID", "NAME", "EMAIL");
                    System.out.println(found);
                },
                () -> System.out.println("No student found with ID " + id + "."));
    }

    private void updateStudent() throws SQLException {
        int id = readPositiveId();
        String name = readNonBlank("New name: ");
        String email = readNonBlank("New email: ");

        if (studentDAO.update(id, name, email)) {
            System.out.println("Student updated.");
        } else {
            System.out.println("No student found with ID " + id + ".");
        }
    }

    private void deleteStudent() throws SQLException {
        int id = readPositiveId();
        System.out.print("Delete student " + id + "? (y/N): ");
        String answer = scanner.nextLine().trim();

        if (!answer.equalsIgnoreCase("y")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (studentDAO.delete(id)) {
            System.out.println("Student deleted.");
        } else {
            System.out.println("No student found with ID " + id + ".");
        }
    }

    private int readPositiveId() {
        while (true) {
            int id = readInteger("Student ID: ");
            if (id > 0) {
                return id;
            }
            System.out.println("ID must be a positive number.");
        }
    }

    private int readInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This value cannot be blank.");
        }
    }
}
