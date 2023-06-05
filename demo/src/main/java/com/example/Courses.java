package com.example;

import java.io.Serializable;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class Courses implements Serializable {
    private String cource;
    private String section;
    private String activity;
    private String CRN;
    private String teacher;
    private String name;
    private String day;
    private String time;
    private String location;
    private Button addingButton;
    private Button removingButton;
    private Button inserting;
    private HBox innerBox = new HBox();
    

    public Courses(String cource, String section, String activity, String CRN, String name, String teacher,
                   String day, String time, String location) {
        this.cource = cource;
        this.section = section;
        this.activity = activity;
        this.CRN = CRN;
        this.teacher = teacher;
        this.name = name;
        this.day = day;
        this.time = time;
        this.location = location;

        addingButton = new Button("ADD");
       
        removingButton = new Button("Remove");
       
        addingButton.setScaleX(1.3);
        Text courseInfo;

        courseInfo = new Text(this.toString());
        courseInfo.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        addingButton.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        removingButton.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        innerBox.getChildren().add(courseInfo);
        innerBox.getChildren().add(addingButton);
        innerBox.getChildren().add(removingButton);

        removingButton.setVisible(false);

        inserting = new Button(this.getCource() + " - " + this.section  + "\n" + this.time);
        
        

    }

    public Button getInserting() {
        inserting.setPrefSize(110, 70);
        String font_name = Font.getFamilies().get(25);
        Font font = Font.font(font_name, FontWeight.BOLD, FontPosture.REGULAR, 12);
        inserting.setFont(font);
        return inserting;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return this.time;
    }
    public HBox getInnerBox(){
        return innerBox;
    }

    public String getDays() {
        return this.day;
    }

    public String getActivity(){return this.activity;}
    public String getCource(){return this.cource;}
    public String getCRN(){return this.CRN;}
    public String getSection(){return this.section;}
    public Button getAddButton(){
        return addingButton;

    }
    public Button getRemoveButton(){
        return removingButton;

    }    public Button getInsertingButton(){
        return inserting;

    }


    public String toString() {
        return this.cource + "-" + this.section + "," + this.activity + "," + this.CRN + "," + this.teacher + ","
                + this.name
                + "," + this.day + "," + this.time + "," + this.location;
    }
}