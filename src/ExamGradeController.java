import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ExamGradeController {

    @FXML
    private Button exitButton;

    @FXML
    private Label gradeLabel;
    
    private String grade;
    
    
    // the grade display initialization
    public void initialize() {
    	grade = ExamController.getGrade();
    	if(Integer.parseInt(grade) >= 60) {
    		gradeLabel.setTextFill(Color.GREEN);
    	}
    	else {
    		gradeLabel.setTextFill(Color.RED);
    	}
    		
    	gradeLabel.setText(grade+"%");
    }
    
    @FXML
    void exitButtonPressed(ActionEvent event) {
    	// get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    void mouseEnteredHandler(MouseEvent event) {
    	if((Button)event.getTarget() == exitButton) {
    		exitButton.setStyle("-fx-background-color: rgba(204, 0, 0, 0.8);"
					+ "-fx-background-radius: 0 10 0 0");
    	}
    }

    @FXML
    void mouseExitedHandler(MouseEvent event) {
    	if((Button)event.getTarget() == exitButton) {
    		exitButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);"
					+ "-fx-background-radius: 0 10 0 0");
    	}
    }

}

