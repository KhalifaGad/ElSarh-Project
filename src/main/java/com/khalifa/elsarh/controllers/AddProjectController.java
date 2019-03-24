package com.khalifa.elsarh.controllers;

import static com.khalifa.elsarh.MainApp.DirPath;
import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import static com.khalifa.elsarh.controllers.SideBarController.scenesWidth;
import com.khalifa.elsarh.modules.AddProjectModule;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import javafx.util.Duration;





/**
 * FXML Controller class
 *
 * @author khalifa
 */
public class AddProjectController implements Initializable {


    @FXML
    AnchorPane addmodel_anchPn, additionalsAnchPn,modelsImages_AnchPane,
            buildingImages_AnchPn, primaryInfoAnchPn, modelsAnchorPane,
            additionalsAnchorPane, modelsImages_pane;
    
    @FXML
    ScrollPane modelsImages_ScPane, modelsScrollPane, additionalsScrollPane;
    

    @FXML
    TextField projectName, floorsNumber, locationSt, locationArea, modelName,
            locationCountry, modelArea, buildingAdditionalPrice,
            buildingAdditionalName;
    
    @FXML
    HBox modelsImgheadHB, primaryInfoHB, projectNameHB, floorsNumberHB,
            locationHB, modelNameHB, modelAreaHB, modelPriceHB;
    
    @FXML
    Button savingBtn, imgBtn1, imgBtn2, imgBtn3, addModelInfoBtn,
            addAdditionalsBtn, addModelImgBtn;

    AddProjectModule module;
    
