package application;


import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class proba extends Application {
  @Override/*  ww  w  . ja  v a  2 s  . c  om*/
  public void start(Stage primaryStage) {

    Rectangle rect = new Rectangle(550.0, 296.0, 60.5, 27);
    rect.setFill(Color.WHITE);
    rect.setStroke(Color.BLACK);

    Pane pane = new Pane();
    pane.getChildren().add(rect);

    pane.setOnMouseMoved(e -> {
      pane.getChildren().remove(1, pane.getChildren().size());
      double x = e.getX();
      double y = e.getY();
      StringBuilder sb = new StringBuilder();
      if (rect.contains(x, y)) {
        sb.append("Mouse point is inside the rectangle");
      } else {
        sb.append("Mouse point is outside the rectangle");
      }
      Text text = new Text(x, y, sb.toString());
      pane.getChildren().add(text);
    });

    Scene scene = new Scene(pane);
    primaryStage.setTitle("java2s.com");
    primaryStage.setScene(scene);
    primaryStage.show();

    pane.requestFocus();
  }

  public static void main(String[] args) {
    launch(args);
  }
}