module com.example.comp2522202430termprojectmazzze {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;


    opens com.example.comp2522202430termprojectmazzze to javafx.fxml;
    exports com.example.comp2522202430termprojectmazzze;
}