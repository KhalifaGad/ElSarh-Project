package com.khalifa.elsarh.controllers;

import static com.khalifa.elsarh.MainApp.DirPath;
import static com.khalifa.elsarh.controllers.SideBarController.scenesHeight;
import static com.khalifa.elsarh.controllers.SideBarController.scenesWidth;
import static com.khalifa.elsarh.controllers.UnitBookingController.freshClientId;
import static com.khalifa.elsarh.controllers.UnitBookingController.renderingClientTrack;
import com.khalifa.elsarh.modules.ClientProfileModule;
import com.khalifa.elsarh.pojos.ClientAgentModel;
import com.khalifa.elsarh.pojos.ClientModel;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author khalifa
 */
public class ClientProfileController implements Initializable {

    @FXML
    HBox clientNameHBox, financialDetailsHB, agentHB;

    @FXML
    AnchorPane clientImages_AnchPn, profilContainer, clientProfileAncPn;

    @FXML
    VBox clientDetailsVBox;

    @FXML
    ScrollPane clientImages_ScPane;

    @FXML
    ImageView imgSavingIV;

    @FXML
    Button imgAddBtn1, saveImgsBtn, deleteClientBtn;

    static List<Integer> clientUnits = new ArrayList();
    static List<Integer> clientRestoredUnits = new ArrayList();
    static List<Integer> clientTransitionedUnits = new ArrayList();

    double imgsAncPnSpacing;
    public static int clientId;
    List<String> clientImagesPathsList = new ArrayList<>();

    static int renderedClientId;
    ClientProfileModule module;
    String clientName;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        module = new ClientProfileModule();
        switch (renderingClientTrack) {
            case "fromUnitBooking":
                clientId = freshClientId;
                break;
            case "fromeInside":
                clientId = renderedClientId;
                break;
            default:
                clientId = ClientsSearchController.clientID;
                break;
        }

        getClientInfo(clientId);

        Image img = new Image("/images/rightIcon.png");
        imgSavingIV.setImage(img);
        AnchorPane.setTopAnchor(profilContainer, scenesHeight * 0.08);
        AnchorPane.setTopAnchor(clientNameHBox, scenesHeight * 0.02);
        AnchorPane.setTopAnchor(clientDetailsVBox, scenesHeight * 0.1);

        AnchorPane.setTopAnchor(financialDetailsHB, scenesHeight * 0.42);
        AnchorPane.setTopAnchor(agentHB, scenesHeight * 0.48);
        AnchorPane.setTopAnchor(clientImages_ScPane, scenesHeight * 0.58);
        AnchorPane.setTopAnchor(imgAddBtn1, scenesHeight * 1.165);
        AnchorPane.setTopAnchor(saveImgsBtn, scenesHeight * 1.16);
        AnchorPane.setTopAnchor(deleteClientBtn, scenesHeight * 1.3);

        AnchorPane.setRightAnchor(saveImgsBtn, scenesWidth * 0.52);
        AnchorPane.setRightAnchor(imgAddBtn1, scenesWidth * 0.46);
        AnchorPane.setRightAnchor(financialDetailsHB, scenesWidth * 0.018);
        AnchorPane.setRightAnchor(agentHB, scenesWidth * 0.018);
        AnchorPane.setRightAnchor(profilContainer, scenesWidth * 0.07);
        AnchorPane.setRightAnchor(clientDetailsVBox, scenesWidth * 0.1);
        AnchorPane.setLeftAnchor(deleteClientBtn, scenesWidth * 0.1);

