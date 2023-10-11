package org.example.Exceptions;

public class QueueOverflowException extends Exception{
    public QueueOverflowException(String message) {
        super(message);
    }
}
