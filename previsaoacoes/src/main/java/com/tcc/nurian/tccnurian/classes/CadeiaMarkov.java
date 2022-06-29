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
 */
public class CadeiaMarkov {

    private List<Estado> estados;
    private MatrizTransicao matrizOriginal;
    private Distribuicao distribuicao;
    private Dados conjuntoDados;

    public CadeiaMarkov() {
        this.conjuntoDados = new Dados();
        this.estados = new ArrayList();

        this.conjuntoDados.lerCSV();
        this.conjuntoDados.calculaDesvPad();
        this.conjuntoDados.calculaValMin();
        this.conjuntoDados.calculaValMax();

        this.criarEstados();
        this.inicializarTransicao();
        this.relacionarPrecoEstado();
        this.criarTransicoes();

        this.matrizOriginal = new MatrizTransicao(this.estados.size());
        this.matrizOriginal.inicializarMatriz(this.estados);
        this.matrizOriginal.imprime(this.matrizOriginal.getMatriz());

        this.distribuicao = new Distribuicao(this.estados.size());
        this.distribuicao.criarDistribuicaoM0(this.estados, this.conjuntoDados.getPrecosFechamento().length);
        this.distribuicao.imprime(this.distribuicao.getDistribuicaoM0());
    }

    /**
     * Calculará a Matrix_Mi, depois o vetor_Mi e na sequencia o vetor_Mi_acc
     *
     * @param passos
     * @return
     */
    public List<Double> getDistribuicaoAcc(int passos) {
        double matrizMi[][] = this.matrizOriginal.pontencia(passos);
        double vetorDistribuicaoMi[] = this.distribuicao.getDistribuicaoMi(matrizMi);
        return this.distribuicao.getDistribuicaoAcc();
    }

    public void criarEstados() {
        boolean stop = false;
        int i = 0;
        double maximo = this.conjuntoDados.getValMax();

        while (!stop) {
            this.estados.add(new Estado());
            this.estados.get(i).setNome(i + 1);
            if (i == 0) {
                this.estados.get(i).setInicio(this.conjuntoDados.getValMin());
            } else {
                this.estados.get(i).setInicio(this.estados.get(i - 1).getFim());
            }

            double fim = this.estados.get(i).getInicio() + this.conjuntoDados.getDesvPad();

            if (fim > maximo) {
                this.estados.get(i).setFim(maximo);
                stop = true;                
            } else {
                this.estados.get(i).setFim(fim);
            }

            i++;
        }
        System.out.println("- Criação estados concluida.");
    }

    public void inicializarTransicao() {
        for (int i = 0; i < this.estados.size(); i++) {
            this.estados.get(i).inicializarTransicao(this.estados.size());
        }
    }

    public void relacionarPrecoEstado() {
        for (int i = 0; i < this.conjuntoDados.getPrecosFechamento().length; i++) {
            double preco = this.conjuntoDados.getPrecosFechamento()[i].getPreco();

            for (int j = 0; j < this.estados.size(); j++) {
                int prevQtnEstados = this.estados.get(j).getQtnItens();

                if (j == this.estados.size() - 1) { // último da estdo
                    if (preco >= this.estados.get(j).getInicio() && preco <= this.estados.get(j).getFim()) {
                        this.conjuntoDados.getPrecosFechamento()[i].setEstado(this.estados.get(j));
                        this.estados.get(j).setQtnItens(prevQtnEstados + 1);
                    }
                    break;
                }
                if (preco >= this.estados.get(j).getInicio() && preco < this.estados.get(j).getFim()) {
                    this.conjuntoDados.getPrecosFechamento()[i].setEstado(this.estados.get(j));
                    this.estados.get(j).setQtnItens(prevQtnEstados + 1);
                    break;
                }
            }
        }
    }

    /**
     * Criar as transições e conta quantas ocorrencias tem em cada intervalo
     */
    public void criarTransicoes() {
        for (int i = 0; i < this.conjuntoDados.getPrecosFechamento().length; i++) {
            if (i == this.conjuntoDados.getPrecosFechamento().length - 1) { // último d lista
                Estado estadoAtual = this.conjuntoDados.getPrecosFechamento()[i].getEstado();

                int maiorQtnTransicao = 0;
                int maiorQtnTransicaoIndex = 0;
                for (int j = 0; j < estadoAtual.getTransicao().length; j++) { // busca em seu estado a transição com maior qunatidade de elementos
                    if (j == 0) {
                        maiorQtnTransicaoIndex = estadoAtual.getTransicao()[j].getQtn();
                    } else if (estadoAtual.getTransicao()[j].getQtn() > maiorQtnTransicao) {
                        maiorQtnTransicao = estadoAtual.getTransicao()[j].getQtn();
                        maiorQtnTransicaoIndex = j;
                    }
                }

                estadoAtual.getTransicao()[maiorQtnTransicaoIndex].setQtn(maiorQtnTransicao + 1); // adicionar esse último valor da lista, sem transição, nessa transição com maior número de itens

                break;
            }

            Estado estadoAtual = this.conjuntoDados.getPrecosFechamento()[i].getEstado();
            Estado estadoSeguinte = this.conjuntoDados.getPrecosFechamento()[i + 1].getEstado();
            if (estadoAtual.getTransicao()[estadoSeguinte.getNome() - 1].getNome().isEmpty()) {
                estadoAtual.getTransicao()[estadoSeguinte.getNome() - 1].setNome("I" + estadoAtual.getNome() + " -> " + "I" + estadoSeguinte.getNome());
            }
            int prevQtn = estadoAtual.getTransicao()[estadoSeguinte.getNome() - 1].getQtn();
            estadoAtual.getTransicao()[estadoSeguinte.getNome() - 1].setQtn(prevQtn + 1);
        }

        System.out.println("- Criando transições:");
        for (int n = 0; n < this.estados.size(); n++) {
            for (int l = 0; l < this.estados.get(n).getTransicao().length; l++) {
                System.out.println(this.estados.get(n).getTransicao()[l].getNome() + ", qtd:" + this.estados.get(n).getTransicao()[l].getQtn());
            }
        }
        System.out.println("- Transições criadas com sucesso");
    }

    /**
     * @return the states
     */
    public List<Estado> getStates() {
        return estados;
    }

    /**
     * @param states the states to set
     */
    public void setStates(ArrayList<Estado> states) {
        this.estados = states;
    }

    /**
     * @return the matrizOriginal
     */
    public MatrizTransicao getMatrizOriginal() {
        return matrizOriginal;
    }

    /**
     * @param matrizOriginal the matrizOriginal to set
     */
    public void setMatrizOriginal(MatrizTransicao matrizOriginal) {
        this.matrizOriginal = matrizOriginal;
    }

    /**
     * @return the distribuicao
     */
    public Distribuicao getDistribuicao() {
        return distribuicao;
    }

    /**
     * @param distribuicao the distribuicao to set
     */
    public void setDistribuicao(Distribuicao distribuicao) {
        this.distribuicao = distribuicao;
    }

    /**
     * @return the conjuntoDados
     */
    public Dados getConjuntoDados() {
        return conjuntoDados;
    }

    /**
     * @param conjuntoDados the conjuntoDados to set
     */
    public void setConjuntoDados(Dados conjuntoDados) {
        this.conjuntoDados = conjuntoDados;
    }

}
