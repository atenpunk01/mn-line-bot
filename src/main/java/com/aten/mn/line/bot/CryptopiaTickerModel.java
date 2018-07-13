package com.aten.mn.line.bot;

import java.math.BigDecimal;

public class CryptopiaTickerModel {

    private boolean Success;
    private Object Message;
    private DataBean Data;
    private Object Error;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public Object getMessage() {
        return Message;
    }

    public void setMessage(Object Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public Object getError() {
        return Error;
    }

    public void setError(Object Error) {
        this.Error = Error;
    }

    public static class DataBean {
        /**
         * TradePairId : 6073
         * Label : CDM/BTC
         * AskPrice : 5.09E-6
         * BidPrice : 4.58E-6
         * Low : 4.57E-6
         * High : 0.005
         * Volume : 27101.35834485
         * LastPrice : 5.2E-6
         * BuyVolume : 909515.79551515
         * SellVolume : 203483.95895937
         * Change : -99.9
         * Open : 0.005
         * Close : 5.2E-6
         * BaseVolume : 0.18705238
         * BuyBaseVolume : 0.31410629
         * SellBaseVolume : 10.0153562
         */

        private int TradePairId;
        private String Label;
        private BigDecimal AskPrice;
        private BigDecimal BidPrice;
        private double Low;
        private double High;
        private double Volume;
        private double LastPrice;
        private double BuyVolume;
        private double SellVolume;
        private double Change;
        private double Open;
        private double Close;
        private double BaseVolume;
        private double BuyBaseVolume;
        private double SellBaseVolume;

        public int getTradePairId() {
            return TradePairId;
        }

        public void setTradePairId(int TradePairId) {
            this.TradePairId = TradePairId;
        }

        public String getLabel() {
            return Label;
        }

        public void setLabel(String Label) {
            this.Label = Label;
        }

        public BigDecimal getAskPrice() {
            return AskPrice;
        }

        public void setAskPrice(BigDecimal AskPrice) {
            this.AskPrice = AskPrice;
        }

        public BigDecimal getBidPrice() {
            return BidPrice;
        }

        public void setBidPrice(BigDecimal BidPrice) {
            this.BidPrice = BidPrice;
        }

        public double getLow() {
            return Low;
        }

        public void setLow(double Low) {
            this.Low = Low;
        }

        public double getHigh() {
            return High;
        }

        public void setHigh(double High) {
            this.High = High;
        }

        public double getVolume() {
            return Volume;
        }

        public void setVolume(double Volume) {
            this.Volume = Volume;
        }

        public double getLastPrice() {
            return LastPrice;
        }

        public void setLastPrice(double LastPrice) {
            this.LastPrice = LastPrice;
        }

        public double getBuyVolume() {
            return BuyVolume;
        }

        public void setBuyVolume(double BuyVolume) {
            this.BuyVolume = BuyVolume;
        }

        public double getSellVolume() {
            return SellVolume;
        }

        public void setSellVolume(double SellVolume) {
            this.SellVolume = SellVolume;
        }

        public double getChange() {
            return Change;
        }

        public void setChange(double Change) {
            this.Change = Change;
        }

        public double getOpen() {
            return Open;
        }

        public void setOpen(double Open) {
            this.Open = Open;
        }

        public double getClose() {
            return Close;
        }

        public void setClose(double Close) {
            this.Close = Close;
        }

        public double getBaseVolume() {
            return BaseVolume;
        }

        public void setBaseVolume(double BaseVolume) {
            this.BaseVolume = BaseVolume;
        }

        public double getBuyBaseVolume() {
            return BuyBaseVolume;
        }

        public void setBuyBaseVolume(double BuyBaseVolume) {
            this.BuyBaseVolume = BuyBaseVolume;
        }

        public double getSellBaseVolume() {
            return SellBaseVolume;
        }

        public void setSellBaseVolume(double SellBaseVolume) {
            this.SellBaseVolume = SellBaseVolume;
        }
    }
}