        saveImgsBtn.setDisable(true);
    }

    private void getClientInfo(int clientId) {

        ClientModel client = module.getClientInfo(clientId);

        clientName = client.getName();
        String peronsalId = client.getPersonalId(),
                mobile1 = client.getMobile1(),
                mobile2 = client.getMobile2(),
                career = client.getCareer(),
                address = client.getAddress(),
                email = client.getEmail();
        boolean mediaCheck = client.isMediaExist();

        dataUIProviding(career, peronsalId, mobile1, mobile2, clientId,
                clientName, address, email);
        if (mediaCheck) {
            getImages();
        }

    }

    private void dataUIProviding(String career, String personalId,
            String mobile1, String mobile2, int clientId, String clinetName,
            String address, String email) {
        clientNameUIProviding(clinetName);
        personalInfoUIProviding(career, personalId, mobile1, mobile2, clientId,
                address, email);
    }

    private void clientNameUIProviding(String clientName) {
        clientNameHBox.getChildren().clear();
        Text headTxt = new Text();
        headTxt.setId("headTxt");
        headTxt.setText("  " + clientName + "   ");
        clientNameHBox.getChildren().add(headTxt);
    }

    private void personalInfoUIProviding(String career, String personalId,
            String mobile1, String mobile2, int clientId, String address,
            String email) {

        clientDetailsVBox.getChildren().clear();

        Text careerTxt = new Text();
        careerTxt.setId("childeTxt");
        careerTxt.setText("الوظيفه:  " + career);

        Button changeCareerBtn = new Button("تغير");
        changeCareerBtn.setId("financeBtnVB");
        changeCareerBtn.setPrefWidth(scenesWidth * 0.05);
        changeCareerBtn.setOnMouseClicked(event -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            dialog.setTitle("ادخال معلومات");
            dialog.setHeaderText("تغير مهنة العميل");
            dialog.setContentText("من فضلك قم بأدخال المهنه الجديده: ");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newCreer -> {
                module.updateCareer(clientId, newCreer);
                getClientInfo(clientId);
            });
        });

        HBox careerHB = new HBox();
        careerHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        careerHB.setAlignment(Pos.CENTER_LEFT);
        careerHB.setSpacing(scenesWidth * 0.05);
        careerHB.getChildren().addAll(careerTxt, changeCareerBtn);

        Text emailTxt = new Text();
        emailTxt.setId("childeTxt");
        emailTxt.setText("البريد الالكترونى: " + email);

        Button changeEmailBtn = new Button("تغير");
        changeEmailBtn.setId("financeBtnVB");
        changeEmailBtn.setPrefWidth(scenesWidth * 0.05);
        changeEmailBtn.setOnMouseClicked(event -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            dialog.setTitle("ادخال معلومات");
            dialog.setHeaderText("تغير ايميل العميل");
            dialog.setContentText("من فضلك قم بأدخال الايميل الجديد: ");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newEmail -> module.updateEmail(clientId, newEmail));
            getClientInfo(clientId);
        });

        HBox emailHB = new HBox();
        emailHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        emailHB.setAlignment(Pos.CENTER_LEFT);
        emailHB.setSpacing(scenesWidth * 0.05);
        emailHB.getChildren().addAll(emailTxt, changeEmailBtn);

        Text addressTxt = new Text();
        addressTxt.setId("childeTxt");
        addressTxt.setText("عنوان المراسله: " + address);

        Button changeAddressBtn = new Button("تغير");
        changeAddressBtn.setId("financeBtnVB");
        changeAddressBtn.setPrefWidth(scenesWidth * 0.05);
        changeAddressBtn.setOnMouseClicked(event -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            dialog.setTitle("ادخال معلومات");
            dialog.setHeaderText("تغير عنوان مراسلة العميل");
            dialog.setContentText("من فضلك قم بأدخال عنوان مراسله الجديد: ");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newAddress -> module.updateAddress(clientId,
                    newAddress));
            getClientInfo(clientId);
        });

        HBox addressHB = new HBox();
        addressHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        addressHB.setAlignment(Pos.CENTER_LEFT);
        addressHB.setSpacing(scenesWidth * 0.05);
        addressHB.getChildren().addAll(addressTxt, changeAddressBtn);

        Text perspnalIdTxt = new Text();
        perspnalIdTxt.setId("childeTxt");
        perspnalIdTxt.setText("رقم تعريف الشخصيه :  " + personalId);

        Text phoneSymbolTxt = new Text();
        String phoneSymbol = "\uD83D\uDCF1";
        phoneSymbolTxt.setText(phoneSymbol);
        phoneSymbolTxt.setStyle("-fx-font-size: 1.3em;");

        Text mobile1Txt = new Text();
        mobile1Txt.setId("childeTxt");
        mobile1Txt.setText(" : " + mobile1);

        Button exchangeMobile1Btn = new Button("تغير");
        exchangeMobile1Btn.setId("financeBtnVB");
        exchangeMobile1Btn.setPrefWidth(scenesWidth * 0.05);
        exchangeMobile1Btn.setOnMouseClicked(event -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            dialog.setTitle("ادخال معلومات");
            dialog.setHeaderText("رقم الجوال الاول");
            dialog.setContentText("من فضلك قم بأدخال الرقم");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(foneNum -> module.updateMobile1(clientId, foneNum));
            getClientInfo(clientId);
        });

        HBox mobile1HB = new HBox();
        mobile1HB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        mobile1HB.setAlignment(Pos.CENTER_LEFT);
        mobile1HB.getChildren().addAll(phoneSymbolTxt, mobile1Txt,
                exchangeMobile1Btn);

        Text phoneSymbolTxtdash = new Text();
        phoneSymbolTxtdash.setText(phoneSymbol);
        phoneSymbolTxtdash.setStyle("-fx-font-size: 1.3em;");

        Text mobile2Txt = new Text();
        mobile2Txt.setId("childeTxt");
        mobile2Txt.setText(" : " + mobile2);

        Button exchangeMobile2Btn = new Button("تغير");
        exchangeMobile2Btn.setId("financeBtnVB");
        exchangeMobile2Btn.setPrefWidth(scenesWidth * 0.05);
        exchangeMobile2Btn.setOnMouseClicked(event -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            dialog.setTitle("ادخال معلومات");
            dialog.setHeaderText("رقم الجوال الثانى");
            dialog.setContentText("من فضلك قم بأدخال الرقم");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(foneNum -> module.updateMobile2(clientId, foneNum));
            getClientInfo(clientId);

        });

        HBox mobile2HB = new HBox();
        mobile2HB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        mobile2HB.setAlignment(Pos.CENTER_LEFT);
        mobile2HB.getChildren().addAll(phoneSymbolTxtdash, mobile2Txt,
                exchangeMobile2Btn);

        HBox mobileHB = new HBox();
        mobileHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        mobileHB.setAlignment(Pos.CENTER_LEFT);
        mobileHB.setSpacing(scenesWidth * 0.05);
        mobileHB.getChildren().addAll(mobile1HB, mobile2HB);

        Text clientCodeText = new Text();
        clientCodeText.setId("childeTxt");
        clientCodeText.setText("كود العميل  : " + clientId);

        clientDetailsVBox.getChildren().addAll(clientCodeText, careerHB,
                perspnalIdTxt, mobileHB, emailHB, addressHB);
    }

    @FXML
    private void addClientImage(MouseEvent event) {
        Button btn = new Button();
        btn.setId("btnImagesId");
        btn.setPrefHeight(scenesHeight * 0.535);
        btn.setPrefWidth(scenesWidth * 0.22);
        btn.setOnMouseClicked((MouseEvent event1) -> {
            uploadModelImage(event1);
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
        imgsAncPnSpacing += scenesWidth * 0.24;
        clientImages_AnchPn.getChildren().add(btn);
    }

    private void uploadModelImage(MouseEvent event) {
        FileChooser imageChooser = new FileChooser();
        imageChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));
        Node source = (Node) event.getSource();
        Window stage = source.getScene().getWindow();
        File choosenFile = imageChooser.showOpenDialog(stage);

        if (choosenFile != null) {

            String clientImagesPath = DirPath + "/ClientsDBImages/"
                    + clientName + "-" + clientId;
            File directory = new File(clientImagesPath);
            if (!directory.exists()) {
                directory.mkdir();
            }
            String DBImgsPath = clientImagesPath + "/";
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
            if (!clientImagesPathsList.contains(targetFile.toURI().toString())) {
                clientImagesPathsList.add(targetFile.toURI().toString());
            }
            saveImgsBtn.setDisable(false);
        }
    }

    @FXML
    private void saveImages() {
        module.seveImages(clientImagesPathsList, clientId);
        module.clientHasMedia(clientId);
        clientImagesPathsList.clear();
        saveImgsBtn.setDisable(true);
        getImages();
    }

    private void getImages() {

        List<String> imgsPaths = module.getImages(clientId);
        clientImages_AnchPn.getChildren().clear();
        imgsAncPnSpacing = 0;
        showImgNode(imgsPaths);

    }

    private void showImgNode(final List<String> imgsPaths) {

        for (final String imgPath : imgsPaths) {
            AnchorPane ancPn = new AnchorPane();
            ancPn.setId("bkgroundLayer");
            ancPn.setPrefHeight(scenesHeight * 0.54);
            ancPn.setPrefWidth(scenesWidth * 0.22);
            AnchorPane.setTopAnchor(ancPn, scenesHeight * 0.01);
            AnchorPane.setRightAnchor(ancPn, imgsAncPnSpacing);
            Image img = new Image(imgPath);
            ImageView imgView = new ImageView();
            imgView.setImage(img);
            imgView.setFitWidth(scenesWidth * 0.22);
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
                                                ClientProfileController.class.
                                                        getName()).
                                                log(Level.SEVERE, null, ex);
                                    }
                                }).start();
                            }
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(ClientProfileController.class.
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
                String confrimationBody = "تأكيد مسح الصوره من ملف العميل";
                if (confirmProcess(null, confrimationBody)) {

                    module.deleteImage(clientId, imgPath);
                    FileUtils.deleteQuietly(new File(imgPath));

                    if (imgsPaths.size() == 1) {
                        module.updateMediaCheck(clientId);
                    }
                    getImages();
                }
            });

            AnchorPane.setBottomAnchor(deletionBtn, scenesHeight * 0.005);
            AnchorPane.setLeftAnchor(deletionBtn, scenesWidth * 0.005);

            ancPn.getChildren().addAll(imgView, deletionBtn);

            AnchorPane.setTopAnchor(imgView, scenesHeight * 0.13);
            clientImages_AnchPn.getChildren().add(ancPn);
            imgsAncPnSpacing += scenesWidth * 0.24;
        }
    }

    @FXML
    private void renderUnitsWindow(MouseEvent event) throws IOException {

        clientUnits = module.getClientUnits(clientId);
        for (Integer unit : clientUnits) {
            System.out.println("reg Uni : " + unit);
        }
        clientRestoredUnits = module.getClientRestoredUnits(clientId);
        for (Integer unit : clientRestoredUnits) {
            System.out.println("restored Uni : " + unit);
        }
        clientTransitionedUnits = module.getClientTransitionedUnits(clientId);
        for (Integer unit : clientTransitionedUnits) {
            System.out.println("trans Uni : " + unit);
        }

        Node source = (Node) event.getSource();
        Window stage = source.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setWidth(stage.getWidth());
        newStage.setHeight(stage.getHeight());
        newStage.setTitle("Client : " + clientId + " units");

        Parent clientUnitsWindow = FXMLLoader.load(getClass().
                getResource("/fxml/clientUnits.fxml"));
        Scene clientUnitsSc = new Scene(clientUnitsWindow);

        newStage.setScene(clientUnitsSc);
        newStage.show();
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

    @FXML
    private void showAgentPane() {

        boolean agentExist = module.checkAgent(clientId);

        ToggleGroup agentPaneOptions = new ToggleGroup();

        ToggleButton showAgentTB = new ToggleButton();
        showAgentTB.setPrefWidth(400);
        showAgentTB.setText("الوكيل");
        showAgentTB.setToggleGroup(agentPaneOptions);
        showAgentTB.setDisable(!agentExist);

        ToggleButton editAgentTB = new ToggleButton();
        editAgentTB.setPrefWidth(400);
        editAgentTB.setText("تعديل");
        editAgentTB.setToggleGroup(agentPaneOptions);

        HBox togglesHB = new HBox();
        togglesHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        AnchorPane.setTopAnchor(togglesHB, 0.0);
        togglesHB.getChildren().addAll(showAgentTB, editAgentTB);

        AnchorPane showAgentAnchPn = new AnchorPane();
        showAgentAnchPn.visibleProperty().bind(showAgentTB.selectedProperty());
        AnchorPane.setTopAnchor(showAgentAnchPn, 50.0);
        AnchorPane.setRightAnchor(showAgentAnchPn, 0.0);

        showAgentTB.setOnMouseClicked((event) -> {
            showAgentLayout(showAgentAnchPn);
        });

        AnchorPane editAgentAnchPn = new AnchorPane();
        editAgentAnchPn.visibleProperty().bind(editAgentTB.selectedProperty());
        AnchorPane.setTopAnchor(editAgentAnchPn, 50.0);

        editAgentTB.setOnMouseClicked((event) -> {
            editAgentLayout(editAgentAnchPn, agentExist);
        });

        AnchorPane agentMainLayout = new AnchorPane();
        agentMainLayout.setPrefSize(800, 400);
        agentMainLayout.getChildren().addAll(togglesHB, showAgentAnchPn,
                editAgentAnchPn);

        // show the new stage.
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("الوكيل للعميل: " + clientId);
        Scene scene1 = new Scene(agentMainLayout, 800, 400);
        scene1.getStylesheets().add("/styles/clientprofile.css");
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
    }

    private void editAgentLayout(AnchorPane editAgentAncPn, boolean agenExist) {
        editAgentAncPn.getChildren().clear();

        HBox nameHB = new HBox();
        Text nameTxt = new Text("الاســــــــــــم: ");
        TextField nameTxtFld = new TextField();

        nameHB.setSpacing(30.0);
        nameHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        nameHB.setAlignment(Pos.CENTER_RIGHT);
        nameHB.getChildren().addAll(nameTxt, nameTxtFld);

        HBox careerHB = new HBox();
        Text careerTxt = new Text("الــمــهـــنـــه: ");
        TextField careerTxtFld = new TextField();

        careerHB.setSpacing(30.0);
        careerHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        careerHB.setAlignment(Pos.CENTER_RIGHT);
        careerHB.getChildren().addAll(careerTxt, careerTxtFld);

        HBox personalIdHB = new HBox();
        Text personalIdTxt = new Text("رقــم الـبـطاقـه: ");
        TextField personalIdTxtFld = new TextField();

        personalIdHB.setSpacing(30.0);
        personalIdHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        personalIdHB.setAlignment(Pos.CENTER_RIGHT);
        personalIdHB.getChildren().addAll(personalIdTxt, personalIdTxtFld);

        HBox mobile1HB = new HBox();
        Text mobile1Txt = new Text("رقـم الـجـوال 1 : ");
        TextField mobile1TxtFld = new TextField();

        mobile1HB.setSpacing(30.0);
        mobile1HB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        mobile1HB.setAlignment(Pos.CENTER_RIGHT);
        mobile1HB.getChildren().addAll(mobile1Txt, mobile1TxtFld);

        HBox mobile2HB = new HBox();
        Text mobile2Txt = new Text("رقـم الـجـوال 2 : ");
        TextField mobile2TxtFld = new TextField();

        mobile2HB.setSpacing(30.0);
        mobile2HB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        mobile2HB.setAlignment(Pos.CENTER_RIGHT);
        mobile2HB.getChildren().addAll(mobile2Txt, mobile2TxtFld);

        HBox emailHB = new HBox();
        Text emailTxt = new Text("البريد الالكترونى: ");
        TextField emailTxtFld = new TextField();

        emailHB.setSpacing(30.0);
        emailHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        emailHB.setAlignment(Pos.CENTER_RIGHT);
        emailHB.getChildren().addAll(emailTxt, emailTxtFld);

        HBox addressHB = new HBox();
        Text addressTxt = new Text("العنوان : ");
        TextField addressFld = new TextField();
        addressFld.setPrefWidth(400.0);

        addressHB.setSpacing(30.0);
        addressHB.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        addressHB.setAlignment(Pos.CENTER_RIGHT);
        addressHB.getChildren().addAll(addressTxt, addressFld);

        HBox actionHB = new HBox();
        Button editBtn = new Button("تعديل");
        editBtn.setPrefWidth(100);
        editBtn.setId("savingbttn");
        editBtn.setOnAction((event) -> {
            String name = nameTxtFld.getText(),
                    career = careerTxtFld.getText(),
                    presonalID = personalIdTxtFld.getText(),
                    mobile1 = mobile1TxtFld.getText(),
                    mobile2 = mobile2TxtFld.getText(),
                    email = emailTxtFld.getText(),
                    address = addressFld.getText();
            if (confirmProcess(null, "تأكيد اضفة بيانات وكيل ؟")) {
                module.addAgentData(clientId, name, career, presonalID, mobile1,
                        mobile2, address, email);
                editBtn.setStyle("-fx-background-color: green;");
            }
        });

        actionHB.setAlignment(Pos.CENTER);
        actionHB.setPrefWidth(770);
        actionHB.getChildren().add(editBtn);

        AnchorPane.setTopAnchor(nameHB, 0.0);
        AnchorPane.setTopAnchor(careerHB, 40.0);
        AnchorPane.setTopAnchor(personalIdHB, 80.0);
        AnchorPane.setTopAnchor(mobile1HB, 120.0);
        AnchorPane.setTopAnchor(mobile2HB, 160.0);
        AnchorPane.setTopAnchor(emailHB, 200.0);
        AnchorPane.setTopAnchor(addressHB, 240.0);
        AnchorPane.setTopAnchor(actionHB, 280.0);

        AnchorPane.setRightAnchor(nameHB, 30.0);
        AnchorPane.setRightAnchor(careerHB, 30.0);
        AnchorPane.setRightAnchor(personalIdHB, 30.0);
        AnchorPane.setRightAnchor(mobile1HB, 30.0);
        AnchorPane.setRightAnchor(mobile2HB, 30.0);
        AnchorPane.setRightAnchor(emailHB, 30.0);
        AnchorPane.setRightAnchor(addressHB, 30.0);
        AnchorPane.setRightAnchor(actionHB, 30.0);

        if (agenExist) {
            ClientAgentModel agent = module.getAgentData(clientId);
            nameTxtFld.setText(agent.getName());
            careerTxtFld.setText(agent.getCareer());
            personalIdTxtFld.setText(agent.getPersonalId());
            mobile1TxtFld.setText(agent.getMobile1());
            mobile2TxtFld.setText(agent.getMobile2());
            emailTxtFld.setText(agent.getEmail());
            addressFld.setText(agent.getAddress());
        }

        editAgentAncPn.getChildren().addAll(nameHB, careerHB, personalIdHB,
                mobile1HB, mobile2HB, emailHB, addressHB, actionHB);
    }

    private void showAgentLayout(AnchorPane showingAgentPane) {
        showingAgentPane.getChildren().clear();

        ClientAgentModel agent = module.getAgentData(clientId);

        Text name = new Text("الاسم: " + agent.getName());
        Text career = new Text("المهنه: " + agent.getCareer());
        Text personalId = new Text("رقم تعريف الهويه: " + agent.getPersonalId());
        Text mobile1 = new Text("جوال1: " + agent.getMobile1());
        Text mobile2 = new Text("جوال2: " + agent.getMobile2());
        Text email = new Text("البريد الالكترونى: " + agent.getEmail());
        Text address = new Text("العنوان : " + agent.getAddress());

        AnchorPane.setTopAnchor(name, 0.0);
        AnchorPane.setTopAnchor(career, 40.0);
        AnchorPane.setTopAnchor(personalId, 80.0);
        AnchorPane.setTopAnchor(mobile1, 120.0);
        AnchorPane.setTopAnchor(mobile2, 160.0);
        AnchorPane.setTopAnchor(email, 200.0);
        AnchorPane.setTopAnchor(address, 240.0);

        AnchorPane.setRightAnchor(name, 30.0);
        AnchorPane.setRightAnchor(career, 30.0);
        AnchorPane.setRightAnchor(personalId, 30.0);
        AnchorPane.setRightAnchor(mobile1, 30.0);
        AnchorPane.setRightAnchor(mobile2, 30.0);
        AnchorPane.setRightAnchor(email, 30.0);
        AnchorPane.setRightAnchor(address, 30.0);

        showingAgentPane.getChildren().addAll(name, career, personalId,
                mobile1, mobile2, email, address);
    }

    @FXML
    private void deleteClient() {
        String confirmationStr = "تأكيد مسح العميل و كل البيانات الخاصه به";
        if (confirmProcess(null, confirmationStr)) {
            module.deleteClient(clientId);

            StackPane mainAppStackPane = (StackPane) clientProfileAncPn.getParent();
            mainAppStackPane.getChildren().clear();

            try {
                AnchorPane loadedAncPane = (AnchorPane) FXMLLoader.load(getClass()
                        .getResource("/fxml/clientsSearch.fxml"));
                mainAppStackPane.getChildren().add(loadedAncPane);
            } catch (IOException ex) {
                Logger.getLogger(ClientsSearchController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

        }
    }

}
