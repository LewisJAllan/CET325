package com.example.bg50xx.assignment;


import org.junit.Test;


import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ArtworkUnitTest {
    DecimalFormat df;
    Currency cur;

    @Test
    public void addArtwork(){
        String test = "Dummy data";
        byte[] image = test.getBytes();
        Artwork art = new Artwork("Test", "Lewis", "Unit Test", 2018, 11, image, 1, 1);

        String actual = art.getTitle();
        String expected = "Test";
        assertEquals("Strings do not match", expected, actual);
    }

    @Test
    public void changingArtwork(){
        String test = "Dummy data";
        byte[] image = test.getBytes();
        Artwork art = new Artwork("Test", "Lewis", "Unit Test", 2018, 11, image, 1, 1);
        art.setTitle("Updated Title");

        String actual = art.getTitle();
        String expected = "Updated Title";
        assertEquals("Strings do not match", expected, actual);
    }

    @Test
    public void convertGBPtoEur(){
        df = new DecimalFormat("'€'0.00");
        double rate = 1.13;
        double adult = 10.00;
        String actual = df.format((adult*rate));
        String expected = "€11.30";
        assertEquals("Wrong conversion",expected,actual);

    }

    @Test
    public void discountStudent(){
        df = new DecimalFormat("'£'0.00");
        double discount = 0.3;
        double adult = 10.00;
        String actual = df.format((adult*(1-discount)));
        String expected = "£7.00";
        assertEquals("Wrong conversion",expected,actual);

    }

    @Test
    public void getRate(){
        cur = new Currency();
        cur.setRate(1.25f);
        float actual = cur.getRate();
        float expected = 1.25f;
        assertEquals("Wrong exchange rate",expected,actual,0.1);

    }

}