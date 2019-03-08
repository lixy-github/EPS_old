package com.broadcontact.common.tools;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Jeaner Chen(BCBS) XML tools 一系列xml的工具 基于org.w3c.dom 用于解析、生成XML文件
 */
public class XMLDomTools {

	/**
	 * 根据xml文件路径获取xml实体对象
	 * 
	 * @param XMLPath:欲操作xml文件路径
	 * @return Document:该XML实体对象
	 */
	public static Document getDOMByPath(String XMLPath) {
		File f = new File(XMLPath);
		return getDOMByFile(f);
	}

	/**
	 * 根据xml文件字串获取xml实体对象
	 * 
	 * @param XMLString:xml文件字串
	 * @return Document:该XML实体对象
	 */
	public static Document getDOMByString(String XMLString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(XMLString);
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		return document;
	}

	/**
	 * 根据XML文件实体获取xml实体对象
	 * 
	 * @param File
	 *          xml文件
	 * @return 该XML实体对象
	 */
	public static Document getDOMByFile(File f) {
		Document document = null;
		if (!f.exists() || !f.isFile()) {
			System.out.println("XML文件不存在！");
			return null;
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(f);
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		return document;
	}

	/**
	 * 创建一空白xml实体对象
	 * 
	 * @return Document:空白XML实体对象
	 */
	public static Document createBlankDOM() {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException ex) {
			System.out.println("ParserConfigurationException:");
			ex.printStackTrace();
		}
		return document;
	}

	/**
	 * 依特定路径从初始节点开始查找节点
	 * 
	 * @param node
	 *          开始查找的节点
	 * @param path
	 *          路径，以‘/’为间隔符
	 * @return 如果找不到 返回null
	 */
	public static Node getNodeByPath(Node node, String path) {
		StringTokenizer st = new StringTokenizer(path, "/");
		String currentNodeName;
		Node currentNode = node;
		Node selectedNode = node;
		NodeList currentNodeList;
		while (st.hasMoreElements()) {
			currentNodeList = selectedNode.getChildNodes();
			currentNodeName = (String) st.nextElement();
			selectedNode = null;
			for (int i = 0; i < currentNodeList.getLength(); i++) {
				currentNode = currentNodeList.item(i);
				if (currentNode.getNodeType() == 1
						&& currentNode.getNodeName().equals(currentNodeName)) {
					selectedNode = currentNode;
					break;
				}
			}
			if (selectedNode == null)
				return null;
		}
		return selectedNode;
	}

	/**
	 * 创建待定的
	 * 
	 * @param node
	 * @param path
	 * @return
	 */
	public static Node setSpecificPath(Node node, String path) {
		StringTokenizer st = new StringTokenizer(path, "/");
		String currentNodeName;
		Node currentNode = node, selectedNode = node;
		NodeList currentNodeList;
		while (st.hasMoreElements()) {
			currentNodeList = selectedNode.getChildNodes();
			currentNodeName = (String) st.nextElement();
			selectedNode = null;
			for (int i = 0; i < currentNodeList.getLength(); i++) {
				currentNode = currentNodeList.item(i);
				if (currentNode.getNodeType() == 1
						&& currentNode.getNodeName().equals(currentNodeName)) {
					selectedNode = currentNode;
					break;
				}
			}
			if (selectedNode == null)
				return null;
		}
		return selectedNode;
	}

