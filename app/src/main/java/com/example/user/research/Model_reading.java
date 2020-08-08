package com.example.user.research;

public class Model_reading {

    private String bpm;
    private String temp;
    //private String hum;

    public Model_reading(String bpm, String temp, String hum) {
        this.bpm = bpm;
        this.temp = temp;
        //this.hum = hum;
    }

    public Model_reading() {
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    //public String getHum() {
      //  return hum;
   // }

   // public void setHum(String hum) {
     //   this.hum = hum;
    //}
}
