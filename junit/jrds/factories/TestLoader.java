package jrds.factories;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import jrds.Tools;
import jrds.factories.Loader.ConfigType;
import jrds.factories.xml.JrdsNode;
import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestLoader {
	static final private Logger logger = Logger.getLogger(TestLoader.class);

	@BeforeClass
	static public void configure() throws ParserConfigurationException, IOException {
		Tools.configure();
		Tools.setLevel(new String[] {"jrds"}, logger.getLevel());
		Tools.prepareXml();

	}

	@Test
	public void doLoadJar() throws ParserConfigurationException, MalformedURLException  {
		Loader l = new Loader();
		l.importUrl(new URL("file:desc"));
		Assert.assertFalse(l.getRepository(ConfigType.GRAPHDESC).isEmpty());
		Assert.assertFalse(l.getRepository(ConfigType.PROBEDESC).isEmpty());
	}

	@Test
	public void doLoadHost() throws Exception  {
		Loader l = new Loader();

		l.importStream(getClass().getResourceAsStream("/ressources/host1.xml"));
		Assert.assertTrue(l.getRepository(ConfigType.HOSTS).containsKey("name"));
	}
	
	@Test
	public void doLoadView() throws Exception  {
		Loader l = new Loader();

		l.importStream(getClass().getResourceAsStream("/ressources/view1.xml"));
		Map<String, JrdsNode> rep = l.getRepository(ConfigType.FILTER);
		logger.trace(rep);
		Assert.assertTrue(rep.containsKey("Test view 1"));
	}
	
	@Test
	public void doLoadProbeDesc() throws Exception {
		Loader l = new Loader();

		l.importStream(getClass().getResourceAsStream("/ressources/fulldesc.xml"));
		Map<String, JrdsNode> rep = l.getRepository(ConfigType.PROBEDESC);
		logger.trace(rep);
		Assert.assertTrue(rep.containsKey("name"));

	}
	
	@Test
	public void doLoadGraph()  throws Exception {
		Loader l = new Loader();

		l.importStream(getClass().getResourceAsStream("/ressources/customgraph.xml"));
		Map<String, JrdsNode> rep = l.getRepository(ConfigType.GRAPHDESC);
		logger.trace(rep);
		Assert.assertTrue(rep.containsKey("name"));
	}

}