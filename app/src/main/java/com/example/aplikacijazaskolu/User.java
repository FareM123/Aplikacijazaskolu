package com.example.aplikacijazaskolu;

public class User{
    private String name;
    private String smjer;
    private String nacSmjer;
    private String vjeronauk;
    private String dsd;

    public User(String name, String smjer, String nacSmjer, String vjeronauk, String dsd) {
        this.name = name;
        this.smjer = smjer;
        this.nacSmjer = nacSmjer;
        this.vjeronauk = vjeronauk;
        this.dsd = dsd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmjer() {
        return smjer;
    }

    public void setSmjer(String smjer) {
        this.smjer = smjer;
    }

    public String getNacSmjer() {
        return nacSmjer;
    }

    public void setNacSmjer(String nacSmjer) {
        this.nacSmjer = nacSmjer;
    }

    public String getVjeronauk() {
        return vjeronauk;
    }

    public void setVjeronauk(String vjeronauk) {
        this.vjeronauk = vjeronauk;
    }

    public String getDsd() {
        return dsd;
    }

    public void setDsd(String dsd) {
        this.dsd = dsd;
    }
}
