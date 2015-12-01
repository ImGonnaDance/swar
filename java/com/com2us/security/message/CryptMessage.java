package com.com2us.security.message;

import com.com2us.security.cryptography.StringEncrypter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import jp.co.cyberz.fox.a.a.i;

public class CryptMessage {
    static final String ENCODING = "UTF-8";
    Properties properties = new Properties();

    public Properties getProperties() {
        return this.properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        if (this.properties == null) {
            return null;
        }
        return this.properties.getProperty(key);
    }

    public Object setProperty(String key, String value) {
        if (this.properties == null) {
            this.properties = new Properties();
        }
        return this.properties.setProperty(key, value);
    }

    public String getMessage(String systemId, String password, long timeoutSeconds) throws MessageCryptException {
        return getMessage(systemId, password, timeoutSeconds, true);
    }

    public void setMessage(String systemId, String password, String message) throws MessageTimeoutException, MessageCryptException {
        setMessage(systemId, password, message, true);
    }

    public String getMessage(String systemId, String password, long timeoutSeconds, boolean isUrl) throws MessageCryptException {
        setProperty("utcexpire", Long.toString((Calendar.getInstance().getTimeInMillis() / 1000) + timeoutSeconds));
        StringBuffer buffer = new StringBuffer();
        try {
            Enumeration<?> enumeration = this.properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = this.properties.getProperty(key);
                if (value == null) {
                    value = i.a;
                }
                if (buffer.length() > 0) {
                    buffer.append("&");
                }
                buffer.append(URLEncoder.encode(key, ENCODING)).append("=").append(URLEncoder.encode(value, ENCODING));
            }
            String encrypted = new StringEncrypter(systemId, password).encrypt(buffer.toString());
            if (isUrl) {
                return encrypted.replaceAll("\\+", "-").replaceAll("\\/", "_").replaceAll("\\=", i.a);
            }
            return encrypted;
        } catch (Exception e) {
            throw new MessageCryptException("Encryption error", e);
        }
    }

    public void setMessage(String systemId, String password, String message, boolean isUrl) throws MessageTimeoutException, MessageCryptException {
        if (isUrl) {
            message = message.replaceAll("\\-", "+").replaceAll("\\_", "/");
            int pad = Math.abs((message.length() % 4) - 4);
            if (pad == 4) {
                pad = 0;
            }
            int i = 0;
            while (i < pad) {
                try {
                    message = new StringBuilder(String.valueOf(message)).append("=").toString();
                    i++;
                } catch (Exception e) {
                    throw new MessageCryptException("Decryption error", e);
                }
            }
        }
        StringTokenizer stringTokenizer = new StringTokenizer(new StringEncrypter(systemId, password).decrypt(message), "&");
        while (stringTokenizer.hasMoreTokens()) {
            String key;
            String value;
            String token = stringTokenizer.nextToken();
            int delimeterIndex = token.indexOf(61);
            if (delimeterIndex != -1) {
                key = token.substring(0, delimeterIndex);
                if (delimeterIndex + 1 < token.length()) {
                    value = token.substring(delimeterIndex + 1);
                } else {
                    value = i.a;
                }
            } else {
                key = token;
                value = i.a;
            }
            setProperty(URLDecoder.decode(key, ENCODING), URLDecoder.decode(value, ENCODING));
        }
        String expire = getProperty("utcexpire");
        if (expire == null || expire.length() == 0) {
            throw new MessageTimeoutException("Message has no expire time");
        }
        try {
            long longExpire = Long.parseLong(expire);
            Calendar currentTime = Calendar.getInstance();
            Calendar expireTime = Calendar.getInstance();
            expireTime.setTimeInMillis(1000 * longExpire);
            if (currentTime.after(expireTime)) {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                throw new MessageTimeoutException("Current time (" + dateformat.format(currentTime.getTime()) + ") is after expire time (" + dateformat.format(expireTime.getTime()) + ")");
            }
        } catch (Exception e2) {
            throw new MessageTimeoutException("Invalid expire time", e2);
        }
    }
}
