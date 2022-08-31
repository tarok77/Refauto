package com.tarok.quotegenerator.Service;

import com.tarok.quotegenerator.Repository.Book;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

@Component
public class BookSetter {
    //filterを使わないならEventReaderである必要はない
    public void createBookFromEventReader(XMLEventReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            //受け渡しはイベントのほうがいいかも
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                StartElement el = event.asStartElement();
                if(el.getName().getLocalPart().equals("title")) {
                    var book = new Book();
                }
                if (el.getName().getLocalPart().equals("creator")) {
                    event = reader.nextEvent();
                    if (event.isCharacters()) {
                        System.out.println("作者は" + event.asCharacters());
                    }
                }

                //getLocalNameではdc:titleも取得されてしまう
                if (el.getName().toString().equals("title")) {
                    event = reader.nextEvent();
                    if (event.isCharacters()) System.out.println("タイトルは" + event.asCharacters().getData());
                }
            }
        }
    }
}
//{http://purl.org/dc/elements/1.1/}creator http://purl.org/dc/elements/1.1/ com.sun.org.apache.xerces.internal.util.NamespaceContextWrapper@2eab3090com.sun.xml.internal.stream.util.ReadOnlyIterator@2b7b4b2
//        作者はThomas, David, 1956-
//el.getNamespaceURI("dc")
//el.getName().getPrefix　これで接頭辞がとれる