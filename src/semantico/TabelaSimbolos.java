package semantico;

import static compiladorfx.HomeCompFXController.listaToken;
import static compiladorfx.HomeCompFXController.txtErrorAux;
import java.util.List;
import lexico.Token;

/**
 *
 * @author calado
 */
public class TabelaSimbolos {

    private Token tokenTabela;
    private String cadeia;
    private String tipo;
    private String valor;

    public TabelaSimbolos() {
    }

    public TabelaSimbolos(Token tokenTabela, String cadeia, String tipo, String valor) {
        this.tokenTabela = tokenTabela;
        this.cadeia = cadeia;
        this.tipo = tipo;
        this.valor = valor;
    }

    public Token getTokenTabela() {
        return tokenTabela;
    }

    public void setTokenTabela(Token tokenTabela) {
        this.tokenTabela = tokenTabela;
    }

    public String getCadeia() {
        return cadeia;
    }

    public void setCadeia(String cadeia) {
        this.cadeia = cadeia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "| Cadeia=" + cadeia + " | Tipo=" + tipo + " | Valor=" + valor + "\n"
                + "___________________________________________________________________\n";
    }

    public void execute() {
        TabelaSimbolos simb = new TabelaSimbolos();
        Token tk;

        for (int i = 0; i <= listaToken.size(); i++) {
            tk = listaToken.get(i);
            // declaração de caractere
            if (tk.getTipo() == Token.TK_CARACTERE) {
                tk = listaToken.get(i + 1);
                simb.setCadeia(tk.getTexto());
                simb.setTipo(Token.TK_TEXT[tk.getTipo()]);
                // caractere foi inicializado?
                if (listaToken.get(i + 2).getTipo() == Token.TK_ASSIGN) {
                    if(listaToken.get(i + 3).getTipo() == Token.TK_IDENTIFIER) {
                        
                    }
                } else {
                    txtErrorAux += "Erro Semântico | "
                            + simb.getTipo() + " "
                            + simb.getCadeia() + " "
                            + "não inicializado!\n\n";
                }
            }
        }
    }

}
