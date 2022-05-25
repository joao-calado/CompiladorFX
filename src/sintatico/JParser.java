package sintatico;

import static compiladorfx.HomeCompFXController.txtJScannerAux;
import static compiladorfx.HomeCompFXController.txtErrorAux;
import static compiladorfx.HomeCompFXController.listaToken;
import lexico.JScanner;
import lexico.Token;

/**
 *
 * @author joao calado
 */
public class JParser {

    private JScanner scanner; // analisador léxico
    private Token token; // token atual
    Token taux; // token anterior

    /*
    O Jparser recebe o analisador léxiico como parâmetro
    no contrutor, por a cada procedimento terá de chama-lo
     */
    public void verificaCondicao() {
        /*
        IDENTIFIER || NUMBER -> OPERADOR-RELACIONAL -> NUMBER || IDENTIFIER
         */
        int flag_cond = 0;

        if (token.getTipo() != Token.TK_INI_CONDICAO) {
            txtErrorAux += "Erro Sintático | INICIO-BLOCO esperado!, mas encontrou: "
                    + token.TK_TEXT[token.getTipo()]
                    + " (" + token.getTexto() + ")"
                    + " na LINHA " + token.getLine()
                    + " e COLUNA " + token.getColumn() + "\n\n";
        } else {
            while (token.getTipo() != Token.TK_FIM_CONDICAO && flag_cond != 3) {

                taux = token;
                token = scanner.nextToken();
                if (token != null) {
                    listaToken.add(token);
                }
                System.out.println(token.toString());
                txtJScannerAux = txtJScannerAux + token.toString() + "\n";

                if (flag_cond == 2 && (token.getTipo() == Token.TK_IDENTIFIER || token.getTipo() == Token.TK_NUMBER)) {
                    flag_cond = 3;
                } else if (flag_cond == 2) {
                    txtErrorAux += "Erro Sintático | IDENTIFIER ou NUMBER Esperado!, encontrou: "
                            + Token.TK_TEXT[token.getTipo()]
                            + " (" + token.getTexto() + ")"
                            + " na LINHA " + token.getLine()
                            + " e COLUNA " + token.getColumn() + "\n\n";
                }

                if (flag_cond == 1 && token.getTipo() == Token.TK_OPREL) {
                    flag_cond = 2;
                } else if (flag_cond == 1) {
                    txtErrorAux += "Erro Sintático | OPERADOR-RELACIONAL Esperado!, encontrou: "
                            + Token.TK_TEXT[token.getTipo()]
                            + " (" + token.getTexto() + ")"
                            + " na LINHA " + token.getLine()
                            + " e COLUNA " + token.getColumn() + "\n\n";
                }

                if (flag_cond == 0 && (token.getTipo() == Token.TK_IDENTIFIER || token.getTipo() == Token.TK_NUMBER)) {
                    flag_cond = 1;
                } else if (flag_cond == 0) {
                    txtErrorAux += "Erro Sintático | IDENTIFIER ou NUMBER Esperado!, encontrou: "
                            + Token.TK_TEXT[token.getTipo()]
                            + " (" + token.getTexto() + ")"
                            + " na LINHA " + token.getLine()
                            + " e COLUNA " + token.getColumn() + "\n\n";
                }
            }

            if (flag_cond == 3) {
                taux = token;
                token = scanner.nextToken();
                if (token != null) {
                    listaToken.add(token);
                }
                txtJScannerAux = txtJScannerAux + token.toString() + "\n";

                if (token.getTipo() != Token.TK_FIM_CONDICAO) {
                    txtErrorAux += "Erro Sintático | FIM-CONDICAO Esperado!, encontrou: "
                            + Token.TK_TEXT[token.getTipo()]
                            + " (" + token.getTexto() + ")"
                            + " na LINHA " + token.getLine()
                            + " e COLUNA " + token.getColumn() + "\n\n";
                }
            }
        }
    }