	/**
	 * 获得指定节点下特定名称的子节点集合
	 * 
	 * @param node
	 *          目标父节点
	 * @param nodeName
	 *          子节点名称
	 * @return 节点集合
	 */
	public static Vector getSubNodesList(Node node, String nodeName) {
		if (node == null)
			return null;
		Vector nodes = new Vector();
		Node currentNode = null;
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			currentNode = nodeList.item(i);
			if (currentNode.getNodeType() == 1
					&& currentNode.getNodeName().equals(nodeName)) {
				nodes.add(currentNode);
			}
		}
		return nodes;
	}

	/**
	 * 创建指定节点
	 * 
	 * @param node
	 *          相对根结点
	 * @param nodeName
	 * @return
	 */
	public static Node createSpecificNode(Document document, Node node,
			String path) {
		Node currentNode = node, tmpNode = null, ParentNode = node;
		String currentNodeName = null;
		StringTokenizer st = new StringTokenizer(path, "/");
		while (st.hasMoreElements()) {
			ParentNode = currentNode;
			currentNodeName = (String) st.nextElement();
			tmpNode = setSpecificPath(ParentNode, currentNodeName);
			if (tmpNode == null) {
				currentNode = currentNode.appendChild(document
						.createElement(currentNodeName));
			} else {
				currentNode = tmpNode;
			}
		}
		if (tmpNode != null)
			currentNode = currentNode.getParentNode().appendChild(
					document.createElement(currentNodeName));
		return currentNode;
	}

	/**
	 * 获得节点值
	 * 
	 * @param node
	 *          查找节点
	 * @param nodeName
	 *          查找名称
	 * @return 节点值
	 */
	public static Collection getNodeValue(Node node, String nodeName) {
		if (node == null)
			return null;
		Node currentNode = null;
		NodeList nodeList = node.getChildNodes();
		Collection collection = new ArrayList();
		for (int i = 0; i < nodeList.getLength(); i++) {
			currentNode = nodeList.item(i);
			if (currentNode.getNodeType() == 1
					&& currentNode.getNodeName().equals(nodeName)) {
				if (currentNode.getFirstChild() != null)
					collection.add(currentNode.getFirstChild().getNodeValue());
			}
		}
		return collection;
	}

	/**
	 * 获得节点值
	 * 
	 * @param node
	 *          查找节点
	 * @param nodeName
	 *          查找名称
	 * @return 节点值
	 */
	public static Collection getNodes(Node node, String nodeName) {
		if (node == null)
			return null;
		Node currentNode = null;
		NodeList nodeList = node.getChildNodes();
		Collection collection = new ArrayList();
		for (int i = 0; i < nodeList.getLength(); i++) {
			currentNode = nodeList.item(i);
			if (currentNode.getNodeType() == 1
					&& currentNode.getNodeName().equals(nodeName)) {
				collection.add(currentNode);
			}
		}
		return collection;
	}

	/**
	 * 获得节点某个属性值
	 * 
	 * @param node
	 *          查找节点
	 * @param atrributeName
	 *          属性名称
	 * @return 属性值
	 */
	public static String getAtrribute(Node node, String atrributeName) {
		if (node == null)
			return null;
		NamedNodeMap nodeList = node.getAttributes();
		Node currentNode = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			currentNode = nodeList.item(i);
			if (currentNode.getNodeType() == 2
					&& currentNode.getNodeName().equals(atrributeName))
				return currentNode.getNodeValue();
		}
		return null;
	}

	/**
	 * 获得节点某个属性值
	 * 
	 * @param node
	 *          查找节点
	 * @param atrributeName
	 *          属性名称
	 * @return 属性值
	 */
	public static String getAtrr(Node node, String atrributeName) {
		if (node == null)
			return null;
		NamedNodeMap nodeList = node.getAttributes();
		Node currentNode = nodeList.getNamedItem(atrributeName);
		if (currentNode != null) {
			return currentNode.getNodeValue();
		} else
			return null;
	}

	/**
	 * 增加节点及值
	 * 
	 * @param node
	 * @param atrributeName
	 * @param newAtrributeValue
	 * @return
	 */
	public static boolean addAttribute(Document document, Node node, String path,
			String atrributeValue) {
		Node curreteNode = createSpecificNode(document, node, path);
		curreteNode.appendChild(document.createTextNode(atrributeValue));
		return true;
	}

	/**
	 * 更新属性值
	 * 
	 * @param node
	 * @param AtrributeName
	 * @param newAtrributeValue
	 * @return
	 */
	public static boolean updateAtrribute(Node node, String AtrributeName,
			String newAtrributeValue) {
		if (node == null)
			return false;
		Node currentNode = null;
		boolean flag = false;
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			currentNode = nodeList.item(i);
			if (currentNode.getNodeType() == 1
					&& currentNode.getNodeName().equals(AtrributeName)) {
				flag = true;
				currentNode.getFirstChild().setNodeValue(newAtrributeValue);
				break;
			}
		}
		if (flag)
			return true;
		else
			return false;
	}

	/**
	 * 将某一节点转储为文件
	 * 
	 * @param fileName:目标文件路径
	 * @param node:目标node
	 * @return
	 */
	public static boolean XML2File(String filePath, Node node) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			return false;
		}
		DOMSource source = new DOMSource(node);
		StreamResult result = new StreamResult(new java.io.File(filePath));
		try {
			transformer.transform(source, result);
		} catch (TransformerException e1) {
		}
		return true;
	}

	/**
	 * 将某一节点转为字符串
	 * 
	 * @param node:目标node
	 * @return String
	 */
	public static String XML2String(Node node) {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		StringWriter sw = new StringWriter();
		try {
			transformer = tFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
		}
		DOMSource source = new DOMSource(node);
		StreamResult result = new StreamResult(sw);
		try {
			transformer.transform(source, result);
		} catch (TransformerException e1) {
		}
		return new String(sw.getBuffer());
	}

	public static void main(String args[]) {
		Document root = createBlankDOM();
		Node node = root.createElement("ddd");
		Node ss = root.createElement("xxx");
		node.appendChild(ss);
		root.appendChild(node);

		System.out.print(XML2String(root));

	}
}