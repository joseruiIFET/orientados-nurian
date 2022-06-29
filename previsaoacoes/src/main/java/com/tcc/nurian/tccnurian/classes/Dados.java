/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.nurian.tccnurian.classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author nuria
 */
public class Dados {

    //pq voce esta criando um precosFechamento de 4 posicoes?
    private Preco[] precosFechamento = new Preco[4];
    private double desvPad;
    private double valMin;
    private double valMax;
    private int qtnTotal;
    public String PATH;

    public Dados() {
        //this.PATH = "C:\\Users\\nuria\\Documents\\NetBeansProjects\\StocksForecast\\src\\assets\\dados.csv";
        this.PATH = "dados.csv";
        try {
            String arquivoCSV = this.PATH;
            BufferedReader br = new BufferedReader(new FileReader(arquivoCSV));

            // inicialização
            long linhas = br.lines().count();
            this.precosFechamento = new Preco[(int) linhas];
            for (int j = 0; j < (int) linhas; j++) {
                this.precosFechamento[j] = new Preco();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.desvPad = 0;
        this.valMin = 0;
        this.valMax = 0;
        this.qtnTotal = this.precosFechamento.length;
    }

    /**
     * @return the precosFechamento
     */
    public Preco[] getPrecosFechamento() {
        return precosFechamento;
    }

    /**
     * @param precosFechamento the precosFechamento to set
     */
    public void setPrecosFechamento(Preco[] precosFechamento) {
        this.precosFechamento = precosFechamento;

    }

    /**
     * @return the desvPad
     */
    public double getDesvPad() {
        return desvPad;
    }

    /**
     * @param desvPad the desvPad to set
     */
    public void setDesvPad(double desvPad) {
        this.desvPad = desvPad;
    }

    /**
     * @return the valMin
     */
    public double getValMin() {
        return valMin;
    }

    /**
     * @param valMin the valMin to set
     */
    public void setValMin(double valMin) {
        this.valMin = valMin;
    }

    /**
     * @return the valMax
     */
    public double getValMax() {
        return valMax;
    }

    /**
     * @param valMax the valMax to set
     */
    public void setValMax(double valMax) {
        this.valMax = valMax;
    }

    /**
     * @return the qtnTotal
     */
    public int getQtnTotal() {
        return qtnTotal;
    }

    /**
     * @param qtnTotal the qtnTotal to set
     */
    public void setQtnTotal(int qtnTotal) {
        this.qtnTotal = qtnTotal;
    }

    public void lerCSV() {
        BufferedReader br = null;
        String linha;
        try {
            int i = 0;
            br = new BufferedReader(new FileReader(this.PATH));
            linha = br.readLine();
            while (linha != null) {
                this.precosFechamento[i].setPreco(Float.parseFloat(linha.replace(",", ".")));

                i++;
                linha = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("- Arquivo lido com sucesso!");
    }

    public void calculaDesvPad() {
        float somaPrecos = 0;

        for (int i = 0; i < this.precosFechamento.length; i++) {
            somaPrecos += this.precosFechamento[i].getPreco();
        }

        float media = somaPrecos / this.precosFechamento.length;

        this.desvPad = 0;
        for (int i = 0; i < this.precosFechamento.length; i++) {
            this.desvPad += Math.pow(this.precosFechamento[i].getPreco() - media, 2);
        }
        
        this.desvPad = this.desvPad / this.precosFechamento.length;
        
        if (this.desvPad > 0) {
            this.desvPad = Math.sqrt(this.desvPad);
        }
        System.out.println("- Desvio padrao concluido: "+this.desvPad);
    }

    public void calculaValMin() {
        for (int i = 0; i < this.precosFechamento.length; i++) {
            if (i == 0) {
                this.valMin = this.precosFechamento[i].getPreco();
            }
            if (this.precosFechamento[i].getPreco() < this.valMin) {
                this.valMin = this.precosFechamento[i].getPreco();
            }
        }
        System.out.println("- Valor minimio concluido: "+this.valMin);
    }

    public void calculaValMax() {
        for (int i = 0; i < this.precosFechamento.length; i++) {
            if (i == 0) {
                this.valMax = this.precosFechamento[i].getPreco();
            }
            if (this.precosFechamento[i].getPreco() > this.valMax) {
                this.valMax = this.precosFechamento[i].getPreco();
            }
        }
        System.out.println("- Valor máximo concluido: "+this.valMax);
    }
}