    public void verificaBloco() {
        /*
        IDENTIFIER || NUMBER -> ASSIGN -> NUMBER || IDENTIFIER
        IDENTIFIER || NUMBER -> OPERATOR -> NUMBER || IDENTIFIER
         */
        taux = token;
        token = scanner.nextToken();
        if (token != null) {
            listaToken.add(token);
        }
        System.out.println(token.toString());
        txtJScannerAux = txtJScannerAux + token.toString() + "\n";

        int flag_bloco = 0;
        if (token.getTipo() != Token.TK_INI_BLOCO) {
            txtErrorAux += "Erro Sintático | INICIO-BLOCO Esperado!, encontrou: "
                    + Token.TK_TEXT[token.getTipo()]
                    + " (" + token.getTexto() + ")"
                    + " na LINHA " + token.getLine()
                    + " e COLUNA " + token.getColumn() + "\n\n";
        } else {
            while (token.getTipo() != Token.TK_FIM_BLOCO && token.getTipo() != Token.TK_FIMP) {
                taux = token;
                token = scanner.nextToken();
                if (token != null) {
                    listaToken.add(token);
                }
                System.out.println(token.toString());
                txtJScannerAux = txtJScannerAux + token.toString() + "\n";

                if (token.getTipo() != Token.TK_FIM_BLOCO) {
                    if (flag_bloco == 3) {
                        flag_bloco = 0;
                    }

                    if (flag_bloco == 2
                            && (token.getTipo() == Token.TK_IDENTIFIER || token.getTipo() == Token.TK_NUMBER)) {
                        flag_bloco = 3;
                    } else if (flag_bloco == 2) {
                        txtErrorAux += "Erro Sintático | IDENTIFIER ou NUMBER Esperado!, encontrou: "
                                + Token.TK_TEXT[token.getTipo()]
                                + " (" + token.getTexto() + ")"
                                + " na LINHA " + token.getLine()
                                + " e COLUNA " + token.getColumn() + "\n\n";
                    }

                    if (flag_bloco == 1
                            && (token.getTipo() == Token.TK_ASSIGN || token.getTipo() == Token.TK_OPERATOR)) {
                        flag_bloco = 2;
                    } else if (flag_bloco == 1) {
                        txtErrorAux += "Erro Sintático | ASSIGN ou OPERATOR Esperado!, encontrou: "
                                + Token.TK_TEXT[token.getTipo()]
                                + " (" + token.getTexto() + ")"
                                + " na LINHA " + token.getLine()
                                + " e COLUNA " + token.getColumn() + "\n\n";
                    }

                    if (flag_bloco == 0
                            && (token.getTipo() == Token.TK_IDENTIFIER || token.getTipo() == Token.TK_NUMBER)) {
                        flag_bloco = 1;
                    } else if (flag_bloco == 0) {
                        if (token.getTipo() == Token.TK_FIMP) {
                            txtErrorAux += "Erro Sintático | FIM-BLOCO Esperado!, encontrou: "
                                    + Token.TK_TEXT[token.getTipo()]
                                    + " (" + token.getTexto() + ")"
                                    + " na LINHA " + token.getLine()
                                    + " e COLUNA " + token.getColumn() + "\n\n";
                        } else {
                            txtErrorAux += "Erro Sintático | IDENTIFIER ou NUMBER Esperado!, encontrou: "
                                    + Token.TK_TEXT[token.getTipo()]
                                    + " (" + token.getTexto() + ")"
                                    + " na LINHA " + token.getLine()
                                    + " e COLUNA " + token.getColumn() + "\n\n";
                        }
                    }
                }
            }
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
        taux = token;
        token = scanner.nextToken();
        if (token != null) {
            listaToken.add(token);
        }
        if (token != null) {
            OP();
            T();
            El();
        }
    }

    public void I() {
        taux = token;
        token = scanner.nextToken();
        if (token != null) {
            listaToken.add(token);
        }

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

    public void SE() {
        taux = token;
        token = scanner.nextToken();
        if (token != null) {
            listaToken.add(token);
        }
        System.out.println(token.toString());
        txtJScannerAux = txtJScannerAux + token.toString() + "\n";
        verificaCondicao();
        verificaBloco();
    }

    public void ENQUANTO() {
        taux = token;
        token = scanner.nextToken();
        if (token != null) {
            listaToken.add(token);
        }
        System.out.println(token.toString());
        txtJScannerAux = txtJScannerAux + token.toString() + "\n";
        verificaCondicao();
        verificaBloco();
    }

    public void T() {

        if (token != null) {
            System.out.println(token.toString());
            txtJScannerAux = txtJScannerAux + token.toString() + "\n";
        }

        taux = token;
        token = scanner.nextToken();
        if (token != null) {
            listaToken.add(token);
        }

        if (token != null) {
            System.out.println(token.toString());
            txtJScannerAux = txtJScannerAux + token.toString() + "\n";

            if (token.getTipo() == Token.TK_SE) {
                SE();
            } else if (token.getTipo() == Token.TK_ENQUANTO) {
                ENQUANTO();
            } else if (token.getTipo() != Token.TK_IDENTIFIER
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
                && token.getTipo() != Token.TK_PONCTUATION
                && token.getTipo() != Token.TK_FIMP
                && taux.getTipo() != Token.TK_INTEIRO
                && taux.getTipo() != Token.TK_CARACTERE) {
            txtErrorAux += "Erro Sintático | OPERATOR Esperado, mas encontrou: "
                    + Token.TK_TEXT[token.getTipo()]
                    + " (" + token.getTexto() + ")"
                    + " na LINHA " + token.getLine()
                    + " e COLUNA " + token.getColumn() + "\n\n";
        }
    }

}
