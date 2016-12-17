package odeen.newrssreader.proj.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import odeen.newrssreader.proj.model.Item;

interface IParser {
    public List<Item> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException;
    public Item readItem(XmlPullParser parser) throws IOException, XmlPullParserException;
    public void skip(XmlPullParser parser) throws XmlPullParserException, IOException;
}
