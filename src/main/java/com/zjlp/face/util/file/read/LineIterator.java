package com.zjlp.face.util.file.read; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 文件读取工具类
 * @ClassName: LineIterator 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年3月5日 下午4:30:57
 */
public class LineIterator implements Iterator<List<String>> {

    private BufferedReader bufferedReader = null;
    
    private List<String> cachedLines;
    
    private boolean finished = false;
    
    private int maxLineCount;
    
    public LineIterator(final Reader reader, int cachedLinesSize) throws IllegalArgumentException {
        if (null == reader) {
            throw new IllegalArgumentException("Reader can not be null.");
        }
        if (0 >= cachedLinesSize) {
            throw new IllegalArgumentException("Cashed line's size must more than 0.");
        }
        if (reader instanceof BufferedReader) {
            bufferedReader = (BufferedReader)reader; 
        } else {
            bufferedReader = new BufferedReader(reader);
        }
        maxLineCount = cachedLinesSize;
        cachedLines = new ArrayList<String>(cachedLinesSize);
    }
    
    public boolean hasNext() {
        if (!cachedLines.isEmpty()) {
            return true;
        } else if (finished) {
            return false;
        } else {
            try {
                String line = null;
                while (true) {
                    line = bufferedReader.readLine();
                    if (isVaildLine(line)) {
                         cachedLines.add(line);
                    } else {
                         finished = true;
                         break;
                    }
                    if (maxLineCount == cachedLines.size()) {
                        break;
                    }
                }
                return !cachedLines.isEmpty();
            } catch(IOException ioe) {
                close();
                throw new IllegalStateException(ioe);
            }
        }
    }

    public List<String> next() {
        if (cachedLines.isEmpty()) {
            throw new NoSuchElementException("No more lines.");
        }
        List<String> lines = new ArrayList<String>(cachedLines);
        cachedLines.clear();
        return lines;
    }

    public void remove() {
        throw new UnsupportedOperationException("Remove unsupported on LineIterator");
    }
    
    protected boolean isVaildLine(String line) {
        if (null == line) {
            return false;
        }
        return true;
    }
    
    private void close() {
        try {
            if (null != bufferedReader) {
                bufferedReader.close();
            }
        } catch (Exception e) {
            //ignore
        }
    }
    
    public static void closeQuietly(LineIterator iterator) {
        if (iterator != null) {
            iterator.close();
        }
    }
    
}

