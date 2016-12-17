package parser;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import odeen.newrssreader.proj.model.Item;
import odeen.newrssreader.proj.parser.Parser;

/**
 * Created by dmitry on 17.12.16.
 */
public class ParserResponseTest {
    private static final String response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
            "  <channel>\n" +
            "    <language>ru</language>\n" +
            "    <title>Lenta.ru : Новости</title>\n" +
            "    <description>Новости, статьи, фотографии, видео. Семь дней в неделю, 24 часа в сутки</description>\n" +
            "    <link>http://lenta.ru</link>\n" +
            "    <image>\n" +
            "      <url>http://assets.lenta.ru/small_logo.png</url>\n" +
            "      <title>Lenta.ru</title>\n" +
            "      <link>http://lenta.ru</link>\n" +
            "      <width>134</width>\n" +
            "      <height>22</height>\n" +
            "    </image>\n" +
            "    <atom:link rel=\"self\" type=\"application/rss+xml\" href=\"http://lenta.ru/rss/news\"/>\n" +
            "<item>\n" +
            "  <guid>https://lenta.ru/news/2016/12/16/chocolate/</guid>\n" +
            "  <title>Терракотовую армию из шоколада показали в Китае</title>\n" +
            "  <link>https://lenta.ru/news/2016/12/16/chocolate/</link>\n" +
            "  <description>\n" +
            "    <![CDATA[В китайском городе Чанчунь (провинция Гирин) представили около 700 скульптур из шоколада. Среди них — уменьшенная копия знаменитой терракотовой армии, макет Эйфелевой башни, шоколадный Будда, музыкальные инструменты, чайники и обувь. Экспонаты изготовил французский художник Николай Попов.]]>\n" +
            "  </description>\n" +
            "  <pubDate>Sat, 17 Dec 2016 06:46:50 +0300</pubDate>\n" +
            "  <enclosure url=\"https://icdn.lenta.ru/images/2016/12/16/14/20161216142549150/pic_0f9f3730cc53de4f39d95beda272baec.jpg\" length=\"46153\" type=\"image/jpeg\"/>\n" +
            "  <category>Из жизни</category>\n" +
            "</item>\n" +
            "<item>\n" +
            "  <guid>https://lenta.ru/news/2016/12/17/accident/</guid>\n" +
            "  <title>Пять человек погибли в ДТП в Псковской области</title>\n" +
            "  <link>https://lenta.ru/news/2016/12/17/accident/</link>\n" +
            "  <description>\n" +
            "    <![CDATA[В Псковской области в результате ДТП погибли пять человек, еще восемь пострадали. Инцидент произошел около полуночи в субботу, 17 декабря. На 433-м километре автодороги Санкт-Петербург — Невель водитель автомашины Mazda выехал на встречную полосу и совершил столкновение с грузовиком Renault.]]>\n" +
            "  </description>\n" +
            "  <pubDate>Sat, 17 Dec 2016 05:33:00 +0300</pubDate>\n" +
            "  <enclosure url=\"https://icdn.lenta.ru/images/2016/12/17/05/20161217053703820/pic_5dd5c7e5c891ea71a0ce11572f13c13d.jpg\" length=\"81539\" type=\"image/jpeg\"/>\n" +
            "  <category>Россия</category>\n" +
            "</item>\n" +
            "</channel>\n" +
            "</rss>\n";

    @Test
    public void parseResponse() throws Exception {
        Parser parser = new Parser();
        List<Item> items = parser.parse(new ByteArrayInputStream(response.getBytes()));
        Assert.assertEquals(items.size(), 2);
        Assert.assertEquals(items.get(0).getLink(), "https://lenta.ru/news/2016/12/16/chocolate/");
        Assert.assertEquals(items.get(0).getTitle(), "Терракотовую армию из шоколада показали в Китае");
        Assert.assertEquals(items.get(1).getLink(), "https://lenta.ru/news/2016/12/17/accident/");
        Assert.assertEquals(items.get(1).getTitle(), "Пять человек погибли в ДТП в Псковской области");
    }
}
