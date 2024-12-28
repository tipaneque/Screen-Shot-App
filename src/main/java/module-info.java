module com.screen.screenshot {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.screen.screenshot to javafx.fxml;
    exports com.screen.screenshot;
}