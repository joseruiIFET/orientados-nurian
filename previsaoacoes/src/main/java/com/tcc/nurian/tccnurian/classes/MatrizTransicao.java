/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.nurian.tccnurian.classes;

import java.util.List;

/**
 *
 * @author nuria
 */
public class MatrizTransicao {

    private double[][] matriz;

    public MatrizTransicao(int qtnEstados) {
        this.matriz = new double[qtnEstados][qtnEstados];
        for (int i = 0; i < qtnEstados; i++) {
            for (int j = 0; i < qtnEstados; i++) {
                this.matriz[i][j] = 0;
            }

        }
    }

    public void inicializarMatriz(List<Estado> estados) {
        for (int i = 0; i < estados.size(); i++) {
            for (int j = 0; j < estados.size(); j++) {
                double transicao = estados.get(i).getTransicao()[j].getQtn() / (1.0f * estados.get(i).getQtnItens());
                this.matriz[i][j] = transicao;
            }
        }
    }

    /**
     * @return the matriz
     */
    public double[][] getMatriz() {
        return matriz;
    }

    /**
     * @param matriz the matriz to set
     */
    public void setMatriz(double[][] matriz) {
        this.matriz = matriz;
    }

    /**
     * Simplemente imprime a matriz
     * @param matriz 
     */
    public void imprime(double matriz[][]) {
        System.out.println("Matriz de transição");
        for (int i = 0; i < matriz.length; i++) {
            System.out.print("\n");
            for (int j = 0; j < matriz.length; j++) {
                System.out.print(Math.round(matriz[i][j] * 1000.0) / 1000.0 + "\t");
            }
        }
        System.out.print("\n");

    }

    /**
     * Eleva uma matriz a um determinado expoente
     *
     * @param expoente: é o numero de passos
     * @return
     */
    public double[][] pontencia(int expoente) {
        double newMatriz[][];
        newMatriz = new double[this.matriz.length][this.matriz.length];
        for (int i = 0; i < this.matriz.length; i++) {
            for (int j = 0; j < this.matriz.length; j++) {
                newMatriz[i][j] = this.matriz[i][j];
            }
        }

        for (int k = 1; k < expoente; k++) {
            double matrizResultado[][] = produto(newMatriz);
            newMatriz = matrizResultado;
        }

        // System.out.println("\n\n________________________________________________");
        // System.out.println("Matriz " + passos + " passos:");
        // this.printMatriz(newMatriz);
        return newMatriz;

    }

    public double[][] produto(double matriz[][]) {
        double matrizResultado[][];
        matrizResultado = new double[this.matriz.length][this.matriz.length];
        double valorPosicao = 0;

        for (int linha = 0; linha < this.matriz.length; linha++) {
            for (int coluna = 0; coluna < this.matriz.length; coluna++) {
                for (int j = 0; j < this.matriz.length; j++) {
                    valorPosicao += matriz[linha][j] * this.matriz[j][coluna];
                }
                matrizResultado[linha][coluna] = valorPosicao;
                valorPosicao = 0;
            }
        }

        return matrizResultado;
    }
}