    double modelAnchPn = 0.0, commercialsAnchPn = 0.0, modelsImgs= 0.0;
    boolean saved = false ;
    Integer modelsCount=0, commercialsCount=0 ;
    int buildingId ;
    List<String> projectImagesPathsList = new ArrayList<>();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        module = new AddProjectModule();
        modelsImgs = (scenesWidth * 0.23) * 3;
        AnchorPane.setTopAnchor(primaryInfoAnchPn, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(modelsImgheadHB, scenesHeight * 0.01);
        AnchorPane.setTopAnchor(projectNameHB, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(savingBtn, scenesHeight * 1.8);
        AnchorPane.setTopAnchor(floorsNumberHB, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(locationHB, scenesHeight * 0.15);
        AnchorPane.setTopAnchor(modelNameHB, 2.0);
        AnchorPane.setTopAnchor(modelAreaHB, 2.0);
        AnchorPane.setTopAnchor(modelPriceHB, 2.0);
        AnchorPane.setTopAnchor(modelsAnchorPane, scenesHeight * 0.4);
        AnchorPane.setTopAnchor(modelsScrollPane, scenesHeight * 0.085);
        AnchorPane.setTopAnchor(additionalsScrollPane, scenesHeight * 0.085);
        AnchorPane.setTopAnchor(modelsImgheadHB, scenesHeight * 0.01);
        AnchorPane.setTopAnchor(modelsImages_ScPane, scenesHeight * 0.1);
        AnchorPane.setTopAnchor(additionalsAnchorPane, scenesHeight * 0.75);
        AnchorPane.setTopAnchor(modelsImages_pane, scenesHeight * 1.1);
        AnchorPane.setTopAnchor(addModelInfoBtn, scenesHeight * 0.2);
        AnchorPane.setTopAnchor(addAdditionalsBtn, scenesHeight * 0.2);
        AnchorPane.setTopAnchor(addModelImgBtn, scenesHeight * 0.585);
        AnchorPane.setTopAnchor(imgBtn1, scenesHeight * 0.015);
        AnchorPane.setTopAnchor(imgBtn2, scenesHeight * 0.015);
        AnchorPane.setTopAnchor(imgBtn3, scenesHeight * 0.015);
        AnchorPane.setRightAnchor(primaryInfoAnchPn, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(projectNameHB, scenesWidth * 0.045);
        AnchorPane.setRightAnchor(locationHB, scenesWidth * 0.045);
        AnchorPane.setRightAnchor(floorsNumberHB, scenesWidth * 0.44);
        AnchorPane.setRightAnchor(modelsAnchorPane, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(modelNameHB, scenesWidth * 0.04);
        AnchorPane.setRightAnchor(modelAreaHB, scenesWidth * 0.23);
        AnchorPane.setRightAnchor(modelPriceHB, scenesWidth * 0.42);
        AnchorPane.setRightAnchor(addModelInfoBtn, scenesWidth * 0.36);
        AnchorPane.setRightAnchor(addAdditionalsBtn, scenesWidth * 0.36);
        AnchorPane.setRightAnchor(addModelImgBtn, scenesWidth * 0.36);
        AnchorPane.setRightAnchor(additionalsAnchorPane, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(modelsImages_pane, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(imgBtn1, scenesWidth * 0.03);
        AnchorPane.setRightAnchor(imgBtn2, scenesWidth * 0.25);
        AnchorPane.setRightAnchor(imgBtn3, scenesWidth * 0.47);
        AnchorPane.setLeftAnchor(savingBtn, scenesWidth * 0.15);
        AnchorPane.setBottomAnchor(savingBtn, scenesWidth * 0.05);
        
    }
    
    //----------------  -> adding nodes functions -> ---firstHeadHBox-----------
    @FXML
    private void addModelImage(MouseEvent event) {
        Button btn = new Button();
        btn.setId("btnImagesId");
        btn.setPrefHeight(scenesHeight * 0.435);
        btn.setPrefWidth(scenesWidth * 0.2);
        btn.setOnMouseClicked((MouseEvent event1) -> {
            uploadModelImage(event1);
        });
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        ImageView imgview = new ImageView();
        Image img = new Image("images/photo(1).png");
        imgview.setImage(img);
        Label label = new Label();
        label.setStyle("-fx-font-size:2.2em;");
        label.setText("ارفع صوره");
        vbox.getChildren().addAll(imgview, label);
        btn.setGraphic(vbox);
        modelsImages_AnchPane.getChildren().add(btn);
        AnchorPane.setRightAnchor(btn, modelsImgs);
        modelsImgs += scenesWidth * 0.23;
        AnchorPane.setTopAnchor(btn, scenesHeight * 0.015);
    }
    
    @FXML
    private void addAddition(MouseEvent event) {
        commercialsCount += 1;
        
        HBox rhbox = new HBox();
        HBox mhbox = new HBox();
        HBox lhbox = new HBox();
        Text rtxt  = new Text();
        Text mtxt  = new Text();
        Text ltxt  = new Text();
        TextField rtxtfld = new TextField();
        TextField mtxtfld = new TextField();
        TextField ltxtfld = new TextField();
        
        rhbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        rhbox.setAlignment(Pos.CENTER_RIGHT);
        rhbox.setPrefWidth(scenesWidth* 0.167);
        rhbox.setStyle("-fx-background-color: transparent;");
        
        mhbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        mhbox.setAlignment(Pos.CENTER_RIGHT);
        mhbox.setPrefWidth(scenesWidth* 0.167);
        mhbox.setStyle("-fx-background-color: transparent;");
        

        lhbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        lhbox.setAlignment(Pos.CENTER_RIGHT);
        lhbox.setPrefWidth(scenesWidth* 0.167);
        lhbox.setStyle("-fx-background-color: transparent;");
        
        
        rtxt.setStyle("-fx-backround-color:transparent;");
        rtxt.setText("الاســـــــم: ");
        
        rtxtfld.setPrefWidth(1000.0);
        rtxtfld.setId("commercialUnitName"+commercialsCount.toString());
        
        mtxt.setStyle("-fx-backround-color:transparent;");
        mtxt.setText("الـمـساحــه : ");
        
        mtxtfld.setPrefWidth(1000.0);
        mtxtfld.setId("commercialUnitArea"+commercialsCount.toString());
        
        ltxt.setStyle("-fx-backround-color:transparent;");
        ltxt.setText("الــســعـــر : ");
        
        ltxtfld.setPrefWidth(1000.0);
        ltxtfld.setId("commercialUnitPrice"+commercialsCount.toString());
        if (commercialsCount > 1) {
            commercialsAnchPn += scenesHeight * 0.05;
        } else {
            commercialsAnchPn += scenesHeight * 0.01;
        }
        
        rhbox.getChildren().addAll(rtxt, rtxtfld);
        mhbox.getChildren().addAll(mtxt, mtxtfld);
        lhbox.getChildren().addAll(ltxt, ltxtfld);
        
        FadeTransition rft = new FadeTransition(Duration.seconds(0.30),
                rhbox);
            rft.setFromValue(0.0);
            rft.setToValue(1.0);
            rft.play();
        FadeTransition mft = new FadeTransition(Duration.seconds(0.30),
                mhbox);
            mft.setFromValue(0.0);
            mft.setToValue(1.0);
            mft.play();
            
         FadeTransition lft = new FadeTransition(Duration.seconds(0.30),
                lhbox);
            lft.setFromValue(0.0);
            lft.setToValue(1.0);
            lft.play();
        
        additionalsAnchPn.getChildren().addAll(rhbox, mhbox, lhbox);
        AnchorPane.setRightAnchor(rhbox, scenesWidth * 0.04);
        AnchorPane.setRightAnchor(mhbox, scenesWidth * 0.23);
        AnchorPane.setRightAnchor(lhbox, scenesWidth * 0.42);
        AnchorPane.setTopAnchor(rhbox, commercialsAnchPn);
        AnchorPane.setTopAnchor(mhbox, commercialsAnchPn);
        AnchorPane.setTopAnchor(lhbox, commercialsAnchPn);
    }    
    @FXML
    private void addModel(MouseEvent event) {
        
        modelsCount += 1;
        HBox rhbox = new HBox();
        HBox mhbox = new HBox();
        HBox lhbox = new HBox();
        Text rtxt  = new Text();
        Text mtxt  = new Text();
        Text ltxt  = new Text();
        TextField rtxtfld = new TextField();
        TextField mtxtfld = new TextField();
        TextField ltxtfld = new TextField();
        
        rhbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        rhbox.setAlignment(Pos.CENTER_RIGHT);
        rhbox.setPrefWidth(scenesWidth* 0.167);
        rhbox.setStyle("-fx-background-color: transparent;");
        
        mhbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        mhbox.setAlignment(Pos.CENTER_RIGHT);
        mhbox.setPrefWidth(scenesWidth* 0.167);
        mhbox.setStyle("-fx-background-color: transparent;");
        

        lhbox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        lhbox.setAlignment(Pos.CENTER_RIGHT);
        lhbox.setPrefWidth(scenesWidth* 0.167);
        lhbox.setStyle("-fx-background-color: transparent;");
        
        
        rtxt.setStyle("-fx-backround-color:transparent;");
        rtxt.setText("الاســـــــم: ");
        
        rtxtfld.setPrefWidth(1000.0);
        rtxtfld.setId("modelName"+modelsCount.toString());
        
        mtxt.setStyle("-fx-backround-color:transparent;");
        mtxt.setText("الـمـساحــه : ");
        
        mtxtfld.setPrefWidth(1000.0);
        mtxtfld.setId("modelArea"+modelsCount.toString());
        
        ltxt.setStyle("-fx-backround-color:transparent;");
        ltxt.setText("الــســعـــر : ");
        
        ltxtfld.setPrefWidth(1000.0);
        ltxtfld.setId("modelPrice"+modelsCount.toString());
        
        modelAnchPn += scenesHeight * 0.05;
        
        rhbox.getChildren().addAll(rtxt, rtxtfld);
        mhbox.getChildren().addAll(mtxt, mtxtfld);
        lhbox.getChildren().addAll(ltxt, ltxtfld);
        
        FadeTransition rft = new FadeTransition(Duration.seconds(0.30),
                rhbox);
            rft.setFromValue(0.0);
            rft.setToValue(1.0);
            rft.play();
        FadeTransition mft = new FadeTransition(Duration.seconds(0.30),
                mhbox);
            mft.setFromValue(0.0);
            mft.setToValue(1.0);
            mft.play();
            
         FadeTransition lft = new FadeTransition(Duration.seconds(0.30),
                lhbox);
            lft.setFromValue(0.0);
            lft.setToValue(1.0);
            lft.play();
        
        addmodel_anchPn.getChildren().addAll(rhbox, mhbox, lhbox);
        AnchorPane.setRightAnchor(rhbox, scenesWidth * 0.04);
        AnchorPane.setRightAnchor(mhbox, scenesWidth * 0.23);
        AnchorPane.setRightAnchor(lhbox, scenesWidth * 0.42);
        AnchorPane.setTopAnchor(rhbox, modelAnchPn);
        AnchorPane.setTopAnchor(mhbox, modelAnchPn);
        AnchorPane.setTopAnchor(lhbox, modelAnchPn);
    }    
    
    
    //------------------ -> warning function -> --------------------------------
    boolean makeItRed(TextField txtFld){
        txtFld.setStyle("-fx-border-color:red;-fx-border-style:solid;");
        return false ;
    }
    
    //--------------- -> getting text field node -> ----------------------------
    TextField getSpecificTextField(String baseStr, Integer Iterator,
            Scene scene){
         String textFiledIdStr = baseStr + Iterator.toString();
         TextField txtFld = (TextField) scene.lookup("#" + textFiledIdStr);
         return txtFld;
    }
    
    //--------------checking models field is empty or not-----------------------
    
    boolean checkModels(Scene scene){
     
        boolean notEmpty = true;
        TextField nameTxtFld, areaTxtFld, priceTxtFld;
        for (int i = 0; modelsCount >= i; i++) {

            nameTxtFld = getSpecificTextField("modelName", i, scene);

            if (nameTxtFld.getText().isEmpty()) {
                notEmpty = false;
                makeItRed(nameTxtFld);
            }
            if (!notEmpty) {
                break;
            }
            areaTxtFld = getSpecificTextField("modelArea", i, scene);

            notEmpty = areaTxtFld.getText().isEmpty()
                    ? makeItRed(areaTxtFld) : true;

            if (!notEmpty) {
                break;
            }
            priceTxtFld = getSpecificTextField("modelPrice", i, scene);

            notEmpty = priceTxtFld.getText().isEmpty()
                    ? makeItRed(priceTxtFld) : true;

            if (!notEmpty) {
                break;
            }
        }
        return notEmpty;
    }
        
    @FXML
    private void uploadModelImage(MouseEvent event) {
        FileChooser imageChooser = new FileChooser();
        imageChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Images", "*.jpg", "*.png"));
        Node source = (Node) event.getSource();
        Window stage = source.getScene().getWindow();
        File choosenFile = imageChooser.showOpenDialog(stage);

        String buildingImagesPath = DirPath + "/BuildingsDBImages/" +
                projectName.getText();
        File directory = new File(buildingImagesPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String DBImgsPath = buildingImagesPath + "/";
        if (choosenFile != null) {
            File targetFile = new File(DBImgsPath, choosenFile.getName());
            
            InputStream is;
            OutputStream os;
            try {
                is = new FileInputStream(choosenFile);
                os = new FileOutputStream(targetFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                is.close();
                os.close();
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(ProjectProfileController.class.getName()).
                        log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ProjectProfileController.class.getName()).
                        log(Level.SEVERE, null, ex);
            }

            Image img = new Image(targetFile.toURI().toString());
            ImageView imgView = new ImageView();
            imgView.setImage(img);
            Button btn = (Button) event.getSource();
            imgView.setFitWidth(scenesWidth * 0.17);
            imgView.setFitHeight(scenesHeight * 0.5);
            imgView.setPreserveRatio(true);
            AnchorPane.setTopAnchor(imgView, scenesHeight * 0.002);
            btn.setGraphic(imgView);
            projectImagesPathsList.add(targetFile.toURI().toString());
        }
    }
    
    // --------------------------- saving to database --------------------------
    @FXML
    private void saveProjData(MouseEvent event) {
                
        saved = true;
       
        Button btn = (Button) event.getSource();
        Scene addProjectscene      = btn.getScene();
        String projectNameStr      = projectName     .getText(),
                floorsNumberStr    = floorsNumber    .getText(),
                locationCountryStr =  locationCountry.getText(),
                locationStStr      =  locationSt     .getText(),
                locationAreaStr    =  locationArea   .getText();
        
        boolean check = projectNameStr.isEmpty() ? 
                        makeItRed(projectName)      :
                        floorsNumber   .getText().isEmpty() ?
                        makeItRed(floorsNumber)     : 
                        locationCountry.getText().isEmpty() ?
                        makeItRed(locationCountry)  :
                        checkModels(addProjectscene);
        
        if(! check) return ;
        if(module.checkBuildingName(projectNameStr)) {
            makeItRed(projectName);
            return;
        }
        
        int floorsCount = Integer.valueOf(floorsNumberStr);
        boolean mediaCheck = true;
        if (projectImagesPathsList.isEmpty()) {
            mediaCheck = false;
        }
        
        module.addBuilding(projectNameStr, locationStStr, locationAreaStr,
                locationCountryStr, floorsCount, mediaCheck );
        buildingId = module.getLastBuildingId();

        if (commercialsCount >= 1) {
            module.createCommercialsUnits(addProjectscene, buildingId,
                    commercialsCount);
        }
        module.createModles(addProjectscene, modelsCount, buildingId);
        module.createHousingUnits(addProjectscene, floorsCount, modelsCount,
                buildingId);
        if(!projectImagesPathsList.isEmpty()){
            module.saveBuildingImgs(projectImagesPathsList, buildingId);    
        }
        savingBtn.setStyle("-fx-background-color: green;");
        savingBtn.setDisable(true);
    }
}
