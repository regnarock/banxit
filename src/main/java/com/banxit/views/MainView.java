package com.banxit.views;

import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.application.ParticleApplication;
import com.gluonhq.particle.view.View;
import com.gluonhq.particle.view.ViewManager;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javax.inject.Inject;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;

@ParticleView(name = "main")
public class MainView implements View {

    @Inject ParticleApplication app;
    
    @Inject private ViewManager viewManager;

    private final VBox controls = new VBox(15);
    
    private Action actionHome;
    
    @Inject private ResourceBundle resourceBundle;

    @Override
    public void init() {
        ActionMap.register(this);
        actionHome =  ActionMap.action("goHome");
        
        Label label = new Label(resourceBundle.getString("label.text"));

        Button button = new Button(resourceBundle.getString("button.text"));
        button.setOnAction(e -> viewManager.switchView("login"));
        
        controls.getChildren().addAll(label, button);
        controls.setAlignment(Pos.CENTER);
    }

    @Override
    public Node getContent() {
        return controls;
    }
        
    @Override
    public void start() {
        app.getParticle().getToolBarActions().add(0, actionHome);
    }
    
    @Override
    public void stop() {
        app.getParticle().getToolBarActions().remove(actionHome);
    }
    
    @ActionProxy(text = "Go Back")
    private void goHome() {
        viewManager.switchView("login");
    }
}
