/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.nurian.tccnurian.classes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nuria
 * 
 * Distribuição é o vetor Mi
 */
public class Distribuicao {

    private double[] distribuicaoM0;

    public Distribuicao(int tamanho) {
        this.distribuicaoM0 = new double[tamanho];
        for (int i = 0; i < tamanho; i++) {
            this.distribuicaoM0[i] = 0.0;
        }
    }

    /**
     * @return the distribuicaoM0
     */
    public double[] getDistribuicaoM0() {
        return distribuicaoM0;
    }

    /**
     * @param estados
     * @param qtnTotal
     */
    public void criarDistribuicaoM0(List<Estado> estados, int qtnTotal) {
        for (int i = 0; i < estados.size(); i++) {
            this.distribuicaoM0[i] = this.distribuicaoPorEstado(estados.get(i).getQtnItens(), qtnTotal);
        }
    }

    private double distribuicaoPorEstado(int qtnIntervalo, int qtnTotal) {
        return qtnIntervalo / (1.0f * qtnTotal);
    }

    
    /**
     * Esta função basicamente multiplica um vetor x matriz
     * Especificamente ele multiplica o Mi * Matriz^Passos
     * @param matriz: é a matrix_i
     * @return 
     */
    public double[] getDistribuicaoMi(double matriz[][]) {
        double distribuicaoResultado[];
        distribuicaoResultado = new double[this.distribuicaoM0.length];
        double valorPosicao = 0.00;

        for (int linha = 0; linha < this.distribuicaoM0.length; linha++) {
            for (int coluna = 0; coluna < this.distribuicaoM0.length; coluna++) {
                valorPosicao += this.distribuicaoM0[coluna] * matriz[coluna][linha];
            }
            distribuicaoResultado[linha] = valorPosicao;
            valorPosicao = 0.00;
        }

        return distribuicaoResultado;
    }
    
    /**
     * Esta função pega o vetor Mi qualquer e 
     * faz a acumulaçaõ dele 
     * @return 
     */
    public List<Double> getDistribuicaoAcc() {
        List<Double> distribuicaoAcc = new ArrayList<>();
        double soma = 0;
        System.out.println("M_acc[");
        for (int i = 0; i < distribuicaoM0.length; i++) {
            soma += distribuicaoM0[i];
            distribuicaoAcc.add(soma);
            System.out.println(soma + ", ");            
        }

        System.out.println("]");
        return distribuicaoAcc;
    }
    
        
    public void imprime(double distribuicao[]) {
        System.out.print("\n \n");
        System.out.print("Vetor de distribuicao: [");
        for (int i = 0; i < distribuicao.length; i++) {
            System.out.print(distribuicao[i]+", ");
            //System.out.print( Math.round(distribuicao[i] * 100.0) / 100.0);
        }
        System.out.print("]\n \n");
    }
}
