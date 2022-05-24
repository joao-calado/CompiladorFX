package lexico;

import static compiladorfx.HomeCompFXController.txtErrorAux;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author joao calado
 */
public class JScanner {

    private char[] conteudo;
    private int estado;
    private int pos;
    private int linha;
    private int coluna;

    public JScanner(String filename) {
        try {
            linha = 1;
            coluna = 0;
            String txtConteudo;
            txtConteudo = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
            conteudo = txtConteudo.toCharArray();
            pos = 0;
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s \n", e.getMessage());
        }
    }

    public Token nextToken() {
        char currentChar;
        estado = 0;
        String termo = "";
        Token token;

        if (isEOF()) {
            return null;
        }
        while (true) {
            currentChar = NextChar();
            coluna++;
            switch (estado) {
                case 0:
                    if (isChar(currentChar)) {
                        termo += currentChar;
                        estado = 1;
                    } else if (isDigit(currentChar)) {
                        estado = 2;
                        termo += currentChar;
                    } else if (isSpace(currentChar)) {
                        estado = 0;
                    } else {
                        token = new Token();
                        termo += currentChar;
                        token.setTexto(termo);
                        token.setLine(linha);
                        token.setColumn(coluna - termo.length());
                        if (isOperator(currentChar)) {
                            token.setTipo(Token.TK_OPERATOR);
                            return token;
                        } else if (isAssign(currentChar)) {
                            token.setTipo(Token.TK_ASSIGN);
                            return token;
                        } else if (isPonctuation(currentChar)) {
                            token.setTipo(Token.TK_PONCTUATION);
                            return token;
                        } else if (isInicioCondicao(currentChar)) {
                            token.setTipo(Token.TK_INI_CONDICAO);
                            return token;
                        } else if (isFimCondicao(currentChar)) {
                            token.setTipo(Token.TK_FIM_CONDICAO);
                            return token;
                        } else if (isInicioBloco(currentChar)) {
                            token.setTipo(Token.TK_INI_BLOCO);
                            return token;
                        } else if (isFimBloco(currentChar)) {
                            token.setTipo(Token.TK_FIM_BLOCO);
                            return token;
                        } else if (isOperatorRel(termo)) {
                            token.setTipo(Token.TK_OPREL);
                            return token;
                        } else {
                            txtErrorAux += "Erro Léxico | SIMBOLO não reconhecido: [" + currentChar + "]\n"
                                    + "Linha: " + linha + "\n"
                                    + "Coluna: " + coluna + "\n\n";
                        }
                    }
                    break;
                case 1:
                    if (isChar(currentChar) || isDigit(currentChar)) {
                        estado = 1;
                        termo += currentChar;
                    } else if ( isSpace(currentChar) 
                                || isOperator(currentChar)
                                || isEOF(currentChar)
                                || isPonctuation(currentChar)
                                || isFimCondicao(currentChar)) {
                        if (!isEOF(currentChar)) {
                            Back();
                        }
                        token = new Token();
                        if (isInicioProg(termo)) {
                            token.setTipo(Token.TK_INIP);
                        } else if (isFimprog(termo)) {
                            token.setTipo(Token.TK_FIMP);
                        } else if (isSe(termo)) {
                            token.setTipo(Token.TK_SE);
                        } else if (isEnquanto(termo)) {
                            token.setTipo(Token.TK_ENQUANTO);
                        } else if (isOperatorRel(termo)) {
                            token.setTipo(Token.TK_OPREL);
                        } else if (isInteiro(termo)) {
                            token.setTipo(Token.TK_INTEIRO);
                        } else if (isCaractere(termo)) {
                            token.setTipo(Token.TK_CARACTERE);
                        }  else if (isFimCondicao(currentChar)) {
                            token.setTipo(Token.TK_IDENTIFIER);
                            estado = 3;
                        } else {
                            token.setTipo(Token.TK_IDENTIFIER);
                        }
                        token.setTexto(termo);
                        token.setLine(linha);
                        token.setColumn(coluna - termo.length());
                        return token;
                    } else {
                        txtErrorAux += "Erro Léxico | SIMBOLO não reconhecido: [" + currentChar + "]\n"
                                + "Linha: " + linha + "\n"
                                + "Coluna: " + coluna + "\n\n";
                    }
                    break;
                case 2:
                    if (isDigit(currentChar)) {
                        estado = 2;
                        termo += currentChar;
                    } else if (!isChar(currentChar) || isEOF(currentChar)) {
                        if (!isEOF(currentChar)) {
                            Back();
                        }
                        token = new Token();
                        token.setTipo(Token.TK_NUMBER);
                        token.setTexto(termo);
                        token.setLine(linha);
                        token.setColumn(coluna - termo.length());
                        return token;
                    } else {
                        txtErrorAux += "Erro Léxico | NUMERO não reconhecido: [" + currentChar + "]\n"
                                + "Linha: " + linha + "\n"
                                + "Coluna: " + coluna + "\n\n";
                    }
                    break;
                case 3:
                    if(isFimCondicao(currentChar)) {
                        token = new Token();
                        token.setTipo(Token.TK_FIM_CONDICAO);
                        token.setTexto(""+currentChar);
                        token.setLine(linha);
                        token.setColumn(coluna - termo.length());
                        estado = 0;
                        return token;
                    }
            }
        }
    }
    
    private boolean isInicioCondicao(char c) {
        return c == '(';
    }
    
    private boolean isFimCondicao(char c) {
        return c == ')';
    }
    
    private boolean isInicioBloco(char c) {
        return c == '{';
    }
    
    private boolean isFimBloco(char c) {
        return c == '}';
    }
    
    private boolean isInteiro (String termo) {
        return termo.equals("inteiro");
    }
    
    private boolean isCaractere (String termo) {
        return termo.equals("caractere");
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean isAssign(char c) {
        return c == '=';
    }

    private boolean isPonctuation(char c) {
        return c == ';';
    }

    private boolean isSpace(char c) {
        if (c == '\n' || c == '\r') {
            linha++;
            coluna = 0;
        }
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    private char NextChar() {
        if (isEOF()) {
            return '\0';
        }
        return conteudo[pos++];
    }

    private void Back() {
        pos--;
        coluna--;
    }

    private boolean isEOF() {
        return pos >= conteudo.length;
    }

    private boolean isEOF(char c) {
        return c == '\0';
    }

    private boolean isInicioProg(String termo) {
        return termo.equals("inicioprog");
    }

    private boolean isFimprog(String termo) {
        return termo.equals("fimprog");
    }

    private boolean isSe(String termo) {
        return termo.equals("se");
    }

    private boolean isEnquanto(String termo) {
        return termo.equals("enquanto");
    }

    private boolean isOperatorRel(String termo) {
        return termo.equals("<") || termo.equals(">")
                || termo.equals("<=")
                || termo.equals(">=")
                || termo.equals("==")
                || termo.equals("!=");
    }

}
