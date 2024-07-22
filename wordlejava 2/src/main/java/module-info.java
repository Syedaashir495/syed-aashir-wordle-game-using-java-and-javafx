module com.example.wordlejava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wordlejava to javafx.fxml;
    exports com.example.wordlejava;
}