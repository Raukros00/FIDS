module com.fids.fids {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.fids.fids to javafx.fxml;
    exports com.fids.fids;
}