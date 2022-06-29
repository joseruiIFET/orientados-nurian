
package com.tcc.nurian.tccnurian;

import com.tcc.nurian.tccnurian.classes.CadeiaMarkov;
import com.tcc.nurian.tccnurian.classes.Projecao;
import java.util.List;

/**
 *
 * @author jose
 */
public class TccNurian {

    public static void main(String[] args) {
       // TODO code application logic here
        CadeiaMarkov mkc = new CadeiaMarkov();        
        
        int passos = 11;                                        
        List<Double> vetorDistribuicaoMi = mkc.getDistribuicaoAcc(passos);       
        
        //geracao dos dados
        Projecao projecao = new Projecao();
        List<Double> lst = projecao.criarListaPrecoSintetico(vetorDistribuicaoMi, mkc.getStates(), mkc.getConjuntoDados().getDesvPad(), mkc.getConjuntoDados().getQtnTotal());        
        
        System.out.println("Lista Sintetica");
        imprime(lst);
        somatorio(lst);        
    }    
    
    public static void imprime(List<Double> lst){
        System.out.println("[");
        for(Double d: lst)
            System.out.print(String.format("%.2f", d)+", ");
        System.out.println("]");
    }
    
    public static void somatorio(List<Double> lst){
        double soma = 0;
        for(Double d: lst){
            soma += d;            
        }        
        System.out.println("Somatorio = "+ soma);
    }
}
