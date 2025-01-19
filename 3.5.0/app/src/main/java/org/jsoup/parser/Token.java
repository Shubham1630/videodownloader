package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;

import static org.jsoup.internal.Normalizer.lowerCase;

/**
 * Parse tokens for the Tokeniser.
 */
public abstract class Token {
    public  TokenType type;

    private Token() {
    }
    
    String tokenType() {
        return this.getClass().getSimpleName();
    }

    /**
     * Reset the data represent by this token, for reuse. Prevents the need to create transfer objects for every
     * piece of data, which immediately get GCed.
     */
    public abstract Token reset();

    public static void reset(StringBuilder sb) {
        if (sb != null) {
            sb.delete(0, sb.length());
        }
    }

    public static final class Doctype extends Token {
        final StringBuilder name = new StringBuilder();
        String pubSysKey = null;
        final StringBuilder publicIdentifier = new StringBuilder();
        final StringBuilder systemIdentifier = new StringBuilder();
        boolean forceQuirks = false;

        Doctype() {
            type = TokenType.Doctype;
        }

        @Override
        public  Token reset() {
            reset(name);
            pubSysKey = null;
            reset(publicIdentifier);
            reset(systemIdentifier);
            forceQuirks = false;
            return this;
        }

        public String getName() {
            return name.toString();
        }

        public   String getPubSysKey() {
            return pubSysKey;
        }

        public  String getPublicIdentifier() {
            return publicIdentifier.toString();
        }

        public String getSystemIdentifier() {
            return systemIdentifier.toString();
        }

        public boolean isForceQuirks() {
            return forceQuirks;
        }
    }

    public static abstract class Tag extends Token {
        public String tagName;
        public String normalName; // lc version of tag name, for case insensitive tree build
        public String pendingAttributeName; // attribute names are generally caught in one hop, not accumulated
        public StringBuilder pendingAttributeValue = new StringBuilder(); // but values are accumulated, from e.g. & in hrefs
        public String pendingAttributeValueS; // try to get attr vals in one shot, vs Builder
        public boolean hasEmptyAttributeValue = false; // distinguish boolean attribute from empty string value
        public boolean hasPendingAttributeValue = false;
        public  boolean selfClosing = false;
        Attributes attributes; // start tags get attributes on construction. End tags get attributes on first new attribute (but only for parser convenience, not used).

        @Override
        public Tag reset() {
            tagName = null;
            normalName = null;
            pendingAttributeName = null;
            reset(pendingAttributeValue);
            pendingAttributeValueS = null;
            hasEmptyAttributeValue = false;
            hasPendingAttributeValue = false;
            selfClosing = false;
            attributes = null;
            return this;
        }

        public   final void newAttribute() {
            if (attributes == null)
                attributes = new Attributes();

            if (pendingAttributeName != null) {
                // the tokeniser has skipped whitespace control chars, but trimming could collapse to empty for other control codes, so verify here
                pendingAttributeName = pendingAttributeName.trim();
                if (pendingAttributeName.length() > 0) {
                    String value;
                    if (hasPendingAttributeValue)
                        value = pendingAttributeValue.length() > 0 ? pendingAttributeValue.toString() : pendingAttributeValueS;
                    else if (hasEmptyAttributeValue)
                        value = "";
                    else
                        value = null;
                    // note that we add, not put. So that the first is kept, and rest are deduped, once in a context where case sensitivity is known (the appropriate tree builder).
                    attributes.add(pendingAttributeName, value);
                }
            }
            pendingAttributeName = null;
            hasEmptyAttributeValue = false;
            hasPendingAttributeValue = false;
            reset(pendingAttributeValue);
            pendingAttributeValueS = null;
        }

        final boolean hasAttributes() {
            return attributes != null;
        }

        final boolean hasAttribute(String key) {
            return attributes != null && attributes.hasKey(key);
        }

        final void finaliseTag() {
            // finalises for emit
            if (pendingAttributeName != null) {
                newAttribute();
            }
        }

        /** Preserves case */
        final String name() { // preserves case, for input into Tag.valueOf (which may drop case)
            Validate.isFalse(tagName == null || tagName.length() == 0);
            return tagName;
        }

        /** Lower case */
        final String normalName() { // lower case, used in tree building for working out where in tree it should go
            return normalName;
        }

        final String toStringName() {
            return tagName != null ? tagName : "[unset]";
        }

        final Tag name(String name) {
            tagName = name;
            normalName = lowerCase(name);
            return this;
        }

        final boolean isSelfClosing() {
            return selfClosing;
        }

        // these appenders are rarely hit in not null state-- caused by null chars.
        final void appendTagName(String append) {
            tagName = tagName == null ? append : tagName.concat(append);
            normalName = lowerCase(tagName);
        }

        final void appendTagName(char append) {
            appendTagName(String.valueOf(append));
        }

        final void appendAttributeName(String append) {
            pendingAttributeName = pendingAttributeName == null ? append : pendingAttributeName.concat(append);
        }

