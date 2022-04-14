package compiladorfx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author joao calado
 */
public class CompiladorFX extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("HomeCompFX.fxml"));

        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root);

        Image imagem;
        imagem = new Image("imagens/icon.png");
        stage.getIcons().add(imagem);

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("CompiladorFX");
        stage.show();
    }

    public static void main(String[] args) {
        
//        try {
//            JScanner al = new JScanner("/home/joao/Documentos/input.txt");
//            Token token = null;
//            do {
//                token = al.nextToken();
//                if (token != null) {
//                    System.out.println(token.toString());
//                }
//            } while (token != null);
//        } 
//        catch (LexicalException le) {
//            System.out.println("Erro Lexico "+le.getMessage());
//        }
//        catch (Exception e) {
//            System.out.println("Erro Generico!!");
//        }
        

        launch(args);
        
        }

    }
