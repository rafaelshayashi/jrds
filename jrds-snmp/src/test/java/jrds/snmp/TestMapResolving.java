package jrds.snmp;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.event.Level;
import org.snmp4j.smi.OID;

import jrds.Log4JRule;
import jrds.PropertiesManager;
import jrds.Tools;

public class TestMapResolving {

    @Rule
    public final TemporaryFolder testFolder = new TemporaryFolder();

    @Rule
    public final Log4JRule logrule = new Log4JRule(this);

    @Before
    public void loggers() {
        logrule.setLevel(Level.TRACE, "jrds.snmp.SnmpConfigurator", "jrds.snmp.SnmpCollectResolver");
    }

    @Test
    public void TestLoadDefault() throws IOException {
        PropertiesManager pm = Tools.makePm(testFolder, "mibdirs=", "oidmaps=" + SnmpConfigurator.class.getClassLoader().getResource("oidmap.properties").getFile() + "; ");
        SnmpConfigurator conf = new SnmpConfigurator();
        conf.configure(pm);
        SnmpCollectResolver resolver = new SnmpCollectResolver();
        resolver.resolve("diskIOWrites").equals(new OID("1.3.6.1.4.1.2021.13.15.1.1.6"));
    }

}
