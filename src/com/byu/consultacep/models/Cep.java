package com.byu.consultacep.models;

import com.byu.consultacep.exceptions.InvalidDataException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Cep {
    protected String cep;
    protected String rua;
    protected String bairro;
    protected String uf;
    protected String cidade;

    public Cep() {}

    public Cep(CepAPI api) {
        this.cep = api.cep();
        this.rua = api.logradouro();
        this.bairro = api.bairro();
        this.uf = api.uf();
        this.cidade = api.localidade();
    }

    public Cep(String cep){
        this.cep = cep;
        fetchCepData(cep);
    }

    public Cep(String cep, String rua, String bairro, String uf, String cidade) {
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.uf = uf;
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    static String buildURL(String cep){
        //concatenate and returns the url with informed cep and expected format (json)
        String baseCEPURL = "http://viacep.com.br/ws/";
        String resultFormat = "/json/";
        return baseCEPURL+cep+resultFormat;
    }

    private void fetchCepData (String cep){
        //fetch data from remote API(viacep) and fills the current object with returned data
        String url = buildURL(cep);
        int code = 0;
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
            JsonElement json = JsonParser.parseString((String) response.body());
            JsonObject jsonObject = json.getAsJsonObject();
            code = response.statusCode();
            if (jsonObject.has("localidade")) {
                this.cidade = jsonObject.get("localidade").getAsString();
                this.uf = jsonObject.get("uf").getAsString();
                this.rua = jsonObject.get("logradouro").getAsString();
                this.bairro = jsonObject.get("bairro").getAsString();
            } else {
                throw new RuntimeException("Unable to find the CEP: " + cep);
            }
            if (code != 200){
                throw new InvalidDataException("Invalid CEP");
            }
        } catch(IllegalArgumentException e){
            System.out.println("An error has ocurred: "+ e.getMessage());
        } catch (InvalidDataException e) {
            System.out.println("An error has ocurred: "+e.getMessage());
        } catch(Exception e) {
            System.out.println("An error has ocurred: " + e.getMessage());
        }
    }
    //overriding toString method for better representing the CEP data
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
