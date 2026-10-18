package com.example.sistemahotel.model;

public class Hospede {
    private final String nome;
    private final String cpf;

    public Hospede(String nome, String cpf) {
        this.nome = nome == null ? null : nome.trim();
        this.cpf = cpf == null ? null : cpf.trim();
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
}
