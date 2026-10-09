package com.timetable;


import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static TimetableService service =
            new TimetableService();

    public static void main(String[] args) {

        FileManager.createDataFolder();

        System.out.println(
            "===================================="
        );
        System.out.println(
            " WEEKLY CLASS TIMETABLE PLANNER"
        );
        System.out.println(
            "===================================="
        );

        showMenu();

        scanner.close();
    }

    public static void showMenu() {

        while (true) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Teacher menu");
            System.out.println("2. Student menu");
            System.out.println("0. Exit");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    teacherMenu();
                    break;

                case 2:
                    studentMenu();
                    break;

                case 0:
                    System.out.println(
                        "Thank you for using the planner."
                    );
                    return;

                default:
                    System.out.println(
                        "Invalid choice."
                    );
            }
        }
    }

    public static void teacherMenu() {

        while (true) {

            System.out.println(
                "\n========== TEACHER MENU =========="
            );

            System.out.println("1. Add subject");
            System.out.println("2. View subjects");
            System.out.println("3. Assign period");
            System.out.println("4. Clear period");
            System.out.println("5. View timetable and reports");
            System.out.println("0. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:

                    String name =
                            readText("Enter subject name: ");

                    String code =
                            readText("Enter subject code: ");

                    if (service.addSubject(name, code)) {
                        FileManager.saveSubjects(service);
                    }

                    break;

                case 2:

                    service.showSubjects();
                    break;

                case 3:

                    service.showSubjects();

                    int day =
                            readInt("Enter day (1-5): ");

                    int period =
                            readInt("Enter period (1-3): ");

                    int subjectId =
                            readInt("Enter subject ID: ");

                    if (service.assignPeriod(
                            day,
                            period,
                            subjectId)) {

                        FileManager.saveTimetable(service);
                    }

                    break;

                case 4:

                    int clearDay =
                            readInt("Enter day (1-5): ");

                    int clearPeriod =
                            readInt("Enter period (1-3): ");

                    service.clearPeriod(
                            clearDay,
                            clearPeriod
                    );

                    FileManager.saveTimetable(service);

                    break;

                case 5:

                    service.showTimetable();
                    service.showReport();

                    FileManager.saveReport(service);

                    break;

                case 0:
                    return;

                default:
                    System.out.println(
                        "Invalid choice."
                    );
            }
        }
    }

    public static void studentMenu() {

        while (true) {

            System.out.println(
                "\n========== STUDENT MENU =========="
            );

            System.out.println("1. View weekly timetable");
            System.out.println("2. View subject totals");
            System.out.println("0. Back");

            int choice =
                    readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    service.showTimetable();
                    break;

                case 2:
                    service.showReport();
                    break;

                case 0:
                    return;

                default:
                    System.out.println(
                        "Invalid choice."
                    );
            }
        }
    }

    public static int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine();

            try {

                return Integer.parseInt(
                    input.trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                    "Please enter a valid number."
                );
            }
        }
    }

    public static String readText(String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(
                "Input cannot be empty."
            );
        }
    }
}