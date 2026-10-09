package com.timetable;

public class WeeklyTimetable {

    private int[][] subjectIds;

    public WeeklyTimetable() {
        subjectIds = new int[5][3];
    }

    public void assign(int day, int period, int subjectId) {
        subjectIds[day][period] = subjectId;
    }

    public void clear(int day, int period) {
        subjectIds[day][period] = 0;
    }

    public int[][] copyGrid() {
        int[][] copy = new int[5][3];

        for (int i = 0; i < 5; i++) {
            copy[i] = subjectIds[i].clone();
        }

        return copy;
    }
}