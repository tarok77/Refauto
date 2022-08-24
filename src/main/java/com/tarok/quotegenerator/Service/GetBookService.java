package com.tarok.quotegenerator.Service;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.Iterator;

public class GetBookService {
    OkhttpForKokkaiApi okhttp = new OkhttpForKokkaiApi();
//TODO　Xpathの生成、Namespaceの設定とnodeからの情報の取り出しを分けること
    public void makeXpathAndGetName(String isbn) throws XPathExpressionException, IOException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        NamespaceContext ctx = new NamespaceContext() {
            public String getNamespaceURI(String prefix) {
                return prefix.equals("dc") ? "http://purl.org/dc/elements/1.1/" : null;
            }

            public Iterator<String> getPrefixes(String uri) {
                return null;
            }

            public String getPrefix(String uri) {
                return null;
            }
        };
        xpath.setNamespaceContext(ctx);
        XPathExpression expr = xpath.compile("//dc:creator/text()");

        Document doc = okhttp.getDocumentFromKokkai(isbn);
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        System.out.println(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println("number" + i + "は" + nodes.item(i).getNodeValue());
        }

    }
}
