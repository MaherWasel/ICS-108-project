package com.example;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class main extends Application {

    String[] time = { "0700-0750", "0800-0850", "0900-0950", "1000-1050", "1100-1150", "1200-1250", "1300-1350", "1400-1450", "1500-1550", "1600-1650", "1700-1750" };
    String[] labTime={"07","08","09","10","11","12","13","14","15","16","17"};
    int[][] reg = new int[11][5];
    List <String> registeredCourses = new ArrayList<>();
    List <String> registeredLabs = new ArrayList<>();

    List regName = new ArrayList<>();
    ScrollPane scrollPane = new ScrollPane();
    VBox box = new VBox();
    File courseOffering = new File("CourseOffering.csv");
    List<Courses> list = CreatinLists(courseOffering);

    BorderPane firstPagePane = new BorderPane();
    Button nextButton = new Button("next");
    Button prevButton = new Button("prev");
    BorderPane secondPagePage = new BorderPane();
    Button startFromSavedSchedule = new Button("Start from a saved Schedule");
    Stage prim;
    VBox vBox = new VBox();
    ScrollPane pane = new ScrollPane();
    List<Courses> addedCourses = new ArrayList<>();
    GridPane g = new GridPane();
    File DegreePlan = new File("DegreePlan.csv");
    File FinishedCourses = new File("FinishedCourses.csv");
    List<Courses> trueList = new ArrayList<>();
    Scene scene2 ;
    Scene scene1;
    Button savedSchedule=new Button("          save schedule          ");

    public void start(Stage prim) {
        savedSchedule.setOnAction(new buttonAction());
        if (!FinishedCourses.exists()){
            System.out.println("finished not found ");
            System.exit(0);
        }
        if (!DegreePlan.exists()){
            System.out.println("DegreePlan file not found");
            System.exit(0);
        }
        if (!courseOffering.exists()){
            System.out.println("courseOffering not found");
            System.exit(0);
        }
        Student student = new Student(FinishedCourses);
        nextButton.setOnAction(new buttonAction());
        startFromSavedSchedule.setOnAction(new buttonAction());
        prevButton.setOnAction(new buttonAction());
        box.setAlignment(Pos.CENTER);
        scrollPane.setScaleX(1.0);
        scrollPane.setScaleY(0.896);
        secondPagePage.setRight(pane);
        nextButton.setScaleX(1.25);

        Text welcomingText = new Text("Add Sections to Basket");
        welcomingText.setFont(Font.font("Arial", FontWeight.BOLD, 36));

        welcomingText.setScaleX(1.8);
        startFromSavedSchedule.setMinSize(150, 30);
        nextButton.setMinSize(100, 50);
        prevButton.setMinSize(100, 50);

        firstPagePane.setBottom(nextButton);
        secondPagePage.setBottom(prevButton);
        prevButton.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        prevButton.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        firstPagePane.setCenter(scrollPane);
        BorderPane.setAlignment(scrollPane, Pos.CENTER);
        BorderPane.setAlignment(nextButton, Pos.BOTTOM_RIGHT);
        startFromSavedSchedule.setScaleY(1.5);
        startFromSavedSchedule.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        startFromSavedSchedule.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        savedSchedule.setScaleY(1.5);
        savedSchedule.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        savedSchedule.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        nextButton.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        nextButton.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        this.prim = prim;
        pane.setScaleX(1.5);
        g.setScaleX(0.95);
        g.setScaleY(0.96);

        HBox upper = new HBox();
        upper.getChildren().add(welcomingText);
        upper.getChildren().add(new Text("                                                                          "));
        upper.getChildren().add(startFromSavedSchedule);
        firstPagePane.setTop(upper);
        upper.setAlignment(Pos.CENTER);
        secondPagePage.setTop(savedSchedule);
        secondPagePage.setAlignment(savedSchedule, Pos.TOP_LEFT);
        scene2 = new Scene(secondPagePage, 1300, 600);

        firstPagePane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        scrollPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        pane.setContent(vBox);

        g.setAlignment(Pos.CENTER);
        g.setPadding(new Insets(20, 20, 20, 20));
        g.setHgap(100);
        g.setVgap(15.3);
        g.add(new Label("7 AM"), 0, 1);
        g.add(new Label("8 AM"), 0, 2);
        g.add(new Label("9 AM"), 0, 3);
        g.add(new Label("10 AM"), 0, 4);
        g.add(new Label("11 AM"), 0, 5);
        g.add(new Label("12 PM"), 0, 6);
        g.add(new Label("1 PM"), 0, 7);
        g.add(new Label("2 PM"), 0, 8);
        g.add(new Label("3 PM"), 0, 9);
        g.add(new Label("4 PM"), 0, 10);
        g.add(new Label("5 PM"), 0, 11);

        g.add(new Label("Sunday"), 1, 0);
        g.add(new Label("Monday"), 2, 0);
        g.add(new Label("Tuesday"), 3, 0);
        g.add(new Label("Wednesday"), 4, 0);
        g.add(new Label("Thursday"), 5, 0);


        for (Courses courses : list) {
            if (legal(courses.getCource(), student.getList(), DegreePlan)) {
                courses.getAddButton().setOnAction(new buttonAction());
                courses.getRemoveButton().setOnAction(new buttonAction());
                courses.getInsertingButton().setOnAction(new buttonAction());
            
                trueList.add(courses);
            }

        }
        for (Courses courses : trueList) {
            box.getChildren().add(courses.getInnerBox());

        }
        prim.setTitle("builder");
        scrollPane.setContent(box);
        secondPagePage.setCenter(g);
        scene1=new Scene(firstPagePane,1300,600);
        prim.setScene(scene1);
        prim.show();

    }
    public void animation(String error){
        HBox upperSeondPage=new HBox();

        Text e =new Text("Error occured : "+error);
        upperSeondPage.getChildren().addAll(savedSchedule);
        upperSeondPage.getChildren().addAll(new Text("                                                                                                                                             "));
        upperSeondPage.getChildren().addAll(e);
        secondPagePage.setTop(null);
        secondPagePage.setTop(upperSeondPage);
        e.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        e.setScaleX(1.3);
        secondPagePage.setAlignment(e, Pos.TOP_CENTER);





    }

    public Stage getStage() {
        return prim;
    }

    public List<Courses> CreatinLists(File file) {

        try {
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {

                scan.nextLine();
            }
            scan.close();
            scan = new Scanner(file);
            scan.nextLine();
            List<Courses> list = new ArrayList<>();

            while (scan.hasNextLine()) {

                int startIndex = 0;
                int endIndex = 0;
                String line = scan.nextLine();
                while (!Character.toString(line.charAt(endIndex)).equals("-")) {
                    endIndex++;
                }
                String course = line.substring(startIndex, endIndex);
                startIndex = endIndex + 1;
                while (!Character.toString(line.charAt(endIndex)).equals(",")) {
                    endIndex++;
                }
                String section = line.substring(startIndex, endIndex);
                startIndex = endIndex + 1;
                endIndex++;
                while (!Character.toString(line.charAt(endIndex)).equals(",")) {
                    endIndex++;
                }
                String activity = line.substring(startIndex, endIndex);
                startIndex = endIndex + 1;
                endIndex++;
                while (!Character.toString(line.charAt(endIndex)).equals(",")) {
                    endIndex++;
                }
                String CRN = line.substring(startIndex, endIndex);
                startIndex = endIndex + 1;

                endIndex++;

                while (!Character.toString(line.charAt(endIndex)).equals(",")) {
                    endIndex++;
                }
                String name = line.substring(startIndex, endIndex);
                startIndex = endIndex + 1;

                endIndex++;
                while (!Character.toString(line.charAt(endIndex)).equals(",")) {
                    endIndex++;
                }
                String teacher = line.substring(startIndex, endIndex);
                startIndex = endIndex + 1;

                endIndex++;
                while (!Character.toString(line.charAt(endIndex)).equals(",")) {
                    endIndex++;
                }
                String day = line.substring(startIndex, endIndex);
                startIndex = endIndex + 1;
                endIndex++;
                while (!Character.toString(line.charAt(endIndex)).equals(",")) {
                    endIndex++;
                }
                String time = line.substring(startIndex, endIndex);
                startIndex = endIndex + 1;
                endIndex++;
                while (!Character.toString(line.charAt(endIndex)).equals(",")) {
                    endIndex++;
                }
                String location = line.substring(startIndex, endIndex);
                list.add(new Courses(course, section, activity, CRN, name, teacher, day, time, location));


            }
            return list;

        } catch (FileNotFoundException e) {
            return null;
        }

    }

    class buttonAction implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            for (int i = 0; i < trueList.size(); i++) {

                if (e.getSource() == trueList.get(i).getAddButton()) {

                    int j = 0;

                    while (j < trueList.size()) {

                        if (trueList.get(i).getCRN().equals(trueList.get(j).getCRN())) {

                            trueList.get(j).getRemoveButton().setVisible(true);
                            trueList.get(j).getAddButton().setVisible(false);
                            addedCourses.add(trueList.get(j));


                        }
                        j++;
                    }

                }
                else if(e.getSource()==savedSchedule){
                    try{
                        FileOutputStream file=new FileOutputStream("saved.dat");
                        ObjectOutputStream out=new ObjectOutputStream(file);

                        out.writeObject((Object)registeredCourses);




                    }catch (IOException D){
                        System.out.println(D);
                    }
                    break;
                }



                else if (e.getSource() == trueList.get(i).getRemoveButton()) {

                    int j = 0;
                    while (j < trueList.size()) {
                        if (trueList.get(i).getCRN().equals(trueList.get(j).getCRN())) {
                            trueList.get(j).getRemoveButton().setVisible(false);
                            trueList.get(j).getAddButton().setVisible(true);
                            addedCourses.remove(trueList.get(j));

                        }
                        j++;
                    }

                } else if (e.getSource() == nextButton) {

                    vBox =new VBox();


                    for (int k = 0; k < addedCourses.size(); k++) {

                        vBox.getChildren().add(addedCourses.get(k).getInserting());


                    }


                    secondPagePage.setRight(vBox);
                    prim.setScene(scene2);
                    prim.show();
                    break;

                }
                else if (e.getSource()==prevButton){

                    prim.setScene(scene1);
                    prim.show();
                    break;
                }


                else if (e.getSource() == startFromSavedSchedule) {
                    try{
                        FileInputStream fil=new FileInputStream("saved.dat");
                        ObjectInputStream in=new ObjectInputStream(fil);
                        ArrayList<Courses> array=(ArrayList<Courses>)in.readObject();

                        g=new GridPane();
                        g.setAlignment(Pos.CENTER);
                        g.setPadding(new Insets(20, 20, 20, 20));
                        g.setHgap(100);
                        g.setVgap(15.3);
                        g.add(new Label("7 AM"), 0, 1);
                        g.add(new Label("8 AM"), 0, 2);
                        g.add(new Label("9 AM"), 0, 3);
                        g.add(new Label("10 AM"), 0, 4);
                        g.add(new Label("11 AM"), 0, 5);
                        g.add(new Label("12 PM"), 0, 6);
                        g.add(new Label("1 PM"), 0, 7);
                        g.add(new Label("2 PM"), 0, 8);
                        g.add(new Label("3 PM"), 0, 9);
                        g.add(new Label("4 PM"), 0, 10);
                        g.add(new Label("5 PM"), 0, 11);

                        g.add(new Label("Sunday"), 1, 0);
                        g.add(new Label("Monday"), 2, 0);
                        g.add(new Label("Tuesday"), 3, 0);
                        g.add(new Label("Wednesday"), 4, 0);
                        g.add(new Label("Thursday"), 5, 0);
                        addedCourses=new ArrayList<>();
                        vBox=new VBox();
                        for (int p=0;p<array.size();p++){
                            for (int j=trueList.size()-1;j>=0;j--){
                                if (trueList.get(j).getCRN().equals(array.get(p))&&!addedCourses.contains(trueList.get(j))){
                                    trueList.get(j).getAddButton().fire();

                                }
                            }

                        }
                        for (int j=addedCourses.size()-1;j>=0;j--){
                            addedCourses.get(j).getInserting().fire();
                        }



                    }catch(IOException ex){
                        System.out.println(ex);
                    }
                    catch (ClassNotFoundException ex){
                        System.out.println("class not found exception");
                    }
                    break;
                }

                else if (e.getSource() == trueList.get(i).getInserting()) {
                   


                    for (int a = 0; a < 11; a++) {
                        if (trueList.get(i).getDays().equals("UTR")) {
                            if (trueList.get(i).getTime().equals(time[a])) {
                                if (regName.contains(trueList.get(i).getCource())) {
                                    animation("duplicate");
                                    break;
                                }
                                if (reg[a][0] == 0 && reg[a][2] == 0 && reg[a][4] == 0) {
                                    registeredCourses.add(trueList.get(i).getCRN());
                                    regName.add(trueList.get(i).getCource());
                                    regName.add(trueList.get(i).getCRN());
                                    regName.add(trueList.get(i).getSection());

                                    reg[a][0] = 1;
                                    reg[a][2] = 1;
                                    reg[a][4] = 1;

                                    Button b1 = new Button(trueList.get(i).getCource());
                                    Button b2 = new Button(trueList.get(i).getCource());
                                    Button b3 = new Button(trueList.get(i).getCource());


                                    b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                    b2.setBorder(new Border(new BorderStroke(Color.BLACK,
                                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                    b3.setBorder(new Border(new BorderStroke(Color.BLACK,
                                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                                    g.add(b1, 1, a + 1);
                                    g.add(b2, 3, a + 1);
                                    g.add(b3, 5, a + 1);

                                    final int finalA = a;
                                    final int finalIndex = i;
                                    b1.setOnAction(actionEvent -> {
                                        reg[finalA][0] = 0;
                                        reg[finalA][2] = 0;
                                        reg[finalA][4] = 0;
                                        b1.setVisible(false);
                                        b2.setVisible(false);
                                        b3.setVisible(false);
                                        registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                        regName.remove(trueList.get(finalIndex).getCource());
                                        regName.remove(trueList.get(finalIndex).getCRN());
                                        regName.remove(trueList.get(finalIndex).getSection());
                                    });
                                    b3.setOnAction(actionEvent -> {
                                        reg[finalA][0] = 0;
                                        reg[finalA][2] = 0;
                                        reg[finalA][4] = 0;
                                        b1.setVisible(false);
                                        b2.setVisible(false);
                                        b3.setVisible(false);
                                        registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                        regName.remove(trueList.get(finalIndex).getCource());
                                        regName.remove(trueList.get(finalIndex).getCRN());
                                        regName.remove(trueList.get(finalIndex).getSection());
                                    });
                                    b2.setOnAction(actionEvent -> {
                                        reg[finalA][0] = 0;
                                        reg[finalA][2] = 0;
                                        reg[finalA][4] = 0;
                                        b1.setVisible(false);
                                        b2.setVisible(false);
                                        b3.setVisible(false);
                                        registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                        regName.remove(trueList.get(finalIndex).getCource());
                                        regName.remove(trueList.get(finalIndex).getCRN());
                                        regName.remove(trueList.get(finalIndex).getSection());
                                    });

                                    break;
                                }
                                else {
                                    animation("conflict");
                                }
                            }
                        }
                        else if (trueList.get(i).getDays().equals("MW")) {
                            if (trueList.get(i).getTime().substring(0, 2).equals(labTime[a])) {
                                if (regName.contains(trueList.get(i).getCource())) {
                                    animation("duplicate");
                                    break;
                                }
                                if (trueList.get(i).getName().equals("Object-Oriented Programming")) {
                                    if (reg[a][1] == 0 && reg[a + 1][1] == 0 && reg[a + 2][1] == 0 && reg[a][3] == 0 && reg[a + 1][3] == 0 && reg[a + 2][3] == 0) {
                                        registeredCourses.add(trueList.get(i).getCRN());
                                        regName.add(trueList.get(i).getCource());
                                        reg[a][1] = 1;
                                        reg[a + 1][1] = 1;
                                        reg[a + 2][1] = 1;
                                        reg[a][3] = 1;
                                        reg[a + 1][3] = 1;
                                        reg[a + 2][3] = 1;
                                        Button b1 = new Button(trueList.get(i).getCource()+"\n\n\n\n\n\n");
                                        b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                        g.add(b1,2,a+1,3,3);

                                        Button b2 = new Button(trueList.get(i).getCource()+"\n\n\n\n\n\n");
                                        b2.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                        g.add(b2,4,a+1,3,3);

                                        final int finalA = a;

                                        final int finalIndex = i;
                                        b1.setOnAction(actionEvent -> {
                                            reg[finalA][1] = 0;
                                            reg[finalA+1][1] = 0;
                                            reg[finalA+2][1] = 0;
                                            reg[finalA][3] = 0;
                                            reg[finalA+1][3] = 0;
                                            reg[finalA+2][3] = 0;
                                            b1.setVisible(false);
                                            b2.setVisible(false);
                                            registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                            regName.remove(trueList.get(finalIndex).getCource());
                                        });

                                        b2.setOnAction(actionEvent -> {
                                            reg[finalA][1] = 0;
                                            reg[finalA+1][1] = 0;
                                            reg[finalA+2][1] = 0;
                                            reg[finalA][3] = 0;
                                            reg[finalA+1][3] = 0;
                                            reg[finalA+2][3] = 0;
                                            b1.setVisible(false);
                                            b2.setVisible(false);
                                            registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                            regName.remove(trueList.get(finalIndex).getCource());

                                        });
                                    }
                                    else {
                                        animation("conflict");
                                    }
                                }
                                else if (reg[a][1] == 0 && reg[a][3] == 0) {
                                    reg[a][1] = 1;
                                    reg[a][3] = 1;
                                    registeredCourses.add(trueList.get(i).getCRN());
                                    regName.add(trueList.get(i).getCource());

                                    Button b1 = new Button(trueList.get(i).getCource());
                                    b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                    g.add(b1,2,a+1);

                                    Button b2 = new Button(trueList.get(i).getCource());
                                    b2.setBorder(new Border(new BorderStroke(Color.BLACK,
                                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                    g.add(b2,4,a+1);

                                    final int finalA = a;
                                    final int finalIndex = i;
                                    b1.setOnAction(actionEvent -> {
                                        reg[finalA][1] = 0;
                                        reg[finalA][3] = 0;
                                        b1.setVisible(false);
                                        b2.setVisible(false);
                                        registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                        regName.remove(trueList.get(finalIndex).getCource());
                                    });
                                    b2.setOnAction(actionEvent -> {
                                        reg[finalA][1] = 0;
                                        reg[finalA][3] = 0;
                                        b1.setVisible(false);
                                        b2.setVisible(false);
                                        registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                        regName.remove(trueList.get(finalIndex).getCource());
                                    });
                                    break;
                                }
                                else {
                                    animation("conflict");
                                }
                            }
                        }
                        else if (trueList.get(i).getDays().equals("UT")) {
                            if (trueList.get(i).getTime().substring(0, 2).equals(labTime[a])) {
                                if (regName.contains(trueList.get(i).getCource())) {
                                    animation("duplicate");
                                    break;
                                }
                                if (trueList.get(i).getName().equals("Object-Oriented Programming")) {
                                    if (reg[a][0] == 0 && reg[a + 1][0] == 0 && reg[a + 2][0] == 0 && reg[a][2] == 0 && reg[a + 1][2] == 0 && reg[a + 2][2] == 0) {
                                        registeredCourses.add(trueList.get(i).getCRN());
                                        regName.add(trueList.get(i).getCource());
                                        reg[a][0] = 1;
                                        reg[a + 1][0] = 1;
                                        reg[a + 2][0] = 1;
                                        reg[a][2] = 1;
                                        reg[a + 1][2] = 1;
                                        reg[a + 2][2] = 1;

                                        Button b1 = new Button(trueList.get(i).getCource()+"\n\n\n\n\n\n");
                                        b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                        g.add(b1,1,a+1,3,3);

                                        Button b2 = new Button(trueList.get(i).getCource()+"\n\n\n\n\n\n");
                                        b2.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                        g.add(b2,3,a+1,3,3);

                                        final int finalA = a;
                                        final String name = trueList.get(i).getCource();
                                        final String crn = trueList.get(i).getCRN();
                                        final String section = trueList.get(i).getSection();
                                        final int finalIndex = i;
                                        b1.setOnAction(actionEvent -> {
                                            reg[finalA][0] = 0;
                                            reg[finalA+1][0] = 0;
                                            reg[finalA+2][0] = 0;
                                            reg[finalA][2] = 0;
                                            reg[finalA+1][2] = 0;
                                            reg[finalA+2][2] = 0;
                                            b1.setVisible(false);
                                            b2.setVisible(false);
                                            registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                            regName.remove(trueList.get(finalIndex).getCource());
                                        });

                                        b2.setOnAction(actionEvent -> {
                                            reg[finalA][0] = 0;
                                            reg[finalA+1][0] = 0;
                                            reg[finalA+2][0] = 0;
                                            reg[finalA][2] = 0;
                                            reg[finalA+1][2] = 0;
                                            reg[finalA+2][2] = 0;
                                            b1.setVisible(false);
                                            b2.setVisible(false);
                                            registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                            regName.remove(trueList.get(finalIndex).getCource());

                                        });
                                    } else {
                                        animation("conflict");
                                    }
                                    break;

                                }
                                else if (reg[a][0] == 0 && reg[a][2] == 0) {
                                    registeredCourses.add(trueList.get(i).getCRN());
                                    regName.add(trueList.get(i).getCource());
                                    reg[a][0] = 1;
                                    reg[a][2] = 1;
                                    Button b1 = new Button(trueList.get(i).getCource());
                                    b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                    g.add(b1,1,a+1);

                                    Button b2 = new Button(trueList.get(i).getCource());
                                    b2.setBorder(new Border(new BorderStroke(Color.BLACK,
                                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                    g.add(b2,3,a+1);

                                    final int finalA = a;
                                    final int finalIndex = i;
                                    b1.setOnAction(actionEvent -> {
                                        reg[finalA][0] = 0;
                                        reg[finalA][2] = 0;
                                        b1.setVisible(false);
                                        b2.setVisible(false);
                                        registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                        regName.remove(trueList.get(finalIndex).getCource());
                                    });
                                    b2.setOnAction(actionEvent -> {
                                        reg[finalA][0] = 0;
                                        reg[finalA][2] = 0;
                                        b1.setVisible(false);
                                        b2.setVisible(false);
                                        registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                        regName.remove(trueList.get(finalIndex).getCource());
                                    });
                                    break;
                                } else {
                                    animation("conflict");
                                }

                            }
                        }
                        else if (trueList.get(i).getDays().equals("U")) {
                            if (trueList.get(i).getActivity().equals("LAB")) {
                                if (trueList.get(i).getTime().substring(0, 2).equals(labTime[a])) {
                                    if (registeredLabs.contains(trueList.get(i).getCource())) {
                                        animation("duplicate lab");
                                    }
                                    else {
                                        if (reg[a][0] == 0 && reg[a + 1][0] == 0 && reg[a + 2][0] == 0) {
                                            registeredLabs.add(trueList.get(i).getCource());
                                            registeredCourses.add(trueList.get(i).getCRN());
                                            reg[a][0] = 1;
                                            reg[a + 1][0] = 1;
                                            reg[a + 2][0] = 1;

                                            Button b1 = new Button(trueList.get(i).getCource() + "\n\n\n\n\n\n");
                                            b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                            g.add(b1, 1, a + 1, 3, 3);

                                            final int finalA = a;
                                            final int finalIndex = i;
                                            b1.setOnAction(actionEvent -> {
                                                reg[finalA][0] = 0;
                                                reg[finalA + 1][0] = 0;
                                                reg[finalA + 2][0] = 0;
                                                b1.setVisible(false);
                                                registeredLabs.remove(trueList.get(finalIndex).getCource());
                                                registeredCourses.remove((trueList.get(finalIndex).getCRN()));
                                            });


                                            break;
                                        } else {
                                            animation("conflict");
                                        }
                                        break;
                                    }
                                }
                            }
                                if (regName.contains(trueList.get(i).getCRN()) && regName.contains(trueList.get(i).getSection())) {
                                    if (trueList.get(i).getTime().equals(time[a])) {
                                        if (reg[a][0] == 0) {
                                            registeredCourses.add(trueList.get(i).getCRN());
                                            regName.add(trueList.get(i).getCource());
                                            reg[a][0] = 1;
                                            Button b1 = new Button(trueList.get(i).getCource());
                                            b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                            g.add(b1,1,a+1);

                                            final int finalA = a;
                                            final int finalIndex = i;
                                            b1.setOnAction(actionEvent -> {
                                                reg[finalA][0] = 0;
                                                b1.setVisible(false);
                                                registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                                regName.remove(trueList.get(finalIndex).getCource());
                                            });
                                        }
                                        else {
                                            animation("conflict");
                                        }
                                        break;
                                    }
                                } else {
                                    if(regName.contains(trueList.get(i))&&!regName.contains(trueList.get(i).getCRN())) {
                                        animation("duplicate");
                                        break;
                                    }
                                }

                        }
                        else if (trueList.get(i).getDays().equals("M")) {
                            if(trueList.get(i).getActivity().equals("LAB")) {
                                if ((trueList.get(i)).getTime().substring(0,2).equals(labTime[a])) {
                                    if (registeredLabs.contains(trueList.get(i).getCource())) {
                                        animation("duplicate lab");
                                    }
                                    else {
                                        if (reg[a][1] == 0 && reg[a + 1][1] == 0 && reg[a + 2][1] == 0) {
                                            registeredLabs.add(trueList.get(i).getCource());
                                            registeredCourses.add(trueList.get(i).getCRN());
                                            reg[a][1] = 1;
                                            reg[a + 1][1] = 1;
                                            reg[a + 2][1] = 1;
                                            Button b1 = new Button(trueList.get(i).getCource()+"\n\n\n\n\n\n");
                                            b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                            g.add(b1,2,a+1,3,3);

                                            final int finalA = a;
                                            final int finalIndex = i;
                                            b1.setOnAction(actionEvent -> {
                                                reg[finalA][1] = 0;
                                                reg[finalA+1][1] = 0;
                                                reg[finalA+2][1] = 0;
                                                b1.setVisible(false);
                                                registeredLabs.remove(trueList.get(finalIndex).getCource());
                                                registeredCourses.remove((trueList.get(finalIndex).getCRN()));
                                            });
                                        }
                                        else {
                                            animation("conflict");
                                        }
                                        break;
                                    }
                                }
                            }

                            if(regName.contains(trueList.get(i).getCRN())&&regName.contains(trueList.get(i).getSection())){
                                if (trueList.get(i).getTime().equals(time[a])) {
                                    if (reg[a][1] == 0) {
                                        reg[a][1] = 1;
                                        Button b1 = new Button(trueList.get(i).getCource());
                                        b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                        g.add(b1,2,a+1);

                                        final int finalA = a;
                                        b1.setOnAction(actionEvent -> {
                                            reg[finalA][1] = 0;
                                            b1.setVisible(false);
                                        });
                                    }
                                    else{
                                        animation("conflict");
                                    }
                                    break;
                                }
                            }
                            else{
                                if(regName.contains(trueList.get(i))&&!regName.contains(trueList.get(i).getCRN())) {
                                    animation("duplicate");
                                    break;
                                }
                            }
                        }
                        else if (trueList.get(i).getDays().equals("T")) {
                            if (trueList.get(i).getActivity().equals("LAB")) {
                                if (trueList.get(i).getTime().substring(0,2).equals(labTime[a])) {
                                    if (registeredLabs.contains(trueList.get(i).getCource())) {
                                        animation("Duplicate Lab");
                                    }
                                    else {
                                        if (reg[a][2] == 0 && reg[a + 1][2] == 0 && reg[a + 2][2] == 0) {
                                            registeredLabs.add(trueList.get(i).getCource());
                                            registeredCourses.add(trueList.get(i).getCRN());
                                            reg[a][2] = 1;
                                            reg[a + 1][2] = 1;
                                            reg[a + 2][2] = 1;
                                            Button b1 = new Button(trueList.get(i).getCource()+"\n\n\n\n\n\n");
                                            b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                            g.add(b1,3,a+1,3,3);

                                            final int finalA = a;
                                            final int finalIndex = i;
                                            b1.setOnAction(actionEvent -> {
                                                reg[finalA][2] = 0;
                                                reg[finalA+1][2] = 0;
                                                reg[finalA+2][2] = 0;
                                                b1.setVisible(false);
                                                registeredLabs.remove(trueList.get(finalIndex).getCource());
                                                registeredCourses.remove((trueList.get(finalIndex).getCRN()));
                                            });

                                        }
                                        else {
                                            animation("conflict");
                                        }
                                        break;
                                    }
                                }
                            }

                            if(regName.contains(trueList.get(i).getCRN())&&regName.contains(trueList.get(i).getSection())){
                                if (trueList.get(i).getTime().equals(time[a])) {
                                    if (reg[a][2] == 0) {
                                        registeredCourses.add(trueList.get(i).getCRN());
                                        regName.add(trueList.get(i).getCource());
                                        reg[a][2] = 1;
                                        Button b1 = new Button(trueList.get(i).getCource());
                                        b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                        g.add(b1,3,a+1);

                                        final int finalA = a;
                                        final int finalIndex = i;
                                        b1.setOnAction(actionEvent -> {
                                            reg[finalA][2] = 0;
                                            b1.setVisible(false);
                                            registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                            regName.remove(trueList.get(finalIndex).getCource());
                                        });
                                    }
                                    else{
                                        animation("conflict");
                                    }
                                    break;
                                }
                            }
                            else{
                                if(regName.contains(trueList.get(i))&&!regName.contains(trueList.get(i).getCRN())) {
                                    animation("duplicate or other section");
                                    break;
                                }
                            }
                        }
                        else if (trueList.get(i).getDays().equals("W")) {
                            if (trueList.get(i).getActivity().equals("LAB")) {
                                if (trueList.get(i).getTime().substring(0,2).equals(labTime[a])) {
                                    if (registeredLabs.contains(trueList.get(i).getCource())) {
                                        animation("duplicate lab");
                                    }
                                    else {
                                        if (reg[a][3] == 0 && reg[a + 1][3] == 0 && reg[a + 2][3] == 0) {
                                            registeredLabs.add(trueList.get(i).getCource());
                                            registeredCourses.add(trueList.get(i).getCRN());
                                            reg[a][3] = 1;
                                            reg[a + 1][3] = 1;
                                            reg[a + 2][3] = 1;
                                            Button b1 = new Button(trueList.get(i).getCource()+"\n\n\n\n\n\n");
                                            b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                            g.add(b1,4,a+1,3,3);

                                            final int finalA = a;
                                            final int finalIndex = i;
                                            b1.setOnAction(actionEvent -> {
                                                reg[finalA][3] = 0;
                                                reg[finalA+1][3] = 0;
                                                reg[finalA+2][3] = 0;
                                                b1.setVisible(false);
                                                registeredLabs.remove(trueList.get(finalIndex).getCource());
                                                registeredCourses.remove((trueList.get(finalIndex).getCRN()));
                                            });
                                        }
                                        else {
                                            animation("conflict");
                                        }
                                        break;
                                    }
                                }
                            }
                            else if(regName.contains(trueList.get(i).getCRN())&& regName.contains(trueList.get(i).getSection())){
                                if (trueList.get(i).getTime().equals(time[a])) {
                                    if (reg[a][3] == 0) {
                                        reg[a][3] = 1;
                                        Button b1 = new Button(trueList.get(i).getCource());
                                        b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                        g.add(b1,4,a+1);

                                        final int finalA = a;
                                        final int finalIndex = i;
                                        b1.setOnAction(actionEvent -> {
                                            reg[finalA][3] = 0;
                                            b1.setVisible(false);
                                        });
                                    }
                                    else{
                                        animation("conflict");
                                    }
                                    break;
                                }
                            }
                            else{
                                if(regName.contains(trueList.get(i).getCource())&&!regName.contains(trueList.get(i).getCRN())) {
                                    animation("duplicate or other section");
                                    break;
                                }
                            }
                        }
                        else if (trueList.get(i).getDays().equals("R")) {
                            if (trueList.get(i).getActivity().equals("LAB")) {
                                if (trueList.get(i).getTime().substring(0,2).equals(labTime[a])) {
                                    if (registeredLabs.contains(trueList.get(i).getCource())) {
                                        animation("duplicate lab");
                                    }
                                    else {
                                        if (reg[a][4] == 0 && reg[a + 1][4] == 0 && reg[a + 2][4] == 0) {
                                            registeredLabs.add(trueList.get(i).getCource());
                                            registeredCourses.add(trueList.get(i).getCRN());
                                            reg[a][4] = 1;
                                            reg[a + 1][4] = 1;
                                            reg[a + 2][4] = 1;
                                            Button b1 = new Button(trueList.get(i).getCource()+"\n\n\n\n\n\n");
                                            b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                            g.add(b1,5,a+1,3,3);

                                            final int finalA = a;
                                            final int finalIndex = i;
                                            b1.setOnAction(actionEvent -> {
                                                reg[finalA][4] = 0;
                                                reg[finalA+1][4] = 0;
                                                reg[finalA+2][4] = 0;
                                                b1.setVisible(false);
                                                registeredLabs.remove(trueList.get(finalIndex).getCource());
                                                registeredCourses.remove((trueList.get(finalIndex).getCRN()));
                                            });
                                        }
                                        else {
                                            animation("conflict");
                                        }
                                        break;
                                    }
                                }
                            }
                            if(regName.contains(trueList.get(i).getCRN())&& regName.contains(trueList.get(i).getSection())){
                                if (trueList.get(i).getTime().equals(time[a])) {
                                    if (reg[a][4] == 0) {
                                        reg[a][4] = 1;
                                        Button b1 = new Button(trueList.get(i).getCource());
                                        b1.setBorder(new Border(new BorderStroke(Color.BLACK,
                                                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                                        g.add(b1,5,a+1);
                                        final int finalA = a;
                                        final int finalIndex = i;
                                        b1.setOnAction(actionEvent -> {
                                            reg[finalA][4] = 0;
                                            b1.setVisible(false);
                                            registeredCourses.remove(trueList.get(finalIndex).getCRN());
                                            regName.remove(trueList.get(finalIndex).getCource());
                                        });
                                    }
                                    else{
                                        animation("conflict");
                                    }
                                    break;
                                }
                            }
                            else{
                                if(regName.contains(trueList.get(i))) {
                                    animation("duplicate ");
                                    break;
                                }
                            }

                        }
                    }

                }
            }

            secondPagePage.setCenter(g);

        }
    }
    public boolean legal(String CourseName, List<String> CompletedCourses, File degreePlan) {
        

        for (int i = 0; i < CompletedCourses.size(); i++) {
            if (CompletedCourses.get(i).equals(CourseName)) {

                return false;
            }
        }
        try {
            Scanner scan = new Scanner(degreePlan);
            scan.nextLine();
            int startingIndex = 0;
            int endingIndex = 0;

            String line = scan.nextLine();
            while (!Character.toString(line.charAt(endingIndex)).equals(",")) {
                endingIndex++;

            }
            String name = line.substring(startingIndex, endingIndex);

            while (!name.equals(CourseName)) {
                line = scan.nextLine();
                startingIndex = 0;
                endingIndex = 0;
                while (!Character.toString(line.charAt(endingIndex)).equals(",")) {
                    endingIndex++;

                }
                name = line.substring(startingIndex, endingIndex);

            }

            startingIndex = endingIndex + 1;
            endingIndex++;
            while (!Character.toString(line.charAt(endingIndex)).equals(",")) {
                endingIndex++;

            }
            startingIndex = endingIndex + 1;
            endingIndex++;
            while (!Character.toString(line.charAt(endingIndex)).equals(",")) {
                endingIndex++;

            }
            String[] req = line.substring(startingIndex, endingIndex).split(";");

            int count = 0;
            for (int i = 0; i < req.length; i++) {
                for (int j = 0; j < CompletedCourses.size(); j++) {

                    if (req[i].equals(CompletedCourses.get(j))) {
                        count++;
                    } else if (req[i].equals("none")) {
                        return true;
                    }
                }
            }

            if (count == req.length) {
                scan.close();
                return true;
            }
            scan.close();
            return false;

        } catch (IOException e) {
            System.out.println("error");
            return false;
        }

    }


    public static void main(String[] args) {
        launch();

    }

}