package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

public final class StringUtils {
    public StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String decode(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int length = str.length();
        int i = 0;
        while (i < length) {
            if (str.charAt(i) == '\\') {
                if (i < length - 5) {
                    int i2 = i + 1;
                    if (str.charAt(i2) == 'u' || str.charAt(i2) == 'U') {
                        try {
                            stringBuffer.append((char) Integer.parseInt(str.substring(i + 2, i + 6), 16));
                            i += 5;
                        } catch (NumberFormatException unused) {
                            stringBuffer.append(str.charAt(i));
                        }
                    }
                }
                stringBuffer.append(str.charAt(i));
            } else {
                stringBuffer.append(str.charAt(i));
            }
            i++;
        }
        return stringBuffer.toString();
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return true;
        }
        if (!(charSequence == null || charSequence2 == null)) {
            int length = charSequence.length();
            if (length == charSequence2.length()) {
                if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
                    return charSequence.equals(charSequence2);
                }
                for (int i = 0; i < length; i++) {
                    if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean equalsIgnoreCase(String str, String str2) {
        if (str == null) {
            return str2 == null;
        } else {
            return str.equalsIgnoreCase(str2);
        }
    }



    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }



    public static int length(CharSequence charSequence) {
        return charSequence == null ? 0 : charSequence.length();
    }









}
