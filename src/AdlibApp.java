import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdlibApp extends Application {
    @Override
    public void start(Stage primaryStage) {



        String[] titles = DataBaseHelp.getStoryTitles();

        ComboBox<String> storyChoice = new ComboBox<>();
        storyChoice.setPromptText("Select");
        storyChoice.getItems().addAll(titles);

        // User inputs
        TextField nounField = new TextField();
        nounField.setPromptText("Enter a noun");

        TextField verbField = new TextField();
        verbField.setPromptText("Enter a verb");

        TextField adjectiveField = new TextField();
        adjectiveField.setPromptText("Enter an adjective");

        TextField adverbField = new TextField();
        adverbField.setPromptText("Enter an adverb");

        Button generateBtn = new Button("Generate Story");
        TextArea storyArea = new TextArea();
        storyArea.setWrapText(true);

        // Generate story button action
        generateBtn.setOnAction(e -> {
            int selectedIndex = storyChoice.getSelectionModel().getSelectedIndex();
            if (selectedIndex == -1) {
                storyArea.setText("Please select a story!");
                return;
            }

            int storyId = selectedIndex + 1; // DB ids start at 1
            String template = DataBaseHelp.getStoryTemplate(storyId);

            Story userStory = new Story(
                    nounField.getText(),
                    verbField.getText(),
                    adverbField.getText(),
                    adjectiveField.getText()
            );

            String finalStory = userStory.fillTemplate(template);
            storyArea.setText(finalStory);
        });

        VBox layout = new VBox(10, storyChoice, nounField, verbField, adjectiveField, adverbField, generateBtn, storyArea);
        layout.setPadding(new Insets(15));

        Scene scene = new Scene(layout, 500, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Adlib Story Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





