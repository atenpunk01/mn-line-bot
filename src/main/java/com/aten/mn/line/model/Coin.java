package com.aten.mn.line.model;

public class Coin {
	private byte[] data;
	private String coin;
		
	@Override
	public boolean equals(Object obj) {
		return this.coin.equals(((Coin)obj).getCoin());
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getCoin() {
		return coin;
	}
	public void setCoin(String coin) {
		this.coin = coin;
	}
	
	
}
