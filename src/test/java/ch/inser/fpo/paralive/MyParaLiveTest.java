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

    @Test
    public void testWithRunnableEncapsulatedCallable() {
        new MyParaLive().withRunnableEncapsulatedCallable();
    }

    @Test
    public void testWithCallable() {
        new MyParaLive().withCallable();
    }

}
