package com.bigdata.locationweibo.ui.util;

import java.awt.TextComponent;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.SwingUtilities;

public class GUIPrintStream extends PrintStream {
    private TextComponent component;
    private StringBuffer sb;
    
    public GUIPrintStream(OutputStream out, TextComponent component){
        super(out);
        this.component = component;
        this.sb = new StringBuffer();
    }
    
    @Override
    public void write(byte[] buf, int off, int len) {
        final String message = new String(buf, off, len);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                sb.append(message);
                component.setText(sb.toString());
            }
        });
    }
}  
