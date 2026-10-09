package com.timetable;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    private static final Path DATA_FOLDER =
            Paths.get("data");

    private static final Path SUBJECT_FILE =
            DATA_FOLDER.resolve("subjects.txt");

    private static final Path TIMETABLE_FILE =
            DATA_FOLDER.resolve("timetable.txt");

    private static final Path REPORT_FILE =
            DATA_FOLDER.resolve("report.txt");

    public static void createDataFolder() {

        try {
            Files.createDirectories(DATA_FOLDER);
        } catch (IOException e) {
            System.out.println(
                "Unable to create data folder."
            );
        }
    }

    public static void saveSubjects(
            TimetableService service) {

        try {

            createDataFolder();

            try (BufferedWriter writer =
                    Files.newBufferedWriter(
                        SUBJECT_FILE,
                        StandardCharsets.UTF_8)) {

                for (Subject subject :
                        service.getSubjects()) {

                    writer.write(
                        subject.getId()
                        + "|"
                        + subject.getName()
                        + "|"
                        + subject.getCode()
                    );

                    writer.newLine();
                }
            }

            System.out.println("Subjects saved.");

        } catch (IOException e) {

            System.out.println(
                "Error saving subjects: "
                + e.getMessage()
            );
        }
    }

    public static void saveTimetable(
            TimetableService service) {

        try {

            createDataFolder();

            int[][] grid = service.getGrid();

            try (BufferedWriter writer =
                    Files.newBufferedWriter(
                        TIMETABLE_FILE,
                        StandardCharsets.UTF_8)) {

                for (int i = 0; i < 5; i++) {

                    writer.write(
                        (i + 1)
                        + "|"
                        + grid[i][0]
                        + "|"
                        + grid[i][1]
                        + "|"
                        + grid[i][2]
                    );

                    writer.newLine();
                }
            }

            System.out.println("Timetable saved.");

        } catch (IOException e) {

            System.out.println(
                "Error saving timetable: "
                + e.getMessage()
            );
        }
    }

    public static void saveReport(
            TimetableService service) {

        try {

            createDataFolder();

            int[][] grid = service.getGrid();

            int occupied = 0;

            for (int[] day : grid) {
                for (int id : day) {

                    if (id != 0) {
                        occupied++;
                    }
                }
            }

            int free = 15 - occupied;

            try (BufferedWriter writer =
                    Files.newBufferedWriter(
                        REPORT_FILE,
                        StandardCharsets.UTF_8)) {

                writer.write(
                    "Occupied periods: " + occupied
                );

                writer.newLine();

                writer.write(
                    "Free periods: " + free
                );

                writer.newLine();

                for (Subject subject :
                        service.getSubjects()) {

                    int count = 0;

                    for (int[] day : grid) {

                        for (int id : day) {

                            if (id == subject.getId()) {
                                count++;
                            }
                        }
                    }

                    writer.write(
                        subject.getName()
                        + " periods: "
                        + count
                    );

                    writer.newLine();
                }
            }

            System.out.println("Report saved.");

        } catch (IOException e) {

            System.out.println(
                "Error saving report: "
                + e.getMessage()
            );
        }
    }
}