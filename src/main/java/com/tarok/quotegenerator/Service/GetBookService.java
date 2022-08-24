package com.tarok.quotegenerator.Service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.Iterator;

@Service
public class GetBookService {
    OkhttpForKokkaiApi okhttp = new OkhttpForKokkaiApi();

    //TODO　Xpathの生成、Namespaceの設定とnodeからの情報の取り出しを分けること
    public XPathExpression makeXpathAndSetNameSpace() throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        NamespaceContext ctx = new NamespaceContext() {
            public String getNamespaceURI(String prefix) {
                if (prefix.equals("dc")) return "http://purl.org/dc/elements/1.1/";
                if (prefix.equals("dcterms")) return "http://purl.org/dc/terms/";
                if (prefix.equals("rdf")) return "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
                if (prefix.equals("rdfs")) return "http://www.w3.org/2000/01/rdf-schema#";
                if (prefix.equals("dcndl")) return "http://ndl.go.jp/dcndl/terms/";
                if (prefix.equals("foaf")) return "http://xmlns.com/foaf/0.1/";
                if (prefix.equals("owl")) return "http://www.w3.org/2002/07/owl#";

                return null;
            }

            public Iterator<String> getPrefixes(String uri) {
                return null;
            }

            public String getPrefix(String uri) {
                return null;
            }
        };
        xpath.setNamespaceContext(ctx);
        XPathExpression expr = xpath.compile("//dc:creator/text()");//rdf:value <dc:title><rdf:Description>
                                    ////dc:title/rdf:Description/rdf:value/text()
        return expr;

//        Document doc = okhttp.getDocumentFromKokkai(isbn);
//        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//        System.out.println(nodes.getLength());
//        for (int i = 0; i < nodes.getLength(); i++) {
//            System.out.println("number" + i + "は" + nodes.item(i).getNodeValue());
//        }

    }

    public NodeList getNodesByISBN(String isbn) throws IOException, XPathExpressionException {
        Document doc = okhttp.getDocumentFromKokkai(isbn);
        XPathExpression expr = makeXpathAndSetNameSpace();
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        System.out.println(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println("number" + i + "は" + nodes.item(i).getNodeValue());
        }
        return nodes;
    }
}
