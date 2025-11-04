package com.byu.consultacep.models;

public class Address extends Cep {
    private int number;

    public Address(String cep, int number){
        //calling the appropriated CEP constructor
        super(cep);
        this.number = number;
    }

    public Address(String cep, String rua, String bairro, String uf, String cidade, int number) {
        super(cep, rua, bairro, uf, cidade);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    //overriding toString method for representing complete data  from CEP and Address classes
    @Override
    public String toString() {
        return "Address{" +
                "cep:'" + cep + '\'' +
                ", rua:'" + rua + '\'' +
                ", num.:'" + number + '\'' +
                ", bairro:'" + bairro + '\'' +
                ", uf:'" + uf + '\'' +
                '}';
    }

}
