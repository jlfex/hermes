package com.jlfex.hermes.common.tool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Strings;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 解析器
 */
public class PdmParser {

	private static final String PACKAGE			= "com.jlfex.hermes.model";
	private static final String PATH_FILE		= "generate/hermes.pdb";
	private static final String PATH_TEMPLATE	= "";
	private static final String PATH_MODEL		= "generate/model/";
	private static final String PATH_SCRIPT		= "generate/script/";
	
	private static Map<String, QName> qnames = new HashMap<String, QName>();
	private static Map<String, Namespace> namespaces;
	private static Configuration configuration;
	
	/**
	 * @param name
	 * @param namespace
	 * @return
	 */
	public static QName qname(String name, String namespace) {
		if (!qnames.containsKey(namespace + ":" + name)) qnames.put(namespace + ":" + name, new QName(name, namespace(namespace)));
		return qnames.get(namespace + ":" + name);
	}
	
	/**
	 * @return
	 */
	public static Map<String, Namespace> namespaces() {
		if (namespaces == null) {
			namespaces = new HashMap<String, Namespace>();
			namespaces.put("a", new Namespace("a", "attribute"));
			namespaces.put("c", new Namespace("c", "collection"));
			namespaces.put("o", new Namespace("o", "object"));
		}
		return namespaces;
	}
	
	/**
	 * @param key
	 * @return
	 */
	public static Namespace namespace(String key) {
		return namespaces().get(key);
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public static Configuration configuration() throws IOException {
		if (configuration == null) {
			configuration = new Configuration();
			configuration.setDirectoryForTemplateLoading(new File(PdmParser.class.getResource(PATH_TEMPLATE).getFile()));
		}
		return configuration;
	}
	
	/**
	 * @return
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Table> tables() throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(PATH_FILE);
		Element root = document.getRootElement();
		
		Element element = root.element(qname("RootObject", "o")); 
		element = element.element(qname("Children", "c"));
		element = element.element(qname("Model", "o"));
		element = element.element(qname("Tables", "c"));
		List<Element> tabs = element.elements(qname("Table", "o"));
		List<Table> tables = new ArrayList<Table>(tabs.size());
		
		for (Element t: tabs) {
			element = t.element(qname("Columns", "c"));
			Table table = new Table();
			table.setName(t.elementTextTrim(qname("Name", "a")));
			table.setCode(t.elementTextTrim(qname("Code", "a")));
			
			List<Element> cols = element.elements(qname("Column", "o"));
			for (Element c: cols) {
				Column column = new Column();
				column.setName(c.elementTextTrim(qname("Name", "a")));
				column.setCode(c.elementTextTrim(qname("Code", "a")));
				column.setDataType(c.elementTextTrim(qname("DataType", "a")));
				column.setLength(Integer.parseInt(Strings.empty(c.elementTextTrim(qname("Length", "a")), "-1")));
				column.setPrecision(Integer.parseInt(Strings.empty(c.elementTextTrim(qname("Precision", "a")), "-1")));
				column.setMandatory(Boolean.valueOf(("1".equals(c.elementTextTrim(qname("Mandatory", "a"))) ? "true" : "false")));
				table.getColumns().add(column);
			}
			tables.add(table);
			Logger.info("%s: %s ~ %s", table.getName(), table.getCode(), table.getClassName());
		}
		
		return tables;
	}
	
	/**
	 * @param tables
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public static void model(List<Table> tables) throws IOException, TemplateException {
		for (Table table: tables) {
			StringWriter writer = new StringWriter();
			Template template = configuration().getTemplate("model.ftl");
			Map<String, Object> root = new HashMap<String, Object>();
			FileOutputStream fos = new FileOutputStream(PATH_MODEL + table.getClassName() + ".java");
			
			root.put("table", table);
			root.put("today", Calendars.date());
			root.put("package", PACKAGE);
			template.process(root, writer);
			fos.write(writer.toString().getBytes());
			writer.close();
			fos.close();
		}
	}
	
	/**
	 * @param tables
	 * @param type
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void ddl(List<Table> tables, String type) throws IOException, TemplateException {
		StringWriter writer = new StringWriter();
		Template template = configuration().getTemplate("ddl-" + type + ".ftl");
		Map<String, Object> root = new HashMap<String, Object>();
		FileOutputStream fos = new FileOutputStream(PATH_SCRIPT + "ddl-" + type + ".sql");
		
		root.put("tables", tables);
		template.process(root, writer);
		fos.write(writer.toString().getBytes());
		writer.close();
		fos.close();
	}
	
	/**
	 * @param args
	 * @throws DocumentException 
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public static void main(String... args) throws DocumentException, IOException, TemplateException {
		List<Table> tables = tables();
		model(tables);
		ddl(tables, "mysql");
	}
}
