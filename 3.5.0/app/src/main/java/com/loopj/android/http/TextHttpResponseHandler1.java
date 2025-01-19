package com.loopj.android.http;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public abstract class TextHttpResponseHandler1 extends AsyncHttpResponseHandler {

    private static final String LOG_TAG = "TextHttpRH";

    /**
     * Creates new instance with default UTF-8 encoding
     */
    public TextHttpResponseHandler1() {
        this(DEFAULT_CHARSET);
    }

    /**
     * Creates new instance with given string encoding
     *
     * @param encoding String encoding, see {@link #setCharset(String)}
     */
    public TextHttpResponseHandler1(String encoding) {
        super();
        setCharset(encoding);
    }

    /**
     * Attempts to encode response bytes as string of set encoding
     *
     * @param charset     charset to create string with
     * @param stringBytes response bytes
     * @return String of set encoding or null
     */
    public static String getResponseString(byte[] stringBytes, String charset) {
        try {
            String toReturn = (stringBytes == null) ? null : new String(stringBytes, charset);
            if (toReturn != null && toReturn.startsWith(UTF8_BOM)) {
                return decodeString(toReturn.substring(1));
            }
            return decodeString(toReturn);
        } catch (UnsupportedEncodingException e) {
            AsyncHttpClient.log.e(LOG_TAG, "Encoding response into string failed", e);
            return null;
        }
    }

    /**
     * Called when request fails
     *
     * @param statusCode     http response status line
     * @param headers        response headers if any
     * @param responseString string response of given charset
     * @param throwable      throwable returned when processing request
     */
    public abstract void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable);

    /**
     * Called when request succeeds
     *
     * @param statusCode     http response status line
     * @param headers        response headers if any
     * @param responseString string response of given charset
     */
    public abstract void onSuccess(int statusCode, Header[] headers, String responseString);

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
        onSuccess(statusCode, headers, getResponseString(responseBytes, getCharset()));
    }

    public static String decodeString(String strData) {
        if (strData == null) {
            return "";
        }
        return strData.replaceAll("&amp;", "&").toString();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
        onFailure(statusCode, headers, getResponseString(responseBytes, getCharset()), throwable);
    }
}
