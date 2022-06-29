/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.nurian.tccnurian.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author nuria
 */
public class Projecao {

    private Random aleatorio;    

    public Projecao() {
        int semente = 3;
        this.aleatorio = new Random(semente);        
    }

    public List<Double> criarListaPrecoSintetico(List<Double> distribuicaoAccMi, List<Estado> estados, double desvPred, int qtnDados) {
        List<Double> lstSintetica = new ArrayList<>();
        
        for (int i = 0; i < qtnDados - 1; i++) {
            double aleatorioGerado = this.aleatorio.nextDouble();
            for (int j = 0; j < distribuicaoAccMi.size() - 1; j++) {
                if (aleatorioGerado < distribuicaoAccMi.get(j)) {
                    double fator = aleatorioGerado * desvPred;
                    double precoSintetico_i = fator + estados.get(j).getInicio();
                    lstSintetica.add(precoSintetico_i);
                    break;
                }
            }
        }
        return lstSintetica;
    }    
}
