package com.example.kaanu.challangelayout;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * my custom insult generator class
 * Created by Kaanu on 9/30/2015.
 */

public class Generator implements Parcelable {

    String[] arrAdj;
    String[] arrNoun;
    int nounLength, adjLength;
    int insultCount, totalCount;
    String lastMsg;

    /**
     * Generator class used to construct a message using an array of adjective and an array of nouns
     * @param N String[] of Nouns
     * @param A String[] of Adjectives
     */
    public Generator(String[] N, String[] A){
        // initialize arrays and defaults
        arrNoun = N;
        arrAdj = A;
        nounLength = 1;
        adjLength = 4;
        resetCount();
    }

    /**
     * Generator class used to construct a message using an array of adjective and an array of nouns
     * @param N String[] of Nouns
     * @param A String[] of Adjectives
     * @param a int number of Adjectives to use per message
     */
    public Generator(String[] N, String[] A, int a){
        // initialize arrays, setup defaults, and set adjective length
        arrNoun = N;
        arrAdj = A;
        nounLength = 1;
        adjLength = a;
        resetCount();
    }

    // --------------------------------------------------------- gets and sets

    /**
     * Sets the number of Nouns to use per message
     * @param I int number of nouns to use
     */
    public void setNounAmmount(int I){ nounLength = I; }

    /**
     * Gets the number of Nouns to use per message
     * @return int number of nouns being used
     */
    public int getNounAmmount(){ return nounLength; }

    /**
     * Sets the number of Adjectives to use per message
     * @param I int number of adjectives to use
     */
    public void setAdjAmmount(int I){ adjLength = I; }

    /**
     * Gets the number of Adjectives to use per message
     * @return int number of adjectives being used
     */
    public int getAdjLength(){ return adjLength; }

    /**
     * Reset generated message tracker to zero
     */
    public void resetCount() {insultCount = 0;}

    /**
     * Get the number of messages generated since the last reset
     * @return int number of messages
     */
    public int getInsultCount(){return insultCount;}

    public int getInsultTotal(){return totalCount;}

    public String getLastMsg() { return lastMsg; }

    //------------------------------------------------------------------ public methods

    /**
     * Generates a message based on the number of Nouns and Adjectives set to use
     * @return String generated message
     */
    public String insultMe(){
        // clear any existing string data
        String insult = "";

        // randomize the Arrays
        shuffle(arrNoun);
        shuffle(arrAdj);

        // concatenate adjectives and nouns
        for (int i = 0; i < adjLength; i++) { insult+= " " + arrAdj[i]; }
        for (int i = 0; i < nounLength; i++) { insult+= " " + arrNoun[i]; }

        // increase insult count tracker and return insult
        insultCount++;
        totalCount++;
        lastMsg = insult;
        return insult;
    }

    //------------------------------------------------------------------ private methods

    /**
     * Loops thought the provided Array to insure all items have been moved at lest once
     * @param T String[] to randomize
     */
    private void shuffle(String[] T){
        // loop though all index's of array
        int i = T.length-1;
        while (i>0) swap(T,i--,(int)(Math.random()*i));
    }

    /**
     * Takes the provided Array, index, and the new index and swaps the position of the items with the provided index's
     * @param T String[] to swap items in
     * @param I int target index to swap
     * @param J int index to swap with the target index
     */
    private void swap(String[] T, int I, int J) {
        // swap the current index item's place with the provided index
        String tempy = T[I];
        T[I] = T[J];
        T[J] = tempy;
    }

    //------------------------------------------------------------------ Parsable methods
    protected Generator(Parcel in) {
        insultCount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(insultCount);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Generator> CREATOR = new Parcelable.Creator<Generator>() {
        @Override
        public Generator createFromParcel(Parcel in) {
            return new Generator(in);
        }

        @Override
        public Generator[] newArray(int size) {
            return new Generator[size];
        }
    };
}