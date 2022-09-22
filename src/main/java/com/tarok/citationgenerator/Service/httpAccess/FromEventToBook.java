package com.tarok.citationgenerator.Service.httpAccess;

import com.tarok.citationgenerator.Repository.RawBook;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
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
@Slf4j
public class FromEventToBook {
    //filterを使わないならEventReaderである必要はない
    public List<RawBook> createBookFromEventReader(XMLEventReader reader) throws XMLStreamException {
        //詰めて戻り値にするためのブックリスト
        List<RawBook> bookList = new ArrayList<>();
        //xmlが破損しているときのnullPointerException対策で空のインスタンスをいれておく
        RawBook rawBook = new RawBook();

        while (reader.hasNext()) {
            //受け渡しはイベントのほうがいいかも
            XMLEvent event = reader.nextEvent();
            //StartElementがrecordの時に新しいインスタンスを生成、
            //タグの内容に応じて本文を格納していき、EndElementがrecordのときにリストに収納する。
            if (event.isStartElement()) {
                StartElement el = event.asStartElement();
                String prefix = el.getName().getPrefix();
                String localName = el.getName().getLocalPart();

                if (localName.equals("record")) {
                    rawBook = new RawBook();
                }

                if (localName.equals("identifier")) {
                    Iterator<Attribute> attributes = el.getAttributes();
                    while (attributes.hasNext()) {
                        if (attributes.next().getValue().equals("http://ndl.go.jp/dcndl/terms/ISBN")) {
                            event = reader.nextEvent();
                        }
                    }
                    //上記の条件を満たしたときだけeventが進み以下の条件を満たす
                    if (event.isCharacters()) {
                        rawBook.setIsbn(event.asCharacters()
                                .getData().replaceAll("-",""));
                    }
                }

                if (prefix.equals("dc") && localName.equals("title")) {
                    //直前のタグでは確定できないため二つ外のdc:titleを参考にしているためそこまで飛ばす
                    reader.nextTag();
                    reader.nextTag();
                    event = reader.nextEvent();
                    if (event.isCharacters()) {
                        rawBook.setTitle(event.asCharacters().getData());
                    }
                }

                if (prefix.equals("dcterms") && localName.equals("publisher")) {
                    //直前のタグでは確定できないため二つ外のdcterms:pulisherを参考にしているためそこまで飛ばす
                    reader.nextTag();
                    reader.nextTag();
                    event = reader.nextEvent();
                    if (event.isCharacters()) {
                        rawBook.setPublisher(event.asCharacters().getData());
                    }
                }

                if (prefix.equals("dc") && localName.equals("creator")) {
                    event = reader.nextEvent();
                    if (event.isCharacters()) {
                        //「スペース + 著」という名前が「著」だけを含むデータと重複して返ってくるためあとで排除するための前処理
                        String creator = event.asCharacters().getData().replaceAll("　著| 著", "著");
                        rawBook.addCreatorList(creator);
                    }
                }

                if (prefix.equals("dcterms") && localName.equals("date")) {
                    event = reader.nextEvent();
                    //TODO 不正値が多いため対策が必要
                    if (event.isCharacters()) {
                        rawBook.setPublishedYearAndMonth(event.asCharacters().getData());
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
        log.info(String.valueOf(bookList.size()));
        //重複データが多く帰ってくるのでISBNと出版年月がかぶっているものを削除
        var onlyOneBookList = bookList.stream().distinct().toList();
        return onlyOneBookList;
    }
}
//{http://purl.org/dc/elements/1.1/}creator http://purl.org/dc/elements/1.1/ com.sun.org.apache.xerces.internal.util.NamespaceContextWrapper@2eab3090com.sun.xml.internal.stream.util.ReadOnlyIterator@2b7b4b2
//        作者はThomas, David, 1956-
//el.getNamespaceURI("dc")
//el.getName().getPrefix　これで接頭辞がとれる