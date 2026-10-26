import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML file from location path bindings
        Parent root = FXMLLoader.load(getClass().getResource("tip_calculator.fxml"));
        
        primaryStage.setTitle("Tip Calculator App");
        primaryStage.setScene(new Scene(root, 350, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
