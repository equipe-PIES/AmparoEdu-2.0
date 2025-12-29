package br.com.amparoedu.backend.model;

public class Endereco {
    private String id;
    private String cep;
    private String uf;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String numero;
    private String complemento;
    private int sincronizado;
    private int excluido;

    public Endereco() {
    }

    public Endereco(String id, String cep, String uf, String cidade, String bairro, String logradouro, String numero, String complemento, int sincronizado, int excluido) {
        this.id = id;
        this.cep = cep;
        this.uf = uf;
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.sincronizado = sincronizado;
        this.excluido = excluido;
    }

    public String getId() {
        return id;
    }
    public String getCep() {
        return cep;
    }
    public String getUf() {
        return uf;
    }
    public String getCidade() {
        return cidade;
    }
    public String getBairro() {
        return bairro;
    }
    public String getLogradouro() {
        return logradouro;
    }
    public String getNumero() {
        return numero;
    }
    public String getComplemento() {
        return complemento;
    }
    public int getSincronizado() {
        return sincronizado;
    }
    public int getExcluido() {
        return excluido;
    }


    public void setId(String id) {
        this.id = id;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
    public void setExcluido(int excluido) {
        this.excluido = excluido;
    }
}