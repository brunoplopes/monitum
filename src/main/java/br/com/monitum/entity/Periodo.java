package br.com.monitum.entity;

public enum Periodo {
	MATUTINO("Matutino"),VESPERTINO("Vespertido"),NORTURNO("Noturno");
	
	private String value;

    private Periodo(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
