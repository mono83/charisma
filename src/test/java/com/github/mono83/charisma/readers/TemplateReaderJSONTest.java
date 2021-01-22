package com.github.mono83.charisma.readers;

import com.github.mono83.charisma.Values;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collection;

public class TemplateReaderJSONTest {
    @Test
    public void testReadEmpty() throws IOException {
        TemplateReaderJSON<Key> r = new TemplateReaderJSON<>(Key.class);
        Assert.assertTrue(r.readString("[]").isEmpty());
    }

    @Test(dependsOnMethods = "testReadEmpty")
    public void testRead() throws IOException {
        TemplateReaderJSON<Key> r = new TemplateReaderJSON<>(Key.class);
        Collection<? extends Values<Key>> data = r.readString("[{\"FOO\":10},{\"FOO\":3, \"BAR\": 5}]");
        Assert.assertEquals(2, data.size());
    }


    public enum Key {
        FOO,
        BAR
    }
}