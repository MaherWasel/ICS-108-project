package com.example;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Student  {
  
    private List<String> listOfCompletedCourses = new ArrayList<>();
    public Student(File FinishedCourses) {
        try {

            Scanner scan = new Scanner(FinishedCourses);

            while (scan.hasNextLine()) {
                String[] seperatedLine = scan.nextLine().split(",");
                listOfCompletedCourses.add(seperatedLine[0]);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> getList() {
        return listOfCompletedCourses;
    }
}

