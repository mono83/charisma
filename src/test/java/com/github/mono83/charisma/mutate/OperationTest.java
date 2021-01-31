package com.github.mono83.charisma.mutate;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OperationTest {
    @DataProvider
    public Object[][] parseDataProvider() {
        return new Object[][]{
                {"set", Operation.SET},
                {"SeT", Operation.SET},
                {"Add", Operation.ADD},
                {"plUs", Operation.ADD},
                {"+", Operation.ADD},
                {"*1000", Operation.X1000},
                {"x1000", Operation.X1000},
        };
    }

    @Test(dataProvider = "parseDataProvider")
    public void testParse(final String given, final Operation expected) {
        Assert.assertEquals(Operation.parse(given), expected);
    }

    @DataProvider
    public Object[][] mergeDataProvider() {
        return new Object[][]{
                {Operation.SET, 1, 5, 5},
                {Operation.SET, 5, 1, 5},
                {Operation.ADD, 5, 1, 6},
                {Operation.ADD, 10, -8, 2},
                {Operation.X1000, 0, 1000, 0},
                {Operation.X1000, 10, 1100, 110},
                {Operation.X1000, 1500, 1100, 1600},
        };
    }

    @Test(dataProvider = "mergeDataProvider")
    public void testMerge(final Operation o, final long a, final long b, final long expected) {
        Assert.assertEquals(o.merge(a, b), expected);
    }

    @Test
    public void testApply() {
    }
}