package ch.inser.fpo.paralive;

import org.junit.Test;

public class MyParaLiveTest {

    @Test
    public void testWithoutPara() {
        new MyParaLive().withoutPara();
    }

    @Test
    public void testWithPara() {
        new MyParaLive().withPara();
    }

}
