package com.tarok.quotegenerator.Service;

import com.tarok.quotegenerator.Repository.Book;
import com.tarok.quotegenerator.Repository.RawBook;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class BookGetter {
    //filterを使わないならEventReaderである必要はない
    public void createBookFromEventReader(XMLEventReader reader) throws XMLStreamException {
        //詰めて戻り値にするためのブックリスト
        List<RawBook> bookList = new ArrayList<>();
        //xmlが破損しているときのnullPointerException対策でインスタンスはいれておく
        RawBook rawBook = new RawBook();

        while (reader.hasNext()) {
            //受け渡しはイベントのほうがいいかも
            XMLEvent event = reader.nextEvent();
            //StartElementがrecordの時に新しいインスタンスを生成、
            //タグの内容に応じて本文を格納していき、EndElementがrecordのときにリストに収納する。
            if (event.isStartElement()) {
                StartElement el = event.asStartElement();
                String localName = el.getName().getLocalPart();
                String prefix = el.getName().getPrefix();
                if (localName.equals("record")) {
                    rawBook = new RawBook();//.setdefault
                }
                //TODO データタイプの設定が必要
                if (localName.equals("identifier")) {
                    Iterator<Attribute> attributes = el.getAttributes();
                    while (attributes.hasNext()) {
                        if (attributes.next().getValue().equals("http://ndl.go.jp/dcndl/terms/ISBN")) {
                            event = reader.nextEvent();
                        }
                    }
                    //上記の条件を満たしたときだけeventが進み以下の条件を満たす
                    if (event.isCharacters()) {
                        rawBook.setIsbn(event.asCharacters().getData());
                    }
                }

                if (prefix.equals("dcterms") && localName.equals("title")) {
                    event = reader.nextEvent();
                    if (event.isCharacters()) {
                        rawBook.setTitle(event.asCharacters().getData());
                    }
                }

                if(prefix.equals("dcterms") && localName.equals("publisher")) {
                    //直前のタグでは確定できないため二つ外のdcterms:pulisherを参考にしているためそこまで飛ばす
                    reader.nextTag(); reader.nextTag();
                    event = reader.nextEvent();
                    if(event.isCharacters()) {
                        rawBook.setPublisher(event.asCharacters().getData());
                    }
                }

                if(prefix.equals("dc") && localName.equals("creator")) {
                    event = reader.nextEvent();
                    if(event.isCharacters()) {
                        rawBook.addCreatorList(event.asCharacters().getData());
                    }
                }

                if(prefix.equals("dcterms") && localName.equals("date")) {
                    event = reader.nextEvent();
                    if(event.isCharacters()) {
                        rawBook.setPublishedYear(event.asCharacters().getData());
                    }
                }
            }
            if (event.isEndElement()) {
                EndElement el = event.asEndElement();
                String prefix = el.getName().getPrefix();
                String localPart = el.getName().getLocalPart();

                if (!prefix.equals("dcndl") && localPart.equals("record")) {
                    bookList.add(rawBook);
                }
            }
        }
        System.out.println(bookList.size());
        bookList.stream().map(Book::format).forEach(System.out::println);
    }
}
//{http://purl.org/dc/elements/1.1/}creator http://purl.org/dc/elements/1.1/ com.sun.org.apache.xerces.internal.util.NamespaceContextWrapper@2eab3090com.sun.xml.internal.stream.util.ReadOnlyIterator@2b7b4b2
//        作者はThomas, David, 1956-
//el.getNamespaceURI("dc")
//el.getName().getPrefix　これで接頭辞がとれる