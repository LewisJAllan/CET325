package com.example.bg50xx.assignment;


import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ArtworkUnitTest {
    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void addArtwork(){
        String test = "Dummy data";
        byte[] image = test.getBytes();
        Artwork art = new Artwork("Test", "Lewis", "Unit Test", 2018, 11, image, 1, 1);

        String actual = art.getTitle();
        String expected = "Test";
        assertEquals("Strings do not match", expected, actual);
    }

}