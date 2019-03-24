package com.khalifa.elsarh.controllers;

import static com.khalifa.elsarh.MainApp.DirPath;
import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import static com.khalifa.elsarh.controllers.SideBarController.scenesWidth;
import com.khalifa.elsarh.modules.ProjectProfileModule;
import com.khalifa.elsarh.pojos.ModelTemplate;
import com.khalifa.elsarh.pojos.UnitModel;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import static javafx.geometry.NodeOrientation.RIGHT_TO_LEFT;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author khalifa
 */
public class ProjectProfileController implements Initializable {

    @FXML
    private HBox projectNameHBox, floorsHBox, modelsHBox, additionalsHBox,
            projImgsHB, unitsHBox, saveEditingHB, editCommUnit1HB,
            editCommUnit2HB, addCommUnit1HB, addCommUnit2HB, saveNewCommUnitHB,
            repricingParamsHB, modelDeletionHB, addModelHB, saveModelHB,
            commDeletionHB;

    @FXML
    private AnchorPane modelsDetails_anchPn, additionalsDetails_anchPn,
            unitsDetails_anchPn, projectImagesAnchPane, profilContainer,
            editUnitsAncPn, addCommerialPane, editCommerialUnitPane,
            commerialsDealingAncPn, editUnitsHeadAncPn, modelDeletionAncPn,
            modelAddingAncPn;

    @FXML
    private ScrollPane modelsDetailsScrlPn, additionalsDetailsScrlPn,
            unitsDetailsScrlPn, projectImages_ScPn, editUnitsScrlPn;

    @FXML
    private VBox locationVBox;

    @FXML
    private ToggleButton editCommerialsTB, addCommerialsTB;

    @FXML
    private Button editUnitsPriceBtn, commerialsDealingBtn, imgAddBtn1,
            saveImgsBtn, deleteModelBtn, addModelBtn;

    @FXML
    private TextField editCommUnitCode, editCommUnitName, editCommUnitArea,
            editCommUnitPrice, addCommUnitName, addCommUnitArea,
            addCommUnitPrice, repricedUnitsCountTxtFld, modelDeletionTxtFld,
            addModelNameTxtFld, addModelPriceTxtFld, addModelAreaTxtFld,
            addModelFromTxtFld, addModelToTxtFld, modelDeletionFromTxtFld,
            modelDeletionToTxtFld, commDeletionTxtFld;

    @FXML
    private Text floorsNoTxt, streetTxt, areaTxt, countryTxt, commercialsNumTxt,
            unitsNumTxt;

    @FXML
    ImageView imgSavingIV;

    boolean modelsIsShown = false, commercialsIsShown = false,
            unitsShown = false, editUnitsPriceBtnClicked = false;
    double modelsDetailsAnchPnSpacing = 5.0, additionalsAnchPnSpacing = 5.0,
            unitsAnchPnSpacing = 5.0, imgsAncPnSpacing = 20.0;

    private int buildingId;
    private boolean mediaCheck;
    private List<ModelTemplate> models = new ArrayList();
    private String buildingName, street, area, country;

    private ProjectProfileModule module;
    private List<String> imgPaths;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        module = new ProjectProfileModule();
        imgPaths = new ArrayList();
        buildingName = ProjectsSearchingController.projectName;

        Image img = new Image("/images/rightIcon.png");
        imgSavingIV.setImage(img);

        getProjectInfo();
        modelsDetailsScrlPn.setVisible(false);
        additionalsDetailsScrlPn.setVisible(false);
        unitsDetailsScrlPn.setVisible(false);
        editUnitsHeadAncPn.setVisible(false);
        commerialsDealingAncPn.setVisible(false);
        modelAddingAncPn.setVisible(false);
        modelDeletionAncPn.setVisible(false);
        projectImages_ScPn.setVisible(true);

        addCommUnit1HB.setSpacing(scenesWidth * 0.05);
        addCommUnit2HB.setSpacing(scenesWidth * 0.05);
        editCommUnit1HB.setSpacing(scenesWidth * 0.05);
        editCommUnit2HB.setSpacing(scenesWidth * 0.05);
        repricingParamsHB.setSpacing(scenesWidth * 0.05);
        modelDeletionHB.setSpacing(scenesWidth * 0.03);
        commDeletionHB.setSpacing(scenesWidth * 0.045);
        addModelHB.setSpacing(scenesWidth * 0.02);

