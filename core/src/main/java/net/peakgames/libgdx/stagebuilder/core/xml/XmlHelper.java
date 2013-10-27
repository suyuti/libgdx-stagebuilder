package net.peakgames.libgdx.stagebuilder.core.xml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlHelper {

	public static float readFloatAttribute(XmlPullParser parser, String attributeName, float defaultValue) {
		String sValue = parser.getAttributeValue(null, attributeName);
		if (sValue == null) {
			return defaultValue;
		}
		return Float.valueOf(sValue);
	}

	public static int readIntAttribute(XmlPullParser parser, String attributeName, int defaultValue) {
		String sValue = parser.getAttributeValue(null, attributeName);
		if (sValue == null) {
			return defaultValue;
		}
		return Integer.valueOf(sValue);
	}

	public static String readStringAttribute(XmlPullParser parser, String attributeName) {
		return parser.getAttributeValue(null, attributeName);
	}
	
	public static String readStringAttribute(XmlPullParser parser, String attributeName, String defaultValue) {
		String sValue = readStringAttribute(parser, attributeName);
		if(sValue == null) {
			return defaultValue;
		}
		return sValue;
	}

    public static boolean readBooleanAttribute(XmlPullParser parser, String attributeName, boolean defaultValue) {
        String sValue = readStringAttribute(parser, attributeName);
        if(sValue == null) {
            return defaultValue;
        }
        return Boolean.valueOf(sValue);
    }



    public static FileHandle readFileAttribute(XmlPullParser parser, String attributeName, String filePath) {
		String sValue = parser.getAttributeValue(null, attributeName);
		if(sValue == null) {
			return Gdx.files.internal(filePath);
		}
		return Gdx.files.internal(sValue);
	}
	
	public static XmlPullParser getXmlParser(FileHandle fileHandle) {
		try {
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(fileHandle.read(), null);
			return parser;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
