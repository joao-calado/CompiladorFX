package compiladorfx;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import lexico.JScanner;
import lexico.Token;
import semantico.Semantico;
import semantico.TabelaSimbolos;
import sintatico.JParser;

/**
 * FXML Controller class
 *
 * @author joao calado
 */
public class HomeCompFXController implements Initializable {

    private JFileChooser file;
    private int contLinha;
    public static String txtJScannerAux;
    public static String txtErrorAux;
    public static String txtTabelaSimbolosAux;
    public static String txtNumLinhaAux;
    public static List<Token> listaToken = new ArrayList<>();

    @FXML
    private Button btMinimize;
    @FXML
    private JFXTextArea txtCode;
    @FXML
    private Button btExecutar;
    @FXML
    private Button btOpen;
    @FXML
    private Button btSave;
    @FXML
    private TextField txtEndereco;
    @FXML
    private JFXTextArea txtErro;
    @FXML
    private JFXTextArea txtJScanner;
    @FXML
    private VBox vbNotSaved;
    @FXML
    private JFXTextArea txtNumLinha;
    @FXML
    private HBox hbSaida;
    @FXML
    private JFXTextArea txtTabelaSimbolos;

    /**
     * Initializes the controller class.
     */
    private void tooltipInitialize() {
        btExecutar.setTooltip(new Tooltip("Executar"));
        btOpen.setTooltip(new Tooltip("Abrir"));
        btSave.setTooltip(new Tooltip("Salvar"));
    }

    private void setLinhas() {
        int cont = txtCode.getText().split("\n").length;
        txtNumLinhaAux = "";
        for (int i = 0; i < cont; i++) {
            txtNumLinhaAux += i + 1 + "\n";
        }
        txtNumLinha.setText(txtNumLinhaAux);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        file = null;
        contLinha = 1;
        txtJScannerAux = "";
        txtErrorAux = "";
        txtNumLinhaAux = "";
        tooltipInitialize();
        
        //txtEndereco.setText("C:\\Users\\calado\\Documents\\input_SE.txt");
    }

    @FXML
    private void clkMinimize(ActionEvent event) {
        ((Stage) (btMinimize.getParent().getScene().getWindow())).setIconified(true);
    }

    @FXML
    private void clkClose(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void dgtCode(KeyEvent event) {
        System.out.println(txtCode.getText());
        vbNotSaved.setStyle("-fx-background-color: #fdbb40; -fx-background-radius: 5px;");

        setLinhas();
//        if (event.getCharacter().toString().equals("\b") || event.getCharacter().toString().equals("\n")) {
//            setLinhas();
//        }

    }

    @FXML
    private void clkExecutar(ActionEvent event) {

        txtErrorAux = "";
        txtJScannerAux = "";
        txtTabelaSimbolosAux = "";
        txtErro.setText("");
        txtJScanner.setText("");
        txtTabelaSimbolos.setText("");
        Label lb = new Label();
        JFXSnackbar snackbar = new JFXSnackbar(hbSaida); 
        
        if (txtEndereco.getLength() > 0) {
            JScanner al = new JScanner(txtEndereco.getText());
            JParser pa = new JParser(al);
            Semantico sem = new Semantico();
            
            pa.E();
            sem.execute();

            txtJScanner.setText(txtJScannerAux);
            txtErro.setText(txtErrorAux);
            txtTabelaSimbolos.setText(txtTabelaSimbolosAux);

            if (txtErrorAux.length() == 0) {
                lb = new Label("Sucesso na Compilação!");
                lb.setStyle("-fx-text-fill: white;"
                        + "-fx-background-color: #008040;"
                        + "-fx-font-size: 12px;"
                        + "-fx-padding: 10px;"
                        + "-fx-background-radius: 5px");
            } else {
                lb = new Label("Algumas exceções encontradas!");
                lb.setStyle("-fx-text-fill: white;"
                        + "-fx-background-color: #cc0000;"
                        + "-fx-font-size: 12px;"
                        + "-fx-padding: 10px;"
                        + "-fx-background-radius: 5px");
            }

        } else if (txtCode.getText().length() > 0) {
            lb = new Label("Salve antes de executar");
            lb.setStyle("-fx-text-fill: white;"
                    + "-fx-background-color: #ee9600;"
                    + "-fx-font-size: 12px;"
                    + "-fx-padding: 10px;"
                    + "-fx-background-radius: 5px");
        } else {
            lb = new Label("Nenhum arquivo encontrado");
            lb.setStyle("-fx-text-fill: white;"
                    + "-fx-background-color: #cc0000;"
                    + "-fx-font-size: 12px;"
                    + "-fx-padding: 10px;"
                    + "-fx-background-radius: 5px;");
        }
        final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(lb, Duration.seconds(2.22), null);
        snackbar.enqueue(snackbarEvent);

    }

    @FXML
    private void clkOpen(ActionEvent event) {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos texto", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        file = chooser;

        String txtConteudo;
        String filename;

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            txtEndereco.setText(chooser.getSelectedFile().getAbsolutePath());

            try {
                filename = chooser.getSelectedFile().getAbsolutePath();
                txtConteudo = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
                txtCode.setText(txtConteudo);

                setLinhas();
            } catch (IOException e) {
                System.err.printf("Erro na abertura do arquivo: %s \n", e.getMessage());
            }

            System.out.println("You chose to open this file: "
                    + chooser.getSelectedFile().getAbsolutePath());
        }

    }

    @FXML
    private void clkSave(ActionEvent event) {

        int returnVal = -1;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos texto", "txt");
        chooser.setFileFilter(filter);
        chooser.setApproveButtonText("Salvar");
        txtJScannerAux = "";
        txtErrorAux = "";

        if (file != null) {
            chooser.setCurrentDirectory(file.getCurrentDirectory());
        } else {
            returnVal = chooser.showSaveDialog(null);
        }

        if (file != null || returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fileWriter;

                if (file != null) {
                    fileWriter = new FileWriter(file.getSelectedFile().getAbsolutePath());
                    txtEndereco.setText(file.getSelectedFile().getAbsolutePath());
                } else {
                    fileWriter = new FileWriter(chooser.getSelectedFile().getAbsolutePath());
                    txtEndereco.setText(chooser.getSelectedFile().getAbsolutePath());
                }

                fileWriter.write(txtCode.getText());
                fileWriter.close();

                vbNotSaved.setStyle("-fx-background-color: transparent; -fx-background-radius: 5px;");
                JFXSnackbar snackbar = new JFXSnackbar(hbSaida);
                Label lb = new Label("Salvo com Sucesso");
                lb.setStyle("-fx-text-fill: white;"
                        + "-fx-background-color: #008040;"
                        + "-fx-font-size: 12px;"
                        + "-fx-padding: 10px;"
                        + "-fx-background-radius: 5px");
                final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(lb, Duration.seconds(2.22), null);
                snackbar.enqueue(snackbarEvent);

            } catch (IOException ex) {
                System.out.println("ERRO: " + ex);
            }
        }
    }

}
