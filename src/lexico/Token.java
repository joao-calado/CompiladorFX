package lexico;

/**
 *
 * @author joao calado
 */
public class Token {

    public static final int TK_IDENTIFIER = 0;
    public static final int TK_NUMBER = 1;
    public static final int TK_OPERATOR = 2;
    public static final int TK_PONCTUATION = 3;
    public static final int TK_ASSIGN = 4;
    
    public static final int TK_INIP = 5;
    public static final int TK_FIMP = 6;
    
    public static final int TK_SE = 7;
    public static final int TK_ENQUANTO = 8;
    
    public static final int TK_OPREL = 9;

    public static final String TK_TEXT[] = {
        "IDENTIFIER", "NUMBER", "OPERATOR", "PONCTUACTION", "ASSIGN",
        "INICIO-PROGRAMA", "FIM-PROGRAMA", "SE", "ENQUANTO", "OPERADOR-RELACIONAL"
    };

    private int tipo;
    private String texto;
    private int line;
    private int column;

    public Token() {
    }

    public Token(int tipo, String texto) {
        this.tipo = tipo;
        this.texto = texto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Token{" + "tipo: " + Token.TK_TEXT[tipo] + ", texto: " + texto + '}';
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
