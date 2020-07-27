package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.entity.Job;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewJobWindowController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField ownerTextField;
    @FXML
    private TextField statusTextField;
    @FXML
    private TextField proposedPriceTextField;
    @FXML
    private ImageView photoImageView;
    @FXML
    private Label errorLabel;
    @FXML
    private Button chooseImageButton;

    private File imageFile;

    private boolean imageFileChanged;

    private Job job;
    @FXML
    private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageFileChanged = false; //initially the image has not changed, use the default

        errorLabel.setText("");    //set the error message to be empty to start

        //load the defautl image for the avatar
        try {
            imageFile = new File("image/defaultImage.png");
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            photoImageView.setImage(image);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // back to main window
    @FXML
    public void backButtonPushed(ActionEvent actionEvent) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"));

        Scene mainViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }

    // save job data
    @FXML
    public void saveJobButtonPushed(ActionEvent actionEvent) {

        try {

            {
                if (imageFileChanged) //create a Volunteer with a custom image
                {
                    job = new Job(idTextField.getText(), nameTextField.getText(),
                            descriptionTextField.getText(), ownerTextField.getText(), statusTextField.getText(), imageFile,
                            Integer.parseInt(proposedPriceTextField.getText()));
                } else  //create a Volunteer with a default image
                {
                    job = new Job(idTextField.getText(), nameTextField.getText(),
                            descriptionTextField.getText(), ownerTextField.getText(), statusTextField.getText(), imageFile,
                            Integer.parseInt(proposedPriceTextField.getText()));
                }
                errorLabel.setText("");    //do not show errors if creating job was successful
                job.insertIntoJob();
            }


        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    // choose image from local
    @FXML
    public void chooseImageButtonPushed(ActionEvent event) {
        //get the Stage to open a new window (or Stage in JavaFX)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //Instantiate a FileChooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");


        //open the file dialog window
        File tmpImageFile = fileChooser.showOpenDialog(stage);

        if (tmpImageFile != null) {
            imageFile = tmpImageFile;

            //update the ImageView with the new image
            if (imageFile.isFile()) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(imageFile);
                    Image img = SwingFXUtils.toFXImage(bufferedImage, null);
                    photoImageView.setImage(img);
                    imageFileChanged = true;
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

    }


}