        final void appendAttributeName(char append) {
            appendAttributeName(String.valueOf(append));
        }

        final void appendAttributeValue(String append) {
            ensureAttributeValue();
            if (pendingAttributeValue.length() == 0) {
                pendingAttributeValueS = append;
            } else {
                pendingAttributeValue.append(append);
            }
        }

        final void appendAttributeValue(char append) {
            ensureAttributeValue();
            pendingAttributeValue.append(append);
        }

        final void appendAttributeValue(char[] append) {
            ensureAttributeValue();
            pendingAttributeValue.append(append);
        }

        final void appendAttributeValue(int[] appendCodepoints) {
            ensureAttributeValue();
            for (int codepoint : appendCodepoints) {
                pendingAttributeValue.appendCodePoint(codepoint);
            }
        }
        
        final void setEmptyAttributeValue() {
            hasEmptyAttributeValue = true;
        }

        private void ensureAttributeValue() {
            hasPendingAttributeValue = true;
            // if on second hit, we'll need to move to the builder
            if (pendingAttributeValueS != null) {
                pendingAttributeValue.append(pendingAttributeValueS);
                pendingAttributeValueS = null;
            }
        }

        @Override
        abstract public String toString();
    }

    public  final static class StartTag extends Tag {
        public   StartTag() {
            super();
            type = TokenType.StartTag;
        }

        @Override
        public   Tag reset() {
            super.reset();
            attributes = null;
            return this;
        }

        public  StartTag nameAttr(String name, Attributes attributes) {
            this.tagName = name;
            this.attributes = attributes;
            normalName = lowerCase(tagName);
            return this;
        }

        @Override
        public String toString() {
            if (hasAttributes() && attributes.size() > 0)
                return "<" + toStringName() + " " + attributes.toString() + ">";
            else
                return "<" + toStringName() + ">";
        }
    }

    public final static class EndTag extends Tag{
        public   EndTag() {
            super();
            type = TokenType.EndTag;
        }

        @Override
        public String toString() {
            return "</" + toStringName() + ">";
        }
    }

    public final static class Comment extends Token {
        public final StringBuilder data = new StringBuilder();
        public String dataS; // try to get in one shot
        public boolean bogus = false;

        @Override
        public  Token reset() {
            reset(data);
            dataS = null;
            bogus = false;
            return this;
        }

        Comment() {
            type = TokenType.Comment;
        }

        String getData() {
            return dataS != null ? dataS : data.toString();
        }

        final Comment append(String append) {
            ensureData();
            if (data.length() == 0) {
                dataS = append;
            } else {
                data.append(append);
            }
            return this;
        }

        public   final Comment append(char append) {
            ensureData();
            data.append(append);
            return this;
        }

        public void ensureData() {
            // if on second hit, we'll need to move to the builder
            if (dataS != null) {
                data.append(dataS);
                dataS = null;
            }
        }

        @Override
        public String toString() {
            return "<!--" + getData() + "-->";
        }
    }




    public static class c extends Token {
        public static String b;



        @Override
        public Token reset() {
            return null;
        }



        public String toString() {
            return this.b;
        }
    }

    public  static class Character extends Token {
        public String data;

        public  Character() {
            super();
            type = TokenType.Character;
        }

        @Override
        public   Token reset() {
            data = null;
            return this;
        }

        public  Character data(String data) {
            this.data = data;
            return this;
        }

        public  String getData() {
            return data;
        }

        @Override
        public String toString() {
            return getData();
        }
    }

    final static class CData extends Character {
        CData(String data) {
            super();
            this.data(data);
        }

        @Override
        public String toString() {
            return "<![CDATA[" + getData() + "]]>";
        }

    }

    public  final static class EOF extends Token {
        EOF() {
            type = TokenType.EOF;
        }

        @Override
        public   Token reset() {
            return this;
        }

        @Override
        public String toString() {
            return "";
        }
    }

    public  final boolean isDoctype() {
        return type == TokenType.Doctype;
    }

    public  final Doctype asDoctype() {
        return (Doctype) this;
    }

    public final boolean isStartTag() {
        return type == TokenType.StartTag;
    }

    public final StartTag asStartTag() {
        return (StartTag) this;
    }

    public  final boolean isEndTag() {
        return type == TokenType.EndTag;
    }

    public  final EndTag asEndTag() {
        return (EndTag) this;
    }

    public  final boolean isComment() {
        return type == TokenType.Comment;
    }

    public  final Comment asComment() {
        return (Comment) this;
    }

    public  final boolean isCharacter() {
        return type == TokenType.Character;
    }

    public final boolean isCData() {
        return this instanceof CData;
    }

    public  final Character asCharacter() {
        return (Character) this;
    }

    public  final boolean isEOF() {
        return type == TokenType.EOF;
    }

    public enum TokenType {
        Doctype,
        StartTag,
        EndTag,
        Comment,
        Character, // note no CData - treated in builder as an extension of Character
        EOF
    }
}
