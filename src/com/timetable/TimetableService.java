package com.timetable;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class TimetableService {

    private ArrayList<Subject> subjects;
    private Set<String> codes;
    private WeeklyTimetable timetable;

    private int nextSubjectId = 1;

    private String[] days = {
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday"
    };

    public TimetableService() {
        subjects = new ArrayList<>();
        codes = new HashSet<>();
        timetable = new WeeklyTimetable();
    }

    public boolean addSubject(String name, String code) {

        name = name.trim();
        code = code.trim().toUpperCase(Locale.ROOT);

        if (name.isEmpty() || code.isEmpty()) {
            System.out.println("Name and code cannot be empty.");
            return false;
        }

        if (name.contains("|") || code.contains("|")) {
            System.out.println("Subject name/code cannot contain |");
            return false;
        }

        if (codes.contains(code)) {
            System.out.println("Subject code already exists.");
            return false;
        }

        Subject subject = new Subject(nextSubjectId, name, code);

        subjects.add(subject);
        codes.add(code);

        System.out.println("Subject " + nextSubjectId + " added.");

        nextSubjectId++;

        return true;
    }

    public void showSubjects() {

        if (subjects.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        System.out.println("\n--- SUBJECTS ---");

        for (Subject subject : subjects) {
            System.out.println(subject);
        }
    }

    public boolean assignPeriod(int day, int period, int subjectId) {

        if (day < 1 || day > 5) {
            System.out.println("Invalid day. Choose 1 to 5.");
            return false;
        }

        if (period < 1 || period > 3) {
            System.out.println("Invalid period. Choose 1 to 3.");
            return false;
        }

        Subject subject = findSubject(subjectId);

        if (subject == null) {
            System.out.println("Unknown subject ID.");
            return false;
        }

        timetable.assign(day - 1, period - 1, subjectId);

        System.out.println("Period assigned successfully.");

        return true;
    }

    public void clearPeriod(int day, int period) {

        if (day < 1 || day > 5 ||
            period < 1 || period > 3) {

            System.out.println("Invalid day or period.");
            return;
        }

        timetable.clear(day - 1, period - 1);

        System.out.println("Period cleared.");
    }

    public void showTimetable() {

        int[][] grid = timetable.copyGrid();

        System.out.println("\n========== WEEKLY TIMETABLE ==========");

        for (int i = 0; i < 5; i++) {

            System.out.println("\n" + days[i]);

            for (int j = 0; j < 3; j++) {

                int subjectId = grid[i][j];

                if (subjectId == 0) {
                    System.out.println(
                        "Period " + (j + 1) + ": Free"
                    );
                } else {

                    Subject subject = findSubject(subjectId);

                    if (subject != null) {
                        System.out.println(
                            "Period " + (j + 1) + ": "
                            + subject.getName()
                        );
                    }
                }
            }
        }
    }

    public void showReport() {

        int[][] grid = timetable.copyGrid();

        int occupied = 0;

        for (int[] day : grid) {
            for (int subjectId : day) {

                if (subjectId != 0) {
                    occupied++;
                }
            }
        }

        int free = 15 - occupied;

        System.out.println("\n========== REPORT ==========");

        System.out.println("Occupied periods: " + occupied);
        System.out.println("Free periods: " + free);

        System.out.println("\nSubject totals:");

        for (Subject subject : subjects) {

            int count = 0;

            for (int[] day : grid) {

                for (int subjectId : day) {

                    if (subjectId == subject.getId()) {
                        count++;
                    }
                }
            }

            System.out.println(
                subject.getName() + " periods: " + count
            );
        }
    }

    private Subject findSubject(int id) {

        for (Subject subject : subjects) {

            if (subject.getId() == id) {
                return subject;
            }
        }

        return null;
    }

    public int[][] getGrid() {
        return timetable.copyGrid();
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public String[] getDays() {
        return days.clone();
    }
}