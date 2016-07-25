package com.epam.tat.selenium.runner;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG tng = new TestNG();
        tng.addListener(tla);
        XmlSuite suite = new XmlSuite();
        suite.setName("TmpSuite");

        List<String> files = new ArrayList<String>();
        files.addAll(new ArrayList<String>() {{
            add("testng.xml");
        }});
        suite.setSuiteFiles(files);


        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        tng.setXmlSuites(suites);

        tng.run();
    }
}
