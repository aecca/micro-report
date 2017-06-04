package com.bbva.reports.engine.visualizer.birt;

public final class BirtTemplate {

    public static final String HTML_TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<report xmlns=\"http://www.eclipse.org/birt/2005/design\" version=\"3.2.23\" id=\"1\">\n" +
            "    <property name=\"author\">BBVA-Report</property>\n" +
            "    <property name=\"createdBy\">BBVA Reporter microservice</property>\n" +
            "    <text-property name=\"title\">Documento</text-property>\n" +
            "    <property name=\"units\">in</property>\n" +
            "    <property name=\"iconFile\">/templates/blank_report.gif</property>\n" +
            "    <property name=\"bidiLayoutOrientation\">ltr</property>\n" +
            "    <page-setup>\n" +
            "        <simple-master-page name=\"DefaultMasteerPage\" id=\"6\"/>\n" +
            "    </page-setup>\n" +
            "    <body>\n" +
            "        <text id=\"7\">\n" +
            "            <property name=\"contentType\">html</property>\n" +
            "            <text-property name=\"content\"><![CDATA[<value-of format=\"HTML\">params['content']</value-of>]]></text-property>\n" +
            "        </text>\n" +
            "    </body>\n" +
            "</report>\n";

}
