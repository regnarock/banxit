package com.banxit.views;

import com.banxit.Credentials;
import com.banxit.dialogs.CreateUserDialog;
import com.banxit.dialogs.PasswordDialog;
import com.gluonhq.particle.annotation.ParticleView;
import com.gluonhq.particle.application.ParticleApplication;
import com.gluonhq.particle.state.StateManager;
import com.gluonhq.particle.view.View;
import com.gluonhq.particle.view.ViewManager;

import java.util.Map;
import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;

@ParticleView(name = "login", isDefault = true)
public class LoginView implements View {

    @Inject
    ParticleApplication app;

    @Inject
    private ViewManager viewManager;

    @Inject
    private StateManager stateManager;

    private final VBox controls = new VBox(15);
    private Label label;

    @Inject
    private ResourceBundle resourceBundle;

    private Action actionSignin;
    private Action actionSignUp;

    @Override
    public void init() {
        ActionMap.register(this);
        actionSignin = ActionMap.action("signin");
        actionSignUp = ActionMap.action("signup");
        label = new Label();

        Button button = new Button(resourceBundle.getString("button.text"));
        button.setOnAction(e -> viewManager.switchView("main"));

        controls.getChildren().addAll(label, button);
        controls.setAlignment(Pos.CENTER);

        stateManager.setPersistenceMode(StateManager.PersistenceMode.USER);
    }

    @Override
    public Node getContent() {
        return controls;
    }

    @Override
    public void start() {
        app.getParticle().getToolBarActions().add(0, actionSignin);
        app.getParticle().getToolBarActions().add(0, actionSignUp);
    }

    @Override
    public void stop() {
        app.getParticle().getToolBarActions().remove(actionSignin);
        app.getParticle().getToolBarActions().remove(actionSignUp);
    }

    public void addUser(String userName) {
        label.setText(resourceBundle.getString("label.text") + (userName.isEmpty() ? "" : ", " + userName) + "!");
    }

    public void createUser(Credentials credentials) {
        addUser(credentials.username);
        String sha256hex = DigestUtils.sha256Hex(credentials.password);
        stateManager.setProperty("Password", sha256hex);
    }

    @ActionProxy(text = "Sign In")
    private void signin() {
        TextInputDialog input = new TextInputDialog(stateManager.getProperty("UserName").orElse("").toString());
        input.setTitle("User name");
        input.setHeaderText(null);
        input.setContentText("Input your name:");
        input.showAndWait().ifPresent(this::addUser);
    }

    @ActionProxy(text = "Sign Up")
    private void signup() {
        CreateUserDialog pd = new CreateUserDialog();
        pd.showAndWait().ifPresent(this::createUser);
    }

}