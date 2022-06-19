module com.fids.fids {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.fids.Login to javafx.fxml;
    opens com.fids.Farmacia to javafx.fxml;
    opens com.fids.Centrale to javafx.fxml;
    opens com.fids.Corriere to javafx.fxml;
    opens Entity to javafx.fxml;
    exports com.fids.Login;
    exports com.fids.Farmacia;
    exports com.fids.Centrale;
    exports com.fids.Corriere;
    exports com.fids;
    exports Entity;
    opens com.fids to javafx.fxml;

}