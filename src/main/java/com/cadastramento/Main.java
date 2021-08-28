package com.cadastramento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.cadastramento.client.RESTClient;
import com.cadastramento.enumeration.Scenes;
import com.cadastramento.interfaces.OnChangeServerStatus;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;


public class Main extends Application {

    private static Stage actualStage;

    private static ObservableList<String> estadosData = FXCollections.observableArrayList();

    private static Scene login;
    private static Scene main;
    private static Scene list;
    private static Scene include;
    private static Scene exclude;
    private static Scene consult;
    private static Scene alter;

    private static AtomicBoolean serverStatus = new AtomicBoolean(false);

    private static ArrayList<OnChangeServerStatus> listeners = new ArrayList<>();

    public static void addOnChangeServerStatusListener(OnChangeServerStatus newListener) {
        listeners.add(newListener);
    }


    private static void notifyAllListeners(boolean serverStatus) {
        for (OnChangeServerStatus listener : listeners) {
            listener.onChangeServerStatus(serverStatus );
        }
    }

    private static void notifyAllListeners(ObservableList<String> estados) {
        for (OnChangeServerStatus listener : listeners) {
            listener.onChangeEstadosStatus(estados);
        }
    }


    @Override
    public void start(Stage primaryStage) {

        actualStage = primaryStage;

        try {

            Parent loginParent = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
            login = new Scene(loginParent, 770, 490);

            Parent mainParent = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
            main = new Scene(mainParent, 770, 490);

            Parent listParent = FXMLLoader.load(getClass().getResource("listScene.fxml"));
            list = new Scene(listParent, 770, 490);

            Parent includeParent = FXMLLoader.load(getClass().getResource("includeScene.fxml"));
            include = new Scene(includeParent, 770, 490);

            Parent excludeParent = FXMLLoader.load(getClass().getResource("excludeScene.fxml"));
            exclude = new Scene(excludeParent, 770, 490);

            Parent consultParent = FXMLLoader.load(getClass().getResource("consultScene.fxml"));
            consult = new Scene(consultParent, 770, 490);

            Parent alterParent = FXMLLoader.load(getClass().getResource("alterScene.fxml"));
            alter = new Scene(alterParent, 770, 490);

            //actualStage.getIcons().add(new Image(Main.class.getResourceAsStream("logo.ico")));
            actualStage.getIcons().add(new Image(Main.class.getResourceAsStream("logo.ico"))) ;
            actualStage.setTitle("CADASTRAMENTO");
            actualStage.setScene(login);
            actualStage.setResizable(false);
            actualStage.show();


        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static void alterScene(Scenes scene) {

        switch (scene){
            case ALTER:
                actualStage.setScene(alter);
                break;
            case CONSULT:
                actualStage.setScene(consult);
                break;
            case EXCLUDE:
                actualStage.setScene(exclude);
                break;
            case INCLUDE:
                actualStage.setScene(include);
                break;
            case LIST:
                actualStage.setScene(list);
                break;
            case MAIN:
                actualStage.setScene(main);
                break;
            default:
        }

        clearTextFieldsOnLoad(actualStage.getScene().getRoot());

    }

    // clear all text fields on load
    private static void clearTextFieldsOnLoad(Parent parent) {

        BorderPane root = (BorderPane) parent;

        root.getChildren().forEach(	node -> {

            if(node instanceof Pane) {
                ((Pane)node).getChildren().forEach( child -> {
                    if (child instanceof TextField) {
                        // clear
                        ((TextField)child).clear();
                    }

                    if(child instanceof Pane) {
                        ((Pane)child).getChildren().forEach( childInner ->{
                            if (childInner instanceof TextField) {
                                // clear
                                ((TextField)childInner).clear();
                            }
                        });
                    }
                });
            }


        });

    }

    public static void main(String[] args) {

        ScheduledExecutorService scheduledES = Executors.newScheduledThreadPool(3);

        // verificando status do servidor a cada 3 segundos
        scheduledES.scheduleAtFixedRate(() -> {

            boolean status = RESTClient.checkWS();
            if( !status || !serverStatus.get()) {
                serverStatus.set(status);
                notifyAllListenersAfter(serverStatus.get());
            };

        }, 1, 3, TimeUnit.SECONDS);


        scheduledES.schedule(new Runnable() {
            @Override
            public void run() {

                while(!serverStatus.get()) {}
                if(estadosData.isEmpty()) {

                    Optional<List<String>> resultado = Optional.ofNullable(RESTClient.getEstados());

                    if(resultado.isPresent())
                        estadosData.addAll(resultado.get());

                    notifyAllListeners(estadosData);
                }
            }
        }, 2, TimeUnit.SECONDS);


        launch(args);

        scheduledES.shutdown();


    }


    private static void notifyAllListenersAfter(boolean wsStatus) {

        try {
            Platform.runLater(()->{

                notifyAllListeners(wsStatus);

            });
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


}
