package br.com.monitum.dto;

public class OptionDTO {
	private Long key;
	private String value;
	
	public OptionDTO(Long key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
