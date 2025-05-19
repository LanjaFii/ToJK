package com.lanjafii.tojk;

public class Teaching {
    private int id;
    private String porteParole;
    private String theme;
    private String date;
    private String versets;
    private String resume;

    public Teaching(int id, String porteParole, String theme, String date, String versets, String resume) {
        this.id = id;
        this.porteParole = porteParole;
        this.theme = theme;
        this.date = date;
        this.versets = versets;
        this.resume = resume;
    }

    public int getId() {
        return id;
    }

    public String getPorteParole() {
        return porteParole;
    }

    public String getTheme() {
        return theme;
    }

    public String getDate() {
        return date;
    }

    public String getVersets() {
        return versets;
    }

    public String getResume() {
        return resume;
    }
}
