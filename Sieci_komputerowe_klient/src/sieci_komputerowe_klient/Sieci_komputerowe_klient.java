/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sieci_komputerowe_klient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import protokoly.SMessage;
import protokoly.Sign_in_up;
import protokoly.Sign_in_up_info;

/**
 *
 * @author Marcin
 */
public class Sieci_komputerowe_klient extends Application {
    //private HBox logowanie_buttons;
    Stage Stage2;
    Stage Stage3;
    Connection con = new Connection();
    ObservableList<String> listView = null;
    ListView<String> listViewAll = new ListView<String>();
    ListView<String> chatList = new ListView<String>();
    ArrayList<Client> clients = new ArrayList<Client>();
    Message tmpMsg;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Projekt GG");
        primaryStage.setHeight(200);
        primaryStage.setWidth(300);
        
        Label LLogin = new Label("Login:");
        LLogin.setFont(Font.font(null, FontWeight.BOLD, 15));
        TextField TFLogin = new TextField();
        Label LPass = new Label("Hasło:");
        LPass.setFont(Font.font(null, FontWeight.BOLD, 15));
        PasswordField TFPass = new PasswordField();
        Button BLogin = new Button("Zaloguj");
        BLogin.setStyle("-fx-font: 16 arial; -fx-base: #2771e7;");
        
