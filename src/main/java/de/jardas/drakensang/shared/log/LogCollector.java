package de.jardas.drakensang.shared.log;

import ch.qos.logback.core.WriterAppender;

import java.io.StringWriter;


public class LogCollector<E> extends WriterAppender<E> {
    private static final StringWriter BUFFER = new StringWriter();

    @Override
    public void start() {
        setWriter(BUFFER);
        super.start();
    }

    public static String getLog() {
        return BUFFER.toString();
    }
}
