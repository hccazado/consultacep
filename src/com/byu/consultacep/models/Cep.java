package com.byu.consultacep.models;

public class Cep {
    private String cep;
    private String rua;
    private String bairro;
    private String uf;
    private String cidade;



    @Override
    public String toString() {
        return "cep{" +
                "cep='" + cep + '\'' +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}
