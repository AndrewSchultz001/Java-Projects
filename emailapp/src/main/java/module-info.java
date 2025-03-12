module org.example.emailapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.emailapp to javafx.fxml;
    exports org.example.emailapp;
}