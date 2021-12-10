import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.lang.IllegalStateException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.security.SecureRandom;
import java.text.NumberFormat;


public class ExamController {
	
	private final int NUM_OF_ANS = 4;
	
	private Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private RadioButton answerA;
    @FXML
    private ToggleGroup answerToggleGroup;
    @FXML
    private RadioButton answerB;
    @FXML
    private RadioButton answerC;
    @FXML
    private RadioButton answerD;
    @FXML
    private Button saveAnswerButton;
    @FXML
    private Label question;
    @FXML
    private TextField gradeTextField;
    @FXML
    private TextField answerStatusTextField;
    @FXML
    private Button nextQuestionButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label totalGrageLabel;
    @FXML
    private Label answerStatusLabel;
    
    
    private ArrayList <Question> questionsArray = new ArrayList<Question>();
    private int correctAnswersCount = 0;
    private int questionIndex = 0;
    private RadioButton [] radioButtonArray = new RadioButton[NUM_OF_ANS];
    private boolean markedAnswer = false;
    private static String grade = "0";
    
    // the first display initialization
    public void initialize() {
    	readFile();
    	shuffleAnswers();
    	setDataToDisplay();
    	   //~~~~~~~~~~~~~~~~~~GUI DESIGEN~~~~~~~~~~~~~~~~~~~~~~~
    	//set buttons style
    	nextQuestionButton.setStyle("-fx-background-color: rgba(128, 223, 255,1); -fx-background-radius: 0;");
    	saveAnswerButton.setStyle("-fx-background-color: rgba(128, 223, 255,1); -fx-background-radius: 0;");
    	answerStatusTextField.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
    	   //~~~~~~~~~~~~~~~~~~END GUI DESIGEN~~~~~~~~~~~~~~~~~~~~~~~
    }
    
    @FXML
    void nextQuestionButtonPressed(ActionEvent event) throws IOException {
    	//nextQuestion can be pressed as long ad there are more questions left
    	if(questionIndex < questionsArray.size()) {
    		shuffleAnswers();
        	setDataToDisplay();
        	answerStatusTextField.clear();
        	//return the save answer button
        	saveAnswerButton.setVisible(true);
    	}
    	// no more questions left
    	else {
    		//calculate the grade
    		grade = calculateGrade();
    		//display the grade
    		switchToGradeScene(event);	
    	}
    	
    }
    //move to the grade window function
    public void switchToGradeScene(ActionEvent event) throws IOException {
    	Parent root = 
				FXMLLoader.load(getClass().getResource("ExamGrade.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	scene.setFill(Color.TRANSPARENT);
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML	
    void saveAnswerButtonPressed(ActionEvent event) {
    	//show the answer status
    	displayAnswersStatus();
    	//count correct answers
    	if(markedAnswer == true) {
    		correctAnswersCount++;
    	}
    }
    
    
    // reads the file and insert the questions into Question objects array
    public void readFile() {
    	String questionBody;
    	String ansA;
    	String ansB;
    	String ansC;
    	String ansD;
    	
    	try(Scanner input = new Scanner(new File("exam.txt"))) {
    		while(input.hasNext()) {
    			questionBody = input.nextLine();
    			ansA = input.nextLine();
    			ansB = input.nextLine();
    			ansC = input.nextLine();
    			ansD = input.nextLine();
    			questionsArray.add(new Question(questionBody,ansA,ansB,ansC,ansD));
    		}
    		input.close();
    	}
    	catch(IOException|IllegalStateException|NoSuchElementException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    //shuffle the radioButtonArray elements
    public void shuffleAnswers() {
    	SecureRandom rand = new SecureRandom();
    	
    	radioButtonArray[0] = answerA;
    	radioButtonArray[1] = answerB;
    	radioButtonArray[2] = answerC;
    	radioButtonArray[3] = answerD;
    	
    	// start from the last element of the array
    	for(int i=radioButtonArray.length-1; i>=0; i--){
    		//pick random from 0 to i
    		int index = rand.nextInt(i+1);
    		
    		RadioButton temp = radioButtonArray[i];
    		radioButtonArray[i] = radioButtonArray[index];
    		radioButtonArray[index] = temp; 
    		
    	}
    }
    
    // display the answers
    public void setDataToDisplay() {
    	
    	question.setText(questionsArray.get(questionIndex).getQuestion());
    	radioButtonArray[0].setText(questionsArray.get(questionIndex).getanswerA());
    	radioButtonArray[0].setUserData(true);//the right answer
    	radioButtonArray[1].setText(questionsArray.get(questionIndex).getanswerB());
    	radioButtonArray[1].setUserData(false);
    	radioButtonArray[2].setText(questionsArray.get(questionIndex).getanswerC());
    	radioButtonArray[2].setUserData(false);
    	radioButtonArray[3].setText(questionsArray.get(questionIndex).getanswerD());
    	radioButtonArray[3].setUserData(false);
    	
    	questionIndex++;
    }
    
    //display the answer status after it saved
    public void displayAnswersStatus() {
    	if(answerToggleGroup.getSelectedToggle() != null) {
	    	markedAnswer = (boolean) answerToggleGroup.getSelectedToggle().getUserData();
	    	if(markedAnswer == true) {
	    		answerStatusTextField.setStyle("-fx-text-inner-color: green;"
	    				+ "-fx-background-color: rgba(255, 255, 255, 0);");
	    	}
	    	else {
	    		answerStatusTextField.setStyle("-fx-text-inner-color: red;"
	    				+ "-fx-background-color: rgba(255, 255, 255, 0);");
	    	}
	    	answerStatusTextField.setText(String.valueOf(markedAnswer));
	    	//hide the save answer button = answer can be saved only once
	    	saveAnswerButton.setVisible(false);
    	}
    	else {
    		answerStatusTextField.setText("You must pick an answer!");
    	}
    }
    
    //return the grade 
    public String calculateGrade() {
    	double grade = 0;
    	double amountOfQuestions = questionsArray.size();
    	grade = (correctAnswersCount*100)/amountOfQuestions;
    	String gradeStr =  String.format("%d", (int)grade);
    	
    	return gradeStr;
    }
    
    public static String getGrade() {
    	return grade;
    }
    
    @FXML
    void exitButtonPressed(ActionEvent event) {
    	// get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    
    //~~~~~~~~~~~~~~~~~~GUI DESIGEN~~~~~~~~~~~~~~~~~~~~~~~
    
    @FXML
    //highlight the button color when hover with mouse
    void mouseEnteredHandler(MouseEvent event) {
    	
    	if((Button)event.getTarget() == exitButton) {
    		exitButton.setStyle("-fx-background-color: rgba(204, 0, 0, 0.8);"
					+ "-fx-background-radius: 0 10 0 0");
    	}

    	else {
	    	((Button)event.getTarget()).setStyle("-fx-background-color: rgba(79,106,171, 0.8);"
	    			+ "-fx-background-radius: 0");
    	}
    }

    @FXML
  // de-highlight the button color when hover with mouse
    void mouseExitedHandler(MouseEvent event) {

    	if((Button)event.getTarget() == exitButton) {
    		exitButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0);"
					+ "-fx-background-radius: 0 10 0 0");
    	}
    	else {
	    	((Button)event.getTarget()).setStyle("-fx-background-color: rgba(128, 223, 255,1);"
	    			+ "-fx-background-radius: 0");
    	}
    }

    //~~~~~~~~~~~~~~~~~~ END GUI DESIGEN~~~~~~~~~~~~~~~~~~~~~~~
}