        Button BRegister = new Button("Zarejestruj");
        BRegister.setStyle("-fx-font: 16 arial; -fx-base: #2771e7;");
        GridPane GP_login_view = new GridPane();
        GP_login_view.setStyle("-fx-background-color: #FFFFFF");
        GP_login_view.setAlignment(Pos.CENTER);
        GP_login_view.setHgap(5);
        GP_login_view.setVgap(10);
        GP_login_view.add(LLogin, 0, 1);
        GP_login_view.add(TFLogin, 1, 1);
        GP_login_view.add(LPass, 0, 2);
        GP_login_view.add(TFPass, 1, 2);
        GP_login_view.add(BLogin, 0, 3);
        GP_login_view.add(BRegister, 1, 3);
        
        
        BLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Sign_in_up sign_in = new Sign_in_up(TFLogin.getText(), TFPass.getText(), 01);
                System.out.println(sign_in.objectToJSONString());
                try {
                    con.sendRequest(sign_in.objectToJSONString());
                } catch (IOException ex) {
                    Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Sign_in_up_info sign_in_info = new Sign_in_up_info();
                while(true){
                    try {
                        sign_in_info = (Sign_in_up_info) con.reciveInfo();
                    } catch (IOException ex) {
                        Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(sign_in_info != null){
                        System.out.println(sign_in_info.getInfo());
                        if(sign_in_info.getCorrect()){
                            primaryStage.close();
                            showUsers();
                        }
                        else{
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setHeaderText(null);
                            alert.setContentText("Logowanie zakończone niepowodzeniem!");
                            alert.showAndWait();
                        }
                        break;
                    }
                }
            }
        });
        BRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(TFLogin.getText().isEmpty()||TFPass.getText().isEmpty()){
                    Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Informacja");
                                alert.setHeaderText(null);
                                alert.setContentText("Login lub hasło puste!");
                                alert.showAndWait();
                }else{
                    Sign_in_up sign_up = new Sign_in_up(TFLogin.getText(), TFPass.getText(), 00);
                    System.out.println(sign_up.objectToJSONString());
                    try {
                        con.sendRequest(sign_up.objectToJSONString());
                    } catch (IOException ex) {
                        Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Sign_in_up_info sign_up_info = new Sign_in_up_info();
                    while(true){
                        try {
                            sign_up_info = (Sign_in_up_info) con.reciveInfo();
                        } catch (IOException ex) {
                            Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(sign_up_info != null){
                            System.out.println(sign_up_info.getInfo());
                            if(sign_up_info.getCorrect()){
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Informacja");
                                alert.setHeaderText(null);
                                alert.setContentText("Rejestracja zakończona pomyślnie!");
                                alert.showAndWait();
                            }
                            else{
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Informacja");
                                alert.setHeaderText(null);
                                alert.setContentText("Rejestracja zakończona niepowodzeniem!");
                                alert.showAndWait();
                            }
                            break;
                        }
                    }
                }
            }
        });
        
        Scene scene = new Scene(GP_login_view, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            try{
                con.close_Connection();
            }catch (IOException ex) {
                   Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
            }
                   Platform.exit();
                    System.exit(0);
        });
        
            
        //root.getChildren().add(btn);
        
    }

    public void showUsers(){
        Stage2 = new Stage();
        Stage2.setMaxHeight(498);
        Stage2.setMaxWidth(160);
        Group groupUser = new Group();
        Stage2.setResizable(false);
        VBox vbox = new VBox();
        vbox.setMaxWidth(160);
        vbox.setMaxHeight(480);
        vbox.setPadding(new Insets(2,2,2,3));
        listViewAll.setPadding(new Insets(2,1,1,1));
        listViewAll.setMaxWidth(160);
        Label Llista = new Label("Lista\nużytkowników : ");
        Llista.setMaxSize(160, 60);
        Llista.setPadding(new Insets(2,5,5,10));
        
        Llista.setTextAlignment(TextAlignment.CENTER);
        Llista.setFont(Font.font(null, FontWeight.BOLD, 17));
		// a horizontal panel to hold the list view and the label.
		
		// the text to be displayed when clicking on a new item in the list.

                
		//listViewAll.setMaxSize(100,500);
		listViewAll.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					public void changed(
							ObservableValue<? extends String> observable,
							String oldValue, String newValue) {
                                                showChat(newValue);
                                                
						// change the label text value to the newly selected
						// item.
						//label.setText("You Selected " + newValue);
					}
				});
                 
		vbox.getChildren().addAll(Llista,listViewAll);
                
                groupUser.getChildren().addAll(vbox);
                
                
		Scene scene = new Scene(groupUser);
		//Stage2.setTitle("List View Selector");
		Stage2.setScene(scene);
		Stage2.show();
                
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean pom = true;
                        int pomInt = 0;
                        while(true){
                        listView = con.getListView();
                        while(listView.size() > clients.size()){
                            //Client tmpClient = new Client(listView.get(clients.size()));
                            clients.add(new Client(listView.get(clients.size())));
                        }
                            
                        listViewAll.setItems(listView);
                            try {
                                tmpMsg = con.reciveData();
                                if(tmpMsg != null){
                                    pomInt = -1;
                                    pom = true;
                                    for(int i=0; i<clients.size(); i++){
                                        if(clients.get(i).getName().equals(tmpMsg.getName())){
                                            pom = false;
                                            pomInt = i;
                                        }
                                    }
                                    if(pom){
                                        clients.add(new Client(tmpMsg.getName()));
                                    }
                                    if(pomInt > -1){
                                        clients.get(pomInt).addMessage(tmpMsg);
                                    }
                                    
                                }
                                Thread.currentThread().sleep(5);
                            } catch (IOException ex) {
                                Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ParseException ex) {
                                Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }, "Thread");
                thread.start();
                
                Stage2.setOnCloseRequest(e -> {
                    try {
                        con.close_Connection();
                    } catch (IOException ex) {
                        Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Platform.exit();
                    System.exit(0);
                    }
                );
                
    }
    public void showChat(String user){
        Stage3 = new Stage();
        Stage3.setWidth(450);
        Stage3.setHeight(310);
        
                Group chat = new Group();
                        // a horizontal panel to hold the list view and the label.
                        VBox box = new VBox();
                        box.setSpacing(3);

                        TextArea TAtext = new TextArea();
                        TAtext.setWrapText(true);
                        
                        TAtext.setEditable(false);
                        TAtext.textProperty().addListener(new ChangeListener<Object>() {
                            @Override
                            public void changed(ObservableValue<?> observable, Object oldValue,
                                    Object newValue) {
                                TAtext.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
                                //use Double.MIN_VALUE to scroll to the top
                            }
                        });
                        
                        HBox hbox = new HBox();
                        hbox.setMaxSize(430,50);
                        TAtext.setMaxSize(430, 300);
                        TextField TFtext = new TextField();
                        TFtext.setPrefSize(282, 30);
                       
                        
                        Button BSend = new Button("Wyślij wiadomość");
                        BSend.setStyle("-fx-font: 16 arial; -fx-base: #2771e7;");
                        
                        hbox.getChildren().addAll(TFtext, BSend);
                        
                        chatList.prefWidth(100);
                        chatList.setMaxWidth(100);
                        
                        box.getChildren().addAll(TAtext, hbox);
                        chat.getChildren().addAll(box);

                        Scene scene1 = new Scene(chat);
                        Stage3.setTitle(user);
                        Stage3.setScene(scene1);
                        Stage3.show();
                        
                        BSend.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            SMessage msg = new SMessage(TFtext.getText(), 30, user);
                            System.out.println(msg.objectToJSONString());
                            int pomInt = -1;
                                    for(int i=0; i<clients.size(); i++){
                                        if(clients.get(i).getName().equals(user)){
                                            pomInt = i;
                                        }
                                    }
                                    clients.get(pomInt).addMessage(new Message(user, TFtext.getText(), true));
                                    TFtext.clear();
                            
                            try {
                                con.sendRequest(msg.objectToJSONString());
                            } catch (IOException ex) {
                                Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                        
                        
                    Thread threadChat = new Thread(new Runnable() {
                    @Override
                    public void run() {
                                boolean pom = true;
                                int pomInt = 0;
                                int sizeOfString = 0;
                                while(true){
                                String tmpString = "";
                                    pomInt = -1;
                                    pom = true;
                                    for(int i=0; i<clients.size(); i++){
                                        if(clients.get(i).getName().equals(user)){
                                            pom = false;
                                            pomInt = i;
                                        }
                                    }
                                    if(pom){
                                        clients.add(new Client(user));
                                    }
                                    for(int i=0; i<clients.get(pomInt).getListOfMessage().size(); i++){
                                        if(clients.get(pomInt).getListOfMessage().get(i).isOwner()){
                                            tmpString += "JA: " + clients.get(pomInt).getListOfMessage().get(i).getText() + "\n";
                                            System.out.println(tmpString);
                                            
                                        }
                                        else
                                            tmpString += clients.get(pomInt).getListOfMessage().get(i).getName() + ": " + clients.get(pomInt).getListOfMessage().get(i).getText() + "\n";
                                            System.out.println(tmpString);
                                    }
                                    if(sizeOfString != tmpString.length()){
                                        sizeOfString = tmpString.length();
                                        TAtext.setText(tmpString);
                                        TAtext.appendText("");
                                    }
                                    try {
                                        Thread.currentThread().sleep(10);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(Sieci_komputerowe_klient.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                    }
                }, "ThreadChat");
                threadChat.start();
                        
                        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
