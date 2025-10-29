package com.byu.consultacep.models;

public class Cep {
    private String cep;
    private String rua;
    private String bairro;
    private String uf;
    private String cidade;

    public Cep(CepAPI api) {
        this.cep = api.cep();
        this.rua = api.logradouro();
        this.bairro = api.bairro();
        this.uf = api.uf();
        this.cidade = api.localidade();
    }

    public String getCidade() {
        return cidade;
    }

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
