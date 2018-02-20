package com.banxit.dialogs;

import com.banxit.Credentials;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.AbstractMap;
import java.util.Map;

public class CreateUserDialog extends Dialog<Credentials> {
  private PasswordField passwordField;
  private TextField usernameField;

  public CreateUserDialog() {
    setTitle("Sign Up");
    setHeaderText("Please enter your credentials.");

    ButtonType passwordButtonType = new ButtonType("Next", ButtonData.OK_DONE);
    getDialogPane().getButtonTypes().addAll(passwordButtonType, ButtonType.CANCEL);

    usernameField = new TextField();
    usernameField.setPromptText("Username");
    passwordField = new PasswordField();
    passwordField.setPromptText("Password");

    VBox vBox = new VBox();
    HBox hBoxUserName = new HBox();
    hBoxUserName.getChildren().add(new Label("Username:"));
    hBoxUserName.getChildren().add(usernameField);
    vBox.getChildren().add(hBoxUserName);
    HBox hBoxPassword = new HBox();
    hBoxPassword.getChildren().addAll(new Label("Password:"), passwordField);
    vBox.getChildren().add(hBoxPassword);
    vBox.setPadding(new Insets(20));
    HBox.setHgrow(passwordField, Priority.ALWAYS);
    HBox.setHgrow(usernameField, Priority.ALWAYS);

    getDialogPane().setContent(vBox);
    getDialogPane().toFront();

    setResultConverter(dialogButton -> {
      if (dialogButton == passwordButtonType) {
        return new Credentials(usernameField.getText(), passwordField.getText());
      }
      return null;
    });
  }

  public PasswordField getPasswordField() {
    return passwordField;
  }
}
