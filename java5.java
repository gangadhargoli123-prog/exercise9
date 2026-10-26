import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Step 3: Set window title
        primaryStage.setTitle("Text and Image Display");

        // Step 4: Create Text Label
        Label label = new Label("Hello, JavaFX!");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Step 5: Configure ImageView with safe image loading fallbacks
        ImageView imageView = new ImageView();
        try {
            // Replace "sample.jpg" with your desired local target image path
            Image image = new Image("file:sample.jpg", true);
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Image loading failed. Ensure 'sample.jpg' exists in the working directory.");
        }
        
        // Define clean sizing boundaries for image display matching constraints
        imageView.setFitWidth(400);
        imageView.setFitHeight(250);
        imageView.setPreserveRatio(true);

        // Step 6: Assemble nodes using a vertical alignment structural container layout
        VBox root = new VBox(20); // 20 pixels layout gap spacing
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(label, imageView);
        root.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 20px;");

        // Step 7: Create a Scene mapping strictly to 600x400 size window dimension rules
        Scene scene = new Scene(root, 600, 400);

        // Step 8: Mount the scene onto target stage window wrapper bounds
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Step 9: Launch standard baseline runtime thread configuration
    public static void main(String[] args) {
        launch(args);
    }
}