        AnchorPane.setTopAnchor(profilContainer, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(projectNameHBox, scenesHeight * 0.02);
        AnchorPane.setTopAnchor(addCommerialPane, scenesHeight * 0.05);
        AnchorPane.setTopAnchor(editCommerialUnitPane, scenesHeight * 0.05);
        AnchorPane.setTopAnchor(saveEditingHB, scenesHeight * 0.12);
        AnchorPane.setTopAnchor(saveNewCommUnitHB, scenesHeight * 0.12);
        AnchorPane.setTopAnchor(addCommUnit2HB, scenesHeight * 0.05);
        AnchorPane.setTopAnchor(editCommUnit2HB, scenesHeight * 0.05);
        AnchorPane.setTopAnchor(locationVBox, scenesHeight * 0.095);
        AnchorPane.setTopAnchor(floorsHBox, scenesHeight * 0.22);
        AnchorPane.setTopAnchor(modelsHBox, scenesHeight * 0.22);
        AnchorPane.setTopAnchor(modelsDetailsScrlPn, scenesHeight * 0.285);
        AnchorPane.setTopAnchor(additionalsHBox, scenesHeight * 0.385);
        AnchorPane.setTopAnchor(additionalsDetailsScrlPn, scenesHeight * 0.44);
        AnchorPane.setTopAnchor(unitsHBox, scenesHeight * 0.385);
        AnchorPane.setTopAnchor(unitsDetailsScrlPn, scenesHeight * 0.44);
        AnchorPane.setTopAnchor(editUnitsPriceBtn, scenesHeight * 0.68);
        AnchorPane.setTopAnchor(commerialsDealingBtn, scenesHeight * 0.74);
        AnchorPane.setTopAnchor(deleteModelBtn, scenesHeight * 0.8);
        AnchorPane.setTopAnchor(addModelBtn, scenesHeight * 0.86);
        AnchorPane.setTopAnchor(editUnitsHeadAncPn, scenesHeight * 0.68);
        AnchorPane.setTopAnchor(commerialsDealingAncPn, scenesHeight * 0.68);
        AnchorPane.setTopAnchor(modelAddingAncPn, scenesHeight * 0.68);
        AnchorPane.setTopAnchor(modelDeletionAncPn, scenesHeight * 0.68);
        AnchorPane.setTopAnchor(repricingParamsHB, scenesHeight * 0.015);
        AnchorPane.setTopAnchor(projImgsHB, scenesHeight * 1.0);
        AnchorPane.setTopAnchor(modelDeletionHB, scenesHeight * 0.07);
        AnchorPane.setTopAnchor(commDeletionHB, scenesHeight * 0.14);
        AnchorPane.setTopAnchor(addModelHB, scenesHeight * 0.1);
        AnchorPane.setTopAnchor(saveModelHB, scenesHeight * 0.15);
        AnchorPane.setTopAnchor(projectImages_ScPn, scenesHeight * 1.07);
        AnchorPane.setTopAnchor(imgAddBtn1, scenesHeight * 1.65);
        AnchorPane.setTopAnchor(saveImgsBtn, scenesHeight * 1.645);
        AnchorPane.setTopAnchor(editUnitsScrlPn, scenesHeight * 0.07);

        AnchorPane.setRightAnchor(profilContainer, scenesWidth * 0.07);
        AnchorPane.setRightAnchor(locationVBox, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(floorsHBox, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(modelsHBox, scenesWidth * 0.43);
        AnchorPane.setRightAnchor(modelsDetailsScrlPn, scenesWidth * 0.43);
        AnchorPane.setRightAnchor(additionalsHBox, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(additionalsDetailsScrlPn, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(unitsHBox, scenesWidth * 0.43);
        AnchorPane.setRightAnchor(projImgsHB, scenesWidth * 0.01);
        AnchorPane.setRightAnchor(unitsDetailsScrlPn, scenesWidth * 0.43);
        AnchorPane.setRightAnchor(editUnitsPriceBtn, 0.0);
        AnchorPane.setRightAnchor(commerialsDealingBtn, 0.0);
        AnchorPane.setRightAnchor(deleteModelBtn, 0.0);
        AnchorPane.setRightAnchor(addModelBtn, 0.0);
        AnchorPane.setRightAnchor(editUnitsHeadAncPn, scenesWidth * 0.2);
        AnchorPane.setRightAnchor(commerialsDealingAncPn, scenesWidth * 0.2);
        AnchorPane.setRightAnchor(modelAddingAncPn, scenesWidth * 0.2);
        AnchorPane.setRightAnchor(modelDeletionAncPn, scenesWidth * 0.2);
        AnchorPane.setRightAnchor(projectImages_ScPn, scenesWidth * 0.1);
        AnchorPane.setRightAnchor(imgAddBtn1, scenesWidth * 0.37);
        AnchorPane.setRightAnchor(saveImgsBtn, scenesWidth * 0.42);
        AnchorPane.setRightAnchor(addCommerialPane, scenesWidth * 0);
        AnchorPane.setRightAnchor(editCommerialUnitPane, scenesWidth * 0);

        editCommerialUnitPane.visibleProperty()
                .bind(editCommerialsTB.selectedProperty());
        addCommerialPane.visibleProperty()
                .bind(addCommerialsTB.selectedProperty());
    }

    private void getProjectInfo() {

        buildingId = module.getBuildingId(buildingName);
        projectNameUIProviding();

        street = module.getBuildingStreet(buildingId);
        area = module.getBuildingArea(buildingId);
        country = module.getBuildingCountry(buildingId);
        locationUIProviding();

        floorsUIProviding(module.getBuildingFloorsCount(buildingId));

        models = module.getBuildingModels(buildingId);
        modelsUIProviding();

        mediaCheck = module.whatAboutMedia(buildingId);
        if (mediaCheck) {
            showImgNode(buildingId);
        }

        commercialsUIProviding();
        unitsUIProviding();
    }

    @FXML
    private void showUnitsRepricingArea() {
        commerialsDealingAncPn.setVisible(false);
        modelDeletionAncPn.setVisible(false);
        modelAddingAncPn.setVisible(false);
        editUnitsPriceBtnClicked = !editUnitsPriceBtnClicked;
        FadeTransition fadeTransition
                = new FadeTransition(Duration.seconds(1.30), editUnitsHeadAncPn);
        if (editUnitsPriceBtnClicked) {
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
        } else {
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
        }
        fadeTransition.play();
        editUnitsHeadAncPn.setVisible(editUnitsPriceBtnClicked);
    }

    @FXML
    private void showModelDeletionPane() {
        commerialsDealingAncPn.setVisible(false);
        editUnitsHeadAncPn.setVisible(false);
        modelAddingAncPn.setVisible(false);
        editUnitsPriceBtnClicked = !editUnitsPriceBtnClicked;
        FadeTransition fadeTransition
                = new FadeTransition(Duration.seconds(1.30), modelDeletionAncPn);
        if (editUnitsPriceBtnClicked) {
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
        } else {
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
        }
        fadeTransition.play();
        modelDeletionAncPn.setVisible(editUnitsPriceBtnClicked);
    }

    @FXML
    private void showModelAddingPane() {
        commerialsDealingAncPn.setVisible(false);
        editUnitsHeadAncPn.setVisible(false);
        modelDeletionAncPn.setVisible(false);
        editUnitsPriceBtnClicked = !editUnitsPriceBtnClicked;
        FadeTransition fadeTransition
                = new FadeTransition(Duration.seconds(1.30), modelAddingAncPn);
        if (editUnitsPriceBtnClicked) {
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
        } else {
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
        }
        fadeTransition.play();
        modelAddingAncPn.setVisible(editUnitsPriceBtnClicked);
    }

    @FXML
    private void showCommerialsDealingPane() {
        editUnitsHeadAncPn.setVisible(false);
        modelDeletionAncPn.setVisible(false);
        modelAddingAncPn.setVisible(false);
        editUnitsPriceBtnClicked = !editUnitsPriceBtnClicked;
        FadeTransition fadeTransition
                = new FadeTransition(Duration.seconds(1.30), commerialsDealingAncPn);
        if (editUnitsPriceBtnClicked) {
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
        } else {
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
        }
        fadeTransition.play();
        commerialsDealingAncPn.setVisible(editUnitsPriceBtnClicked);
    }

    @FXML
    private void SummonSpecialUnit() {
        String specialUnitsCountStr = repricedUnitsCountTxtFld.getText();
        if (specialUnitsCountStr.isEmpty()) {
            repricedUnitsCountTxtFld.setStyle("-fx-border-color: red;");
            return;
        } else {
            repricedUnitsCountTxtFld.setStyle("-fx-border-color: transparent;");
        }
        editUnitsAncPn.getChildren().clear();
        int specialUnitsCount = Integer.valueOf(specialUnitsCountStr);

        double specialUnitsHrozSpacing = scenesHeight * 0.01;

        for (int i = 1; i <= specialUnitsCount; i++) {

            specialUnitsHrozSpacing += scenesHeight * 0.04;

            Text floorTxt = new Text();
            floorTxt.setText("الــدور : ");
            floorTxt.setId("darkBkTxt");

            Text modelTxt = new Text();
            modelTxt.setText("الـنـمـوذج");
            modelTxt.setId("darkBkTxt");

            Text newPrice = new Text();
            newPrice.setText("الـسـعـر : ");
            newPrice.setId("darkBkTxt");

            TextField priceTxtFld = new TextField();
            priceTxtFld.setPrefWidth(scenesWidth * 0.08);
            priceTxtFld.setId("priceTxtFld" + i);

            TextField floorTxtFld = new TextField();
            floorTxtFld.setPrefWidth(scenesWidth * 0.05);
            floorTxtFld.setId("floorTxtFld" + i);

            TextField modelTxtFld = new TextField();
            modelTxtFld.setPrefWidth(scenesWidth * 0.125);
            modelTxtFld.setId("modelTxtFld" + i);

            HBox specialUnitHB = new HBox();
            specialUnitHB.setStyle("-fx-background-color:transparent;");
            specialUnitHB.setSpacing(scenesWidth * 0.01);
            specialUnitHB.setAlignment(Pos.CENTER);
            specialUnitHB.getChildren().addAll(floorTxt, floorTxtFld, modelTxt,
                    modelTxtFld, newPrice, priceTxtFld);

            FadeTransition fadeTransition
                    = new FadeTransition(Duration.seconds(i * 0.5), specialUnitHB);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            AnchorPane.setTopAnchor(specialUnitHB, specialUnitsHrozSpacing);
            AnchorPane.setLeftAnchor(specialUnitHB, scenesWidth * 0.05);
            editUnitsAncPn.getChildren().add(specialUnitHB);
        }
        specialUnitsHrozSpacing += scenesHeight * 0.04;

        Button savingBtn = new Button("حفظ");
        savingBtn.setStyle("-fx-background-color:#ffcc33;");
        savingBtn.setOnMouseClicked((event) -> {

            saveSpecialUnitPrice(event, specialUnitsCount);

        });

        HBox specialUnitHB = new HBox();//editUnitsAncPn
        specialUnitHB.setPrefWidth(editUnitsAncPn.getPrefWidth());
        specialUnitHB.setStyle("-fx-background-color:transparent;");
        specialUnitHB.setAlignment(Pos.CENTER);
        specialUnitHB.getChildren().add(savingBtn);

        AnchorPane.setTopAnchor(specialUnitHB, specialUnitsHrozSpacing);
        editUnitsAncPn.getChildren().add(specialUnitHB);
    }

    TextField getSpecificTextField(String baseStr, Integer Iterator,
            Scene scene) {
        String textFiledIdStr = baseStr + Iterator.toString();
        TextField txtFld = (TextField) scene.lookup("#" + textFiledIdStr);
        return txtFld;
    }

    boolean makeItRed(TextField txtFld) {
        txtFld.setStyle("-fx-border-color:red;-fx-border-style:solid;");
        return false;
    }

    private void saveSpecialUnitPrice(MouseEvent event, int specialUnitsCount) {

        Button btn = (Button) event.getSource();
        Scene scene = btn.getScene();

        for (int i = 1; specialUnitsCount >= i; i++) {

            TextField floorNumberTF
                    = getSpecificTextField("floorTxtFld", i, scene),
                    modelTF = getSpecificTextField("modelTxtFld", i, scene);

            int floorNumber = Integer.
                    valueOf(floorNumberTF.getText()),
                    unitID = 0;
            boolean existance = false;
            String model = modelTF.getText();
            List<UnitModel> unitsList = module.getHousingUnits(buildingId);
            for (UnitModel unit : unitsList) {

                if (floorNumber == unit.getFloorNumber()
                        && model.equals(unit.getModelName())) {
                    unitID = unit.getId();
                    existance = true;
                }
            }
            if (existance) {
                TextField newPriceTF
                        = getSpecificTextField("priceTxtFld", i, scene);
                float newPrice = Float.
                        valueOf(newPriceTF.getText());
                module.itsSpecial(unitID, newPrice);
            } else {
                makeItRed(floorNumberTF);
                makeItRed(modelTF);
                return;
            }
        }
        btn.setStyle("-fx-background-color: green;");
    }

    private void showImgNode(int buildingId) {
        projectImages_ScPn.setVisible(true);
        projectImagesAnchPane.getChildren().clear();
        List<String> imgs = module.getImgsPaths(buildingId);
        imgsAncPnSpacing = 0;
        for (String imgPath : imgs) {

            AnchorPane ancPn = new AnchorPane();
            ancPn.setId("bkgroundLayer");
            ancPn.setPrefHeight(scenesHeight * 0.5);
            ancPn.setPrefWidth(scenesWidth * 0.3);
            AnchorPane.setTopAnchor(ancPn, scenesHeight * 0.01);
            AnchorPane.setRightAnchor(ancPn, imgsAncPnSpacing);
            Image img = new Image(imgPath);
            ImageView imgView = new ImageView();
            imgView.setImage(img);
            imgView.setFitWidth(scenesWidth * 0.2);
            imgView.setFitHeight(scenesHeight * 0.5);
            imgView.setPreserveRatio(true);
            imgView.setSmooth(true);
            imgView.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {

                    if (event.getClickCount() == 2) {
                        try {
                            if (Desktop.isDesktopSupported()) {
                                URI url = new URI(imgPath);
                                File file = new File(url);
                                new Thread(() -> {
                                    try {
                                        Desktop.getDesktop().open(file);
                                    } catch (IOException ex) {
                                        Logger.getLogger(
                                                ProjectProfileController.class.getName()).
                                                log(Level.SEVERE, null, ex);
                                    }
                                }).start();
                            }
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(ProjectProfileController.class.
                                    getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

            Button deletionBtn = new Button();
            deletionBtn.setPrefHeight(scenesHeight * 0.01);
            deletionBtn.setPrefWidth(scenesWidth * 0.01);
            deletionBtn.setId("smallRedBtn");
            deletionBtn.setText("-");

            deletionBtn.setOnMouseClicked((event) -> {
                String confrimationBody = "تأكيد مسح الصوره من ملف المشروع";
                if (confirmProcess(null, confrimationBody)) {

                    module.deleteImage(buildingId, imgPath);
                    FileUtils.deleteQuietly(new File(imgPath));

                    if (imgs.size() == 1) {
                        module.updateMediaCheck(buildingId);
                    }
                    saveImages();
                }
            });

            AnchorPane.setBottomAnchor(deletionBtn, scenesHeight * 0.005);
            AnchorPane.setLeftAnchor(deletionBtn, scenesWidth * 0.005);

            ancPn.getChildren().addAll(imgView, deletionBtn);

            AnchorPane.setTopAnchor(imgView, scenesHeight * 0.05);
            AnchorPane.setLeftAnchor(imgView, scenesWidth * 0.03);
            projectImagesAnchPane.getChildren().add(ancPn);
            imgsAncPnSpacing += scenesWidth * 0.31;
        }
    }

    private void openFile(File img) {
        try {
            Desktop.getDesktop().open(img);
        } catch (IOException ex) {
            Logger.getLogger(ProjectProfileController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addProjImage(MouseEvent event) {
        Button btn = new Button();
        btn.setId("btnImagesId");
        btn.setPrefHeight(scenesHeight * 0.535);
        btn.setPrefWidth(scenesWidth * 0.22);
        btn.setOnMouseClicked((MouseEvent event1) -> {
            uploadProjectImage(event1);
        });
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        ImageView imgview = new ImageView();
        Image img = new Image("images/photo(1).png");

        imgview.setImage(img);
        Label label = new Label();
        label.setStyle("-fx-font-size:30px;");
        label.setText("ارفع صوره");
        vbox.getChildren().addAll(imgview, label);
        btn.setGraphic(vbox);
        AnchorPane.setRightAnchor(btn, imgsAncPnSpacing);
        AnchorPane.setTopAnchor(btn, scenesHeight * 0.015);
        imgsAncPnSpacing += scenesWidth * 0.31;
        projectImagesAnchPane.getChildren().add(btn);
    }

    private void uploadProjectImage(MouseEvent event) {
        FileChooser imageChooser = new FileChooser();
        imageChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));
        Node source = (Node) event.getSource();
        Window stage = source.getScene().getWindow();
        File choosenFile = imageChooser.showOpenDialog(stage);

        if (choosenFile != null) {

            String buildingImagesPath = DirPath + "/BuildingsDBImages/"
                    + buildingName;
            File directory = new File(buildingImagesPath);
            if (!directory.exists()) {
                directory.mkdir();
            }
            String DBImgsPath = buildingImagesPath + "/";
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
            } catch (FileNotFoundException ex) {
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
            imgView.setFitWidth(scenesWidth * 0.22);
            imgView.setFitHeight(scenesHeight * 0.5);
            imgView.setPreserveRatio(true);
            btn.setPrefHeight(scenesHeight * 0.54);
            btn.setPrefWidth(scenesWidth * 0.22);
            btn.setGraphic(imgView);
            if (!imgPaths.contains(targetFile.toURI().toString())) {
                imgPaths.add(targetFile.toURI().toString());
            }
            saveImgsBtn.setDisable(false);
        }
    }

    @FXML
    private void saveImages() {
        module.seveImages(imgPaths, buildingId);
        if (!mediaCheck) {
            module.buildingHasMedia(buildingId);
        }
        imgPaths.clear();
        saveImgsBtn.setDisable(true);
        showImgNode(buildingId);
    }

    @FXML
    private void deleteModel() { //modelDeletionToTxtFld modelDeletionFromTxtFld
        String modelDeletionName = modelDeletionTxtFld.getText(),
                fromStr = modelDeletionFromTxtFld.getText(),
                toStr = modelDeletionToTxtFld.getText();

        if (modelDeletionName.isEmpty()) {
            modelDeletionTxtFld.setStyle("-fx-border-color: red;");
            return;
        }
        if (fromStr.isEmpty()) {
            modelDeletionFromTxtFld.setStyle("-fx-border-color: red;");
            return;
        }
        if (toStr.isEmpty()) {
            modelDeletionToTxtFld.setStyle("-fx-border-color: red;");
            return;
        }
        boolean checkModelExistance = false;
        for (ModelTemplate model : models) {
            if (modelDeletionName.equals(model.getName())) {
                checkModelExistance = true;
            }
        }
        if (!checkModelExistance) {
            modelDeletionTxtFld.setStyle("-fx-border-color: red;");
            return;
        }
        if (confirmProcess(null, "تأكيد مسح النموذج")) {
            // true if units are free
            int from = Integer.parseInt(fromStr),
                    to = Integer.parseInt(toStr);

            boolean checkUnits = module.checkModelUnits(buildingId,
                    modelDeletionName, from, to);
            if (!checkUnits) {
                alertError("خطأ فى عملية المسح",
                        "يوجد وحدات غير شاغره بالنموذج");
                return;
            }
            module.deleteModel(buildingId, modelDeletionName, from, to);
            modelDeletionTxtFld.setStyle("-fx-border-color: green;");
            modelDeletionTxtFld.setText("");
            models = module.getBuildingModels(buildingId);
            modelsUIProviding();
            unitsUIProviding();
            if (!modelsIsShown) {
                modelsDetailsScrlPn.setVisible(false);
            }
        }
    }

    @FXML
    private void deleteCommerialUnit() {

        String commUnitCodeStr = commDeletionTxtFld.getText();
        if (commUnitCodeStr.isEmpty()) {
            commDeletionTxtFld.setStyle("-fx-border-color: red;");
        } else {
            commDeletionTxtFld.setStyle("-fx-border-color: transparent;");
        }

        int commUnitCode = Integer.parseInt(commUnitCodeStr);
        if (module.isCommUnitAvailable(commUnitCode, buildingId)) {
            if (confirmProcess("تأكيد ! ", "تأكيد مسح وحده تجاريه")) {
                module.deleteCommUnit(buildingId, commUnitCode);
                commercialsUIProviding();
            }
        } else {
            alertError("خطأ!!",
                    "خطأ بعملية المسح من فضلك تأكد من بيانات الوحده");
        }

    }

    @FXML
    private void addModel() {

        String addingModelName = addModelNameTxtFld.getText(),
                addingModelPrice = addModelPriceTxtFld.getText(),
                addingModelArea = addModelAreaTxtFld.getText(),
                addingModelFrom = addModelFromTxtFld.getText(),
                addingModelTo = addModelToTxtFld.getText();

        if (addingModelName.isEmpty()) {
            addModelNameTxtFld.setStyle("-fx-border-color: red;");
            return;
        }
        if (addingModelPrice.isEmpty()) {
            addModelPriceTxtFld.setStyle("-fx-border-color: red;");
            return;
        }
        if (addingModelArea.isEmpty()) {
            addModelAreaTxtFld.setStyle("-fx-border-color: red;");
            return;
        }
        if (addingModelFrom.isEmpty()) {
            addModelFromTxtFld.setStyle("-fx-border-color: red;");
            return;
        }
        if (addingModelTo.isEmpty()) {
            addModelToTxtFld.setStyle("-fx-border-color: red;");
            return;
        }
        float modelPrice = Float.parseFloat(addingModelPrice);
        int modelArea = Integer.parseInt(addingModelArea),
                from = Integer.parseInt(addingModelFrom),
                to = Integer.parseInt(addingModelTo);

        if (confirmProcess(null, "تأكيد اضافة نموذج")) {
            int modelsCount = module.getBuildingModelsCount(buildingId);

            module.addModel(buildingId, addingModelName, modelArea, modelPrice,
                    modelsCount, from, to);
            models = module.getBuildingModels(buildingId);
            modelsUIProviding();
            unitsUIProviding();
            if (!modelsIsShown) {
                modelsDetailsScrlPn.setVisible(false);
            }
            addModelNameTxtFld.setText("");
            addModelPriceTxtFld.setText("");
            addModelAreaTxtFld.setText("");
        }

    }

    private void alertError(String headerStr, String contentStr) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.getDialogPane().setNodeOrientation(
                NodeOrientation.RIGHT_TO_LEFT);
        errorAlert.setTitle("خطأ ! ");
        errorAlert.setHeaderText(headerStr);
        errorAlert.setContentText(contentStr);
        errorAlert.showAndWait();
    }

    private void projectNameUIProviding() {
        Text headTxt = new Text();
        headTxt.setId("headTxt");
        headTxt.setText("  " + buildingName);
        projectNameHBox.getChildren().add(headTxt);
    }

    private void locationUIProviding() {
        streetTxt.setText("الــــشــــــارع : " + street);
        areaTxt.setText("المـــــنــطــقه : " + area);
        countryTxt.setText("المـــحــافــظه : " + country);

    }

    private void floorsUIProviding(int floorsNo) {
        String floorsNoStr = String.valueOf(floorsNo);
        floorsNoTxt.setText("عدد الادوار : " + floorsNoStr);
    }

    private void unitsUIProviding() {
        unitsNumTxt.setText("");
        int unitsNum = module.getHousingUnitsCount(buildingId);
        unitsNumTxt.setText(" الوحدات السكنيه: " + unitsNum);
    }

    @FXML
    private void showUnits(MouseEvent event) {
        Button btn = (Button) event.getSource();
        if (unitsShown) {
            unitsShown = false;
            unitsDetailsScrlPn.setVisible(false);
            btn.setText("↓");
        } else {
            unitsShown = true;
            unitsDetails_anchPn.getChildren().clear();
            FadeTransition ft = new FadeTransition(Duration.seconds(1.00),
                    unitsDetailsScrlPn);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            unitsDetailsScrlPn.setVisible(true);
            btn.setText("↑");

            VBox vbox = new VBox();
            vbox.setNodeOrientation(RIGHT_TO_LEFT);
            vbox.setStyle("-fx-background-color: white");
            AnchorPane.setRightAnchor(vbox, scenesWidth * 0.01);

            Text unitCodeHeaderTxt = new Text();
            unitCodeHeaderTxt.setText("ك.الوحده");

            Text unitModelHeaderTxt = new Text();
            unitModelHeaderTxt.setText("نموذج");

            Text unitStatHeaderTxt = new Text();
            unitStatHeaderTxt.setText("الحاله");

            Text clienCodetHeaderTxt = new Text();
            clienCodetHeaderTxt.setText("ك.العميل");

            Text unitSpecialPriceHeaderTxt = new Text();
            unitSpecialPriceHeaderTxt.setText("سعر مميز");

            HBox headHB = new HBox();
            headHB.setStyle("-fx-background-color:#0564ff;");
            headHB.setNodeOrientation(RIGHT_TO_LEFT);
            headHB.setSpacing(scenesWidth * 0.05);
            headHB.setPrefHeight(scenesHeight * 0.04);
            headHB.setAlignment(Pos.CENTER_RIGHT);

            headHB.getChildren().addAll(unitCodeHeaderTxt, unitModelHeaderTxt,
                    unitStatHeaderTxt, clienCodetHeaderTxt,
                    unitSpecialPriceHeaderTxt);

            vbox.getChildren().add(headHB);

            String buleStyle = "-fx-background-color:#cce0ff;",
                    transparentStyle = "-fx-background-color:transparent;";
            int i = 0;
            List<UnitModel> unitsList = module.getHousingUnits(buildingId);
            for (UnitModel unit : unitsList) {
                i++;
                HBox unitDetailsHB = new HBox();
                if (i % 2 > 0) {
                    unitDetailsHB.setStyle(buleStyle);
                } else {
                    unitDetailsHB.setStyle(transparentStyle);
                }
                unitDetailsHB.setSpacing(scenesWidth * 0.05);
                unitDetailsHB.setNodeOrientation(RIGHT_TO_LEFT);
                unitDetailsHB.setPrefHeight(scenesHeight * 0.04);
                unitDetailsHB.setAlignment(Pos.CENTER_LEFT);

                Text unitCodeTxt = new Text();
                unitCodeTxt.setText("   " + unit.getUnitCode());

                Text unitModelTxt = new Text();
                unitModelTxt.setText(unit.getModelName());

                Text unitStatText = new Text();
                Text clientCodeTxt = new Text();

                if (unit.isAvailable()) {
                    unitStatText.setText("شاغره");
                    clientCodeTxt.setText("        ");
                } else {
                    unitStatText.setText("محجوزه");
                    clientCodeTxt.setText("" + unit.getClientId());
                }

                Text specialPriceTxt = new Text();
                specialPriceTxt.setText(""
                        + String.format("%.1f", unit.getSpecialPrice()));

                unitDetailsHB.getChildren().addAll(unitCodeTxt, unitModelTxt,
                        unitStatText, clientCodeTxt, specialPriceTxt);

                vbox.getChildren().add(unitDetailsHB);
            }
            FadeTransition fadeTransition
                    = new FadeTransition(Duration.seconds(1.30), vbox);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            unitsDetails_anchPn.getChildren().add(vbox);
        }
    }

    private void commercialsUIProviding() {
        commercialsNumTxt.setText("");
        int comercialsNum = module.getCommUnitsCount(buildingId);
        commercialsNumTxt.setText(" الوحدات التجاريه: " + comercialsNum);

    }

    @FXML
    private void showCommercials(MouseEvent event) {
        Button btn = (Button) event.getSource();
        if (commercialsIsShown) {
            additionalsDetailsScrlPn.setVisible(false);
            commercialsIsShown = false;
            btn.setText("↓");
        } else {
            additionalsDetails_anchPn.getChildren().clear();
            FadeTransition ft = new FadeTransition(Duration.seconds(1.00),
                    additionalsDetailsScrlPn);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            additionalsDetailsScrlPn.setVisible(true);
            btn.setText("↑");
            commercialsIsShown = true;
            VBox vbox = new VBox();
            vbox.setNodeOrientation(RIGHT_TO_LEFT);
            vbox.setStyle("-fx-background-color: white");
            AnchorPane.setRightAnchor(vbox, 0.0);

            String buleStyle = "-fx-background-color:#cce0ff;",
                    transparentStyle = "-fx-background-color:transparent;";
            int i = 0;
            List<UnitModel> commUnits = module.getCommUnits(buildingId);
            for (UnitModel commUnit : commUnits) {
                i++;
                HBox unitDetailsHB = new HBox();
                if (i % 2 > 0) {
                    unitDetailsHB.setStyle(buleStyle);
                } else {
                    unitDetailsHB.setStyle(transparentStyle);
                }
                unitDetailsHB.setSpacing(scenesWidth * 0.03);
                unitDetailsHB.setNodeOrientation(RIGHT_TO_LEFT);
                unitDetailsHB.setPrefHeight(scenesHeight * 0.04);
                unitDetailsHB.setAlignment(Pos.CENTER_LEFT);

                Text commUnitNameTxt = new Text();
                commUnitNameTxt.setText(commUnit.getModelName());

                Text commUnitCodeTxt = new Text();
                commUnitCodeTxt.setText("ك: " + commUnit.getUnitCode());

                Text commUnitAreaTxt = new Text();
                commUnitAreaTxt.setText("م: " + commUnit.getFloorNumber());

                Text commUnitPrice = new Text();
                commUnitPrice.setText("س: "
                        + String.format("%.1f", commUnit.getSpecialPrice()));

                Text commClientTxt = new Text();
                Text commUnitStatTxt = new Text();
                if (commUnit.isAvailable()) {
                    commUnitStatTxt.setText("شاغره");
                    commClientTxt.setText("ك.ع: 00");
                } else {
                    commUnitStatTxt.setText("غير متاح ");
                    commClientTxt.setText("ك.ع: " + commUnit.getClientId());
                }
                unitDetailsHB.getChildren().addAll(commUnitNameTxt,
                        commUnitCodeTxt, commUnitAreaTxt, commUnitPrice,
                        commUnitStatTxt, commClientTxt);

                vbox.getChildren().add(unitDetailsHB);
            }

            FadeTransition fadeTransition
                    = new FadeTransition(Duration.seconds(1.30), vbox);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            additionalsDetails_anchPn.getChildren().add(vbox);
        }
    }

    @FXML
    private void editCommUnitData() {

        String commUnitCodeStr = editCommUnitCode.getText(),
                commUnitName = editCommUnitName.getText(),
                commUnitAreaStr = editCommUnitArea.getText(),
                commUnitPriceStr = editCommUnitPrice.getText();

        if (commUnitCodeStr.isEmpty()) {
            editCommUnitCode.setStyle("-fx-border-color: red");
            return;
        } else {
            editCommUnitCode.setStyle("-fx-border-color: green");
        }
        if (commUnitName.isEmpty()) {
            editCommUnitName.setStyle("-fx-border-color: red");
            return;
        } else {
            editCommUnitName.setStyle("-fx-border-color: green");
        }
        if (commUnitAreaStr.isEmpty()) {
            editCommUnitArea.setStyle("-fx-border-color: red");
            return;
        } else {
            editCommUnitArea.setStyle("-fx-border-color: green");
        }
        if (commUnitPriceStr.isEmpty()) {
            editCommUnitPrice.setStyle("-fx-border-color: red");
            return;
        } else {
            editCommUnitPrice.setStyle("-fx-border-color: green");
        }

        int commUnitCode = Integer.parseInt(commUnitCodeStr),
                commUnitArea = Integer.parseInt(commUnitAreaStr);

        float commUnitPrice = Float.parseFloat(commUnitPriceStr);

        String confirmationBody = "بيانات الوحده: " + commUnitCodeStr
                + System.lineSeparator() + "الاسم: " + commUnitName
                + System.lineSeparator() + "المساحه" + commUnitAreaStr
                + System.lineSeparator() + "السعر: " + commUnitPriceStr,
                confirmationHeader = "تأكيد تعديل الوحده";
        if (confirmProcess(confirmationHeader, confirmationBody)) {
            module.editCommUnit(buildingId, commUnitCode, commUnitName,
                    commUnitArea, commUnitPrice);
            editCommUnitCode.setText("");
            editCommUnitName.setText("");
            editCommUnitArea.setText("");
            editCommUnitPrice.setText("");
        }

    }

    @FXML
    private void addCommUnit() {

        String commUnitName = addCommUnitName.getText(),
                commUnitAreaStr = addCommUnitArea.getText(),
                commUnitPriceStr = addCommUnitPrice.getText();

        if (commUnitName.isEmpty()) {
            addCommUnitName.setStyle("-fx-border-color: red");
            return;
        } else {
            addCommUnitName.setStyle("-fx-border-color: green");
        }
        if (commUnitAreaStr.isEmpty()) {
            addCommUnitArea.setStyle("-fx-border-color: red");
            return;
        } else {
            addCommUnitArea.setStyle("-fx-border-color: green");
        }
        if (commUnitPriceStr.isEmpty()) {
            addCommUnitPrice.setStyle("-fx-border-color: red");
            return;
        } else {
            addCommUnitPrice.setStyle("-fx-border-color: green");
        }

        int commUnitArea = Integer.parseInt(commUnitAreaStr);

        float commUnitPrice = Float.parseFloat(commUnitPriceStr);

        String confirmBody = "اضافة وحده تجاريه"
                + System.lineSeparator() + "باسم: " + commUnitName
                + System.lineSeparator() + "المساحه: " + commUnitAreaStr
                + System.lineSeparator() + "بسعر: " + commUnitPriceStr,
                confirmHeader = "تأكيد اضافة وحده تجاريه";
        if (confirmProcess(confirmHeader, confirmBody)) {
            module.addCommUnit(buildingId, commUnitArea, commUnitPrice,
                    commUnitName);
            commercialsUIProviding();
            addCommUnitName.setText("");
            addCommUnitArea.setText("");
            addCommUnitPrice.setText("");
        }

    }

    private boolean confirmProcess(String headerStr, String confirmationStr) {
        Alert confirmationAlert
                = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.getDialogPane().setNodeOrientation(
                NodeOrientation.RIGHT_TO_LEFT);
        confirmationAlert.setTitle("تأكيد");
        confirmationAlert.setHeaderText(headerStr);
        confirmationAlert.setContentText(confirmationStr);
        Optional<ButtonType> result = confirmationAlert
                .showAndWait();
        return result.get() == ButtonType.OK;
    }

    private void modelsUIProviding() {
        modelsHBox.getChildren().clear();
        int modelsNum = module.getBuildingModelsCount(buildingId);
        Text modelsNumTxt = new Text();
        modelsNumTxt.setId("childText");
        modelsNumTxt.setText("عدد النماذج : " + modelsNum);
        Button modelsDetailsBtn = new Button("↓");
        modelsDetailsBtn.setPrefSize(5.0, 5.0);
        modelsDetailsBtn.setStyle("-fx-background-color:#ffcc33;");
        modelsDetailsBtn.setOnMouseClicked((MouseEvent event) -> {
            Button btn = (Button) event.getSource();
            showModels(btn);
        });
        modelsHBox.getChildren().addAll(modelsNumTxt, modelsDetailsBtn);
    }

    private void showModels(Button btn) {
        modelsDetails_anchPn.getChildren().clear();
        if (modelsIsShown) {
            modelsDetailsScrlPn.setVisible(false);
            btn.setText("↓");
            modelsIsShown = false;
        } else {
            btn.setText("↑");
            modelsIsShown = true;
            FadeTransition fadeTransition
                    = new FadeTransition(Duration.seconds(1.00),
                            modelsDetailsScrlPn);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
            modelsDetailsScrlPn.setVisible(true);
            float price;
            int modelArea;
            String name;
            VBox vbox = new VBox();
            vbox.setNodeOrientation(RIGHT_TO_LEFT);
            vbox.setStyle("-fx-background-color: white");
            AnchorPane.setRightAnchor(vbox, 0.0);

            for (ModelTemplate model : models) {
                Text modelsDetailsTxt = new Text();
                price = model.getPrice();
                modelArea = model.getArea();
                name = model.getName();
                modelsDetailsTxt.
                        setText("نموذج : " + name + " مساحته : "
                                + modelArea + " و سعره : "
                                + String.format("%.1f", price));
                vbox.getChildren().add(modelsDetailsTxt);

            }
            FadeTransition ft = new FadeTransition(Duration.seconds(1.30),
                    vbox);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            modelsDetails_anchPn.getChildren().add(vbox);
        }
    }
}
