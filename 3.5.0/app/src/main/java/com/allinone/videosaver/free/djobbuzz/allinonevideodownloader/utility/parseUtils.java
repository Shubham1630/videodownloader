package com.allinone.videosaver.free.djobbuzz.allinonevideodownloader.utility;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.Parser;

import org.jsoup.parser.Token;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TreeBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class parseUtils {


    private static final String TAG ="parseUtils" ;

    public static Document parsefburls(String str) {
        TreeBuilder bVar = new HtmlTreeBuilder();
        bVar.initialiseParse(new StringReader(str), "", new Parser(bVar));
        Tokeniser kVar = bVar.tokeniser;
        Token.TokenType jVar = Token.TokenType.EOF;

        while (true) {
            if (kVar.isEmitPending) {
                Token iVar;
                StringBuilder stringBuilder = kVar.charsBuilder;
                if (stringBuilder.length() != 0) {
                    String stringBuilder2 = stringBuilder.toString();
                    stringBuilder.delete(0, stringBuilder.length());
                    kVar.charsString = null;
                    iVar = kVar.emitPending;
                    // TODO: 3/12/2021 fixme 
                    iVar.asCharacter().data = stringBuilder2;
                } else {
                    String str2 = kVar.charsString;
                    if (str2 != null) {
                        Token iVar2 = kVar.emitPending;
                        //fixme
                        Log.e(TAG, "parsefburls: str2:"+str2 );
                      //  iVar2.asCharacter().data = str2;
                        iVar2.asStartTag().normalName = str2;
                        kVar.charsString = null;
                        iVar = iVar2;
                    } else {
                        kVar.isEmitPending = false;
                        iVar = kVar.emitPending;
                    }
                }
                Log.e(TAG, "parsefburls: iVar:"+iVar.toString() );
                bVar.process(iVar);
                iVar.reset();
                if (iVar.type == jVar) {
                    break;
                }
            } else {
                kVar.state.read(kVar, kVar.reader);
            }
        }
        CharacterReader aVar = bVar.reader;
        Reader reader = aVar.reader;
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
                aVar.reader = null;
                aVar.charBuf = null;
                aVar.stringCache = null;
            }
            aVar.reader = null;
            aVar.charBuf = null;
            aVar.stringCache = null;
        }
        bVar.reader = null;
        bVar.tokeniser = null;
        bVar.stack = null;
        return bVar.doc;
    }



}
