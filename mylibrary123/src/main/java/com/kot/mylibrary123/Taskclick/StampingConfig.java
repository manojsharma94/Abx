package com.kot.mylibrary123.Taskclick;

class StampingConfig {
    private int stampHeight;
    private int text_view_font_size;
    private int sealHorizontalPadding;
    private int seal_scale;
    private int sealVerticalPadding;
    private int finalPhotoWidth;

    StampingConfig(int finalPhotoWidth) {
        this.finalPhotoWidth = finalPhotoWidth;

        sealHorizontalPadding = 1000;
        seal_scale = 1;
        sealVerticalPadding = 100;
        stampHeight = 270;
        text_view_font_size = 30;

        if (finalPhotoWidth <= 800) {
            stampHeight = 60;
            text_view_font_size = 6;
            seal_scale = 8;
            sealHorizontalPadding = 130;
            sealVerticalPadding = 20;
        } else if (finalPhotoWidth <= 1600) {
            stampHeight = 100;
            text_view_font_size = 12;
            seal_scale = 4;
            sealHorizontalPadding = 270;
            sealVerticalPadding = 50;
        } else if (finalPhotoWidth <= 1920) {
            sealHorizontalPadding = 350;
            seal_scale = 3;
            sealVerticalPadding = 50;
            stampHeight = 130;
            text_view_font_size = 15;
        } else if (finalPhotoWidth <= 2592) {
            sealHorizontalPadding = 350;
            seal_scale = 3;
            stampHeight = 150;
            text_view_font_size = 18;
        } else if (finalPhotoWidth <= 3264) {
            sealHorizontalPadding = 550;
            seal_scale = 2;
            sealVerticalPadding = 100;
            stampHeight = 170;
            text_view_font_size = 20;
        } else if (finalPhotoWidth <= 4032) {
            sealHorizontalPadding = 550;
            seal_scale = 2;
            sealVerticalPadding = 100;
            stampHeight = 250;
            text_view_font_size = 25;
        } else if (finalPhotoWidth <= 4608) {
            sealHorizontalPadding = 1000;
            seal_scale = 1;
            sealVerticalPadding = 100;
            stampHeight = 270;
            text_view_font_size = 30;
        }
    }

    int getFinalPhotoWidth() {
        return finalPhotoWidth;
    }

    int getStampHeight() {
        return stampHeight;
    }

    int getTextViewFontSize() {
        return text_view_font_size;
    }

    int getSealHorizontalPadding() {
        return sealHorizontalPadding;
    }

    int getSeal_scale() {
        return seal_scale;
    }

    int getSealVerticalPadding() {
        return sealVerticalPadding;
    }
}
