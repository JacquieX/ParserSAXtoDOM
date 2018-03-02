/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsersaxtodom;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Shihui Xiong
 */
public class xmlLoader {

    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static Code load(File file) throws Exception {
        Code initCode = new Code();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                int depth = 0;
                ArrayList<Code> codes = new ArrayList<Code>();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    Code code = null;
                    if (depth == 0) {
                        code = initCode;
                    } else {
                        code = new Code();
                        codes.get(depth - 1).contains.add(code);
                    }
                    codes.add(depth, code);
                    code.tag = qName;
                    depth += 1;
                    for (int i = 0; i < attributes.getLength(); i++) {
                        Attri attr = new Attri();
                        attr.id = attributes.getLocalName(i);
                        attr.value = attributes.getValue(i);
                        code.attrs.add(attr);
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    depth -= 1;
                    codes.remove(depth);
                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    codes.get(depth - 1).content = new String(ch, start, length);
                }
            };

            saxParser.parse(file.getAbsoluteFile(), handler);

        } catch (Exception ex) {
            throw ex;
        }

        return initCode;
    }
}
    

