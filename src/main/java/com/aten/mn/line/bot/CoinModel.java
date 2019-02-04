/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aten.mn.line.bot;

/**
 *
 * @author IdeaPad
 */
public class CoinModel {
    private String name;
    private String key;
    private String buy;
    private String sell;
    private String change;
    private int[] changeBlock;
    private int[] changeCollateral;
    private long maxsupply;
    private String urlImage01;
    private String urlImage02;
    private String urlImage03;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

	public int[] getChangeBlock() {
		return changeBlock;
	}

	public void setChangeBlock(int[] changeBlock) {
		this.changeBlock = changeBlock;
	}

	public int[] getChangeCollateral() {
		return changeCollateral;
	}

	public void setChangeCollateral(int[] changeCollateral) {
		this.changeCollateral = changeCollateral;
	}

	public long getMaxsupply() {
		return maxsupply;
	}

	public void setMaxsupply(long maxsupply) {
		this.maxsupply = maxsupply;
	}

	public String getUrlImage01() {
		return urlImage01;
	}

	public void setUrlImage01(String urlImage01) {
		this.urlImage01 = urlImage01;
	}

	public String getUrlImage02() {
		return urlImage02;
	}

	public void setUrlImage02(String urlImage02) {
		this.urlImage02 = urlImage02;
	}

	public String getUrlImage03() {
		return urlImage03;
	}

	public void setUrlImage03(String urlImage03) {
		this.urlImage03 = urlImage03;
	}
}
