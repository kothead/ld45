package com.shoggoth.ld45.util;

public class RenderConfig {

    private int fieldWidth;
    private int fieldHeight;
    private int cardWidth = 140;
    private int cardHeight = 196;
    private int padding = 8;
    private int margin = 100;

    public void setCardWidth(int cardWidth) {
        this.cardWidth = cardWidth;
    }

    public void setCardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public int getPadding() {
        return padding;
    }

    public int getMargin() {
        return margin;
    }

    public int getCardWidth() {
        return cardWidth;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public int getMaxHeight() {
        return margin * 2 + padding * (fieldHeight - 1) + cardHeight * fieldHeight;
    }

    public int getMaxWidth() {
        return margin * 2 + padding * (fieldWidth - 1) + cardWidth * fieldWidth;
    }
}
