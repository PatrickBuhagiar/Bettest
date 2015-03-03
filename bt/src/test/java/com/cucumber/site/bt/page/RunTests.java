package com.cucumber.site.bt.page;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@SuppressWarnings("deprecation")
@RunWith(Cucumber.class)
@Cucumber.Options(format="pretty", glue = { "com.cucumber.site.bt.page"}, features = { "src/test/java/com/resources" }, monochrome = true)
public class RunTests {
}

