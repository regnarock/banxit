package com.banxit.actions;

import com.gluonhq.particle.annotation.ParticleActions;
import com.gluonhq.particle.application.ParticleApplication;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.inject.Inject;
import org.controlsfx.control.action.ActionProxy;

@ParticleActions
public class MenuActions {

    @Inject ParticleApplication app;

    @ActionProxy(text="Exit", accelerator="alt+F4")
    private void exit() {
        app.exit();
    }
    
    @ActionProxy(text="About")
    private void about() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Gluon Desktop");
        alert.setHeaderText("About Gluon Desktop");
        alert.setGraphic(new ImageView(new Image(MenuActions.class.getResource("/icon.png").toExternalForm(), 48, 48, true, true)));
        alert.setContentText("This is a basic Gluon Desktop Application");
        alert.showAndWait();
    }
        
}