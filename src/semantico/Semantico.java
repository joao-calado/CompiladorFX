package semantico;

import static compiladorfx.HomeCompFXController.listaToken;
import static compiladorfx.HomeCompFXController.txtErrorAux;
import static compiladorfx.HomeCompFXController.txtTabelaSimbolosAux;
import java.util.ArrayList;
import java.util.List;
import lexico.Token;

/**
 *
 * @author calado
 */
public class Semantico {

    private List<TabelaSimbolos> listaSimb = new ArrayList<>();

    public void alterar(String id, String valor) {
        for (int i = 0; i < listaSimb.size(); i++) {
            if (id.equals(listaSimb.get(i).getCadeia())) {
                listaSimb.get(i).setValor(valor);
                txtTabelaSimbolosAux = txtTabelaSimbolosAux + listaSimb.get(i).toString();
            }
        }
    }
    
    public TabelaSimbolos busca(String id) {
        for (int i = 0; i < listaSimb.size(); i++) {
            if (id.equals(listaSimb.get(i).getCadeia())) {
                return listaSimb.get(i);
            }
        }
        return null;
    }

    public void execute() {
        TabelaSimbolos simb = new TabelaSimbolos();
        TabelaSimbolos simbAux = new TabelaSimbolos();
        TabelaSimbolos simbVar = new TabelaSimbolos();
        Token tk;

        for (int i = 0; i < listaToken.size(); i++) {
            simb = new TabelaSimbolos();
            simbAux = new TabelaSimbolos();
            simbVar = new TabelaSimbolos();
            tk = listaToken.get(i);
            // declaração de caractere (não é possível declarar e inicializar)
            if (tk.getTipo() == Token.TK_CARACTERE) {
                simb.setCadeia(listaToken.get(i + 1).getTexto());
                simb.setTipo("CARACTERE");
                simb.setValor("null");
                listaSimb.add(simb);
                txtTabelaSimbolosAux = txtTabelaSimbolosAux + simb.toString();
            } else if (tk.getTipo() == Token.TK_INTEIRO) {
                simb.setCadeia(listaToken.get(i + 1).getTexto());
                simb.setTipo("INTEIRO");
                simb.setValor("null");
                listaSimb.add(simb);
                txtTabelaSimbolosAux = txtTabelaSimbolosAux + simb.toString();
            }

            if (tk.getTipo() == Token.TK_IDENTIFIER
                    && listaToken.get(i - 1).getTipo() != Token.TK_CARACTERE
                    && listaToken.get(i - 1).getTipo() != Token.TK_INTEIRO) {
                simbAux = busca(tk.getTexto());
                // se esta cadeia nao se trata de uma variável
                if (simbAux == null) {
                    if (listaToken.get(i - 1).getTipo() != Token.TK_ASSIGN) {
                        txtErrorAux += "Erro Semântico | "
                                + tk.getTexto()
                                + " não declarado\n\n";
                    } else {
                        alterar(listaToken.get(i - 2).getTexto(), tk.getTexto());
                    }
                } //else {
//                    if (listaToken.get(i + 1).getTipo() == Token.TK_ASSIGN) {
//                        simbVar = busca(listaToken.get(i + 2).getTexto());
//                        // se atr é uma var já declarada
//                        if (simbVar == null) {
//                            if (simbAux.getTipo().equals("CARACTERE")) {
//                                alterar(listaToken.get(i + 2).getTexto(), listaToken.get(i + 2).getTexto());
//                            } else if (simbAux.getTipo().equals("INTEIRO")) {
//
//                            }
//                        } else {
//                            if (simbAux.getTipo().equals("CARACTERE")) {
//                                alterar(simbVar.getCadeia(), listaToken.get(i + 2).getTexto());
//                            } else if (simbAux.getTipo().equals("INTEIRO")) {
//
//                            }
//                        }
//                    } else {
//                        txtErrorAux += "Erro Semântico | "
//                                + tk.getTexto()
//                                + " não é uma declaração\n\n";
//                    }
//                }
            }
        }
    }

    public Semantico() {

    }
}
