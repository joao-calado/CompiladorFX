package sintatico;

import static compiladorfx.HomeCompFXController.txtJScannerAux;
import static compiladorfx.HomeCompFXController.txtErrorAux;
import lexico.JScanner;
import lexico.Token;

/**
 *
 * @author joao calado
 */
public class JParser {

    private JScanner scanner; // analisador léxico
    private Token token; // token atual

    /*
    O Jparser recebe o analisador léxiico como parâmetro
    no contrutor, por a cada procedimento terá de chama-lo
     */
    public boolean verificaCondicao() {
        token = scanner.nextToken();
        if (token.getTipo() == Token.TK_IDENTIFIER || token.getTipo() == Token.TK_INTEIRO) {
            return true;
        } else {
            return false;
        }
    }
    
    public JParser(JScanner scanner) {
        this.scanner = scanner;
    }

    public void E() {
        I();
        T();
        El();
    }

    public void El() {
        token = scanner.nextToken();
        if (token != null) {
            OP();
            T();
            El();
        }
    }

    public void I() {

        token = scanner.nextToken();

        if (token != null) {

            if (token.getTipo() != Token.TK_INIP) {
                txtErrorAux += "Erro Sintático | INICIO-PROGRAMA Esperado!, encontrou: "
                        + Token.TK_TEXT[token.getTipo()]
                        + " (" + token.getTexto() + ")"
                        + " na LINHA " + token.getLine()
                        + " e COLUNA " + token.getColumn() + "\n\n";
            }
        }
    }

    public void F(Token token) {
        if (token != null) {

            if (token.getTipo() != Token.TK_FIMP) {
                txtErrorAux += "Erro Sintático | FIM-PROGRAMA Esperado!, encontrou: "
                        + Token.TK_TEXT[token.getTipo()]
                        + " (" + token.getTexto() + ")"
                        + " na LINHA " + token.getLine()
                        + " e COLUNA " + token.getColumn() + "\n\n";
            }
        }
    }
    
    public void SE () {
        System.out.println("\nSE ENCONTRADOOOOOOOOOOOOOOOOOOOO\n");
        while (token.getTipo() != Token.TK_FIM_BLOCO) {
            token = scanner.nextToken();
            if (token.getTipo() != Token.TK_INI_CONDICAO) {
                txtErrorAux += "Erro Sintático | INICIO-BLOCO esperado!, mas encontrou: "
                        + token.TK_TEXT[token.getTipo()]
                        + " (" + token.getTexto() + ")"
                        + " na LINHA " + token.getLine()
                        + " e COLUNA " + token.getColumn() + "\n\n";
            } else {
                verificaCondicao();
            }
        }
    }

    public void T() {

        if (token != null) {
            System.out.println(token.toString());
            txtJScannerAux = txtJScannerAux + token.toString() + "\n";
        }

        Token taux;
        taux = token;
        token = scanner.nextToken();

        if (token != null) {
            System.out.println(token.toString());
            txtJScannerAux = txtJScannerAux + token.toString() + "\n";

            if (token.getTipo() == Token.TK_SE) {
                SE();
            }
            else if (token.getTipo() != Token.TK_IDENTIFIER
                    && token.getTipo() != Token.TK_NUMBER
                    && token.getTipo() != Token.TK_FIMP
                    && token.getTipo() != Token.TK_SE
                    && token.getTipo() != Token.TK_ENQUANTO
                    && token.getTipo() != Token.TK_INTEIRO
                    && token.getTipo() != Token.TK_CARACTERE) {
                txtErrorAux += "Erro Sintático | IDENTIFIER ou NUMBER Esperado!, encontrou: "
                        + Token.TK_TEXT[token.getTipo()]
                        + " (" + token.getTexto() + ")"
                        + " na LINHA " + token.getLine()
                        + " e COLUNA " + token.getColumn() + "\n\n";
            }
        } else {
            F(taux);
        }

    }

    public void OP() {
        if (token.getTipo() != Token.TK_OPERATOR
                && token.getTipo() != Token.TK_ASSIGN
                && token.getTipo() != Token.TK_PONCTUATION) {
            txtErrorAux += "Erro Sintático | OPERATOR Esperado, mas encontrou: "
                    + Token.TK_TEXT[token.getTipo()]
                    + " (" + token.getTexto() + ")"
                    + " na LINHA " + token.getLine()
                    + " e COLUNA " + token.getColumn() + "\n\n";
        }
    }

}
