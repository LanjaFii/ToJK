package com.lanjafii.tojk;

public class Song {
    int id;
    String nomChanson;
    String genre;
    String tonalite;
    String posteBatterie;
    String posteBass;
    String posteSolo;
    String posteClavier1;
    String posteClavier2;

    public Song(int id, String nomChanson, String genre, String tonalite,
                String posteBatterie, String posteBass, String posteSolo,
                String posteClavier1, String posteClavier2) {
        this.id = id;
        this.nomChanson = nomChanson;
        this.genre = genre;
        this.tonalite = tonalite;
        this.posteBatterie = posteBatterie;
        this.posteBass = posteBass;
        this.posteSolo = posteSolo;
        this.posteClavier1 = posteClavier1;
        this.posteClavier2 = posteClavier2;
    }
}
