/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.nurian.tccnurian.classes;

/**
 *
 * @author nuria
 */
public class Estado {
    private int nome;
    private double inicio;
    private double fim;
    private Transicao[] transicao;
    private int qtnItens;
    
    public Estado() {
        this.nome = 0;
        this.inicio = 0;
        this.fim = 0;
        this.qtnItens = 0;
    }

    /**
     * @return the nome
     */
    public int getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(int nome) {
        this.nome = nome;
    }

    /**
     * @return the inicio
     */
    public double getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(double inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fim
     */
    public double getFim() {
        return fim;
    }

    /**
     * @param fim the fim to set
     */
    public void setFim(double fim) {
        this.fim = fim;
    }

    /**
     * @return the qtnItens
     */
    public int getQtnItens() {
        return qtnItens;
    }

    /**
     * @param qtnItens the qtnItens to set
     */
    public void setQtnItens(int qtnItens) {
        this.qtnItens = qtnItens;
    }

    /**
     * @return the transicao
     */
    public Transicao[] getTransicao() {
        return transicao;
    }

    /**
     * @param transicao the transicao to set
     * @param index
     */
    public void setTransicao(Transicao transicao, int index) {
        this.transicao[index] = transicao;
    }
    
    public void inicializarTransicao(int qtnEstados) {
        this.transicao = new Transicao[qtnEstados];
        for(int i=0; i<qtnEstados; i++){
            this.transicao[i] = new Transicao();
        }
    }
    
}
