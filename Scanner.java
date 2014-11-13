// Scanner.java -- the implementation of class Scanner

import java.io.*;

class Scanner {

    private PushbackInputStream in;
    private byte[] buf = new byte[1000];

    public Scanner(InputStream i) {
        in = new PushbackInputStream(i);
    }

    public Token getNextToken() {
        int bite = -1;
        boolean shouldSkip;
        boolean commentDetected = false;
        char ch;

        // It would be more efficient if we'd maintain our own input buffer
        // and read characters out of that buffer, but reading individual
        // characters from the input stream is easier.

        do {
            shouldSkip = false;
            try {
                bite = in.read();
            } catch (IOException e) {
                System.err.println("We fail: " + e.getMessage());
                return null;
            }

            if (bite == -1) {
                return null;
            }

            ch = (char) bite;

            //skips whitespace characters
            switch (ch) {
                case '\n':
                    commentDetected = false;
                case ' ':
                case '\t':
                case '\r':
                case '\f':
                    shouldSkip = true;
                    break;
                case ';':
                    commentDetected = true;

                default:
                    break;
            }
        } while (shouldSkip || commentDetected);

        // Special characters
        if (ch == '\'') {
            return new Token(Token.QUOTE);
        } else if (ch == '(') {
            return new Token(Token.LPAREN);
        } else if (ch == ')') {
            return new Token(Token.RPAREN);
        } else if (ch == '.') {
            return new Token(Token.DOT);
        } // Boolean constants
        else if (ch == '#') {
            try {
                bite = in.read();
            } catch (IOException e) {
                System.err.println("We fail: " + e.getMessage());
            }

            if (bite == -1) {
                System.err.println("Unexpected EOF following #");
                return null;
            }
            ch = (char) bite;
            if (ch == 't') {
                return new Token(Token.TRUE);
            } else if (ch == 'f') {
                return new Token(Token.FALSE);
            } else {
                System.err.println("Illegal character '" + (char) ch + "' following #");
                return getNextToken();
            }
        } // String constants
        else if (ch == '"') {
            int bufIndex = 0;
            boolean foundQuote = false;
            while (!foundQuote) {
                try {
                    bite = in.read();
                } catch (IOException e) {
                    System.err.println("We fail: " + e.getMessage());
                }

                if (bite == -1) {
                    System.err.println("Unexpected EOF following string constant opening");
                    return null;
                }
                ch = (char) bite;

                if (ch == '"') {
                    foundQuote = true;
                } else {
                    buf[bufIndex++] = (byte) bite;
                }
            }
            return new StrToken(new String(buf, 0, bufIndex));
        } // Integer constants
        else if (ch >= '0' && ch <= '9') {
            int bufIndex = 0;
            buf[bufIndex++] = (byte) ch;
            boolean foundNumEnd = false;
            while (!foundNumEnd) {
                try {
                    bite = in.read();
                } catch (IOException e) {
                    System.err.println("We fail: " + e.getMessage());
                }

                if (bite == -1) {
                    System.err.println("Unexpected EOF following integer constant");
                    return null;
                }
                ch = (char) bite;

                if (ch >= '0' && ch <= '9') {
                    buf[bufIndex++] = (byte) bite;
                } else {
                    foundNumEnd = true;
                    try {
                        in.unread(bite);
                    } catch (IOException e) {
                        System.err.println("Failed to push back extra non-interger byte: " + e.getMessage());
                    }
                }
            }
            String s = new String(buf).substring(0, bufIndex);
            return new IntToken(Integer.parseInt(s));
        } // Identifiers 
        else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')
                || ch == '!' || ch == '$' || ch == '%'
                || ch == '&' || ch == '*' || ch == '+'
                || ch == '-' || ch == '.' || ch == '/'
                || ch == ':' || ch == '<' || ch == '='
                || ch == '>' || ch == '?' || ch == '@'
                || ch == '^' || ch == '_' || ch == '~') {

            int bufIndex = 0;
            buf[bufIndex++] = (byte) ch;
            boolean foundIdentEnd = false;
            while (!foundIdentEnd) {
                try {
                    bite = in.read();
                } catch (IOException e) {
                    System.err.println("We fail: " + e.getMessage());
                }

                if (bite == -1) {
                    System.err.println("Unexpected EOF following identifier start");
                    return null;
                }
                ch = (char) bite;

                if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')
                        || ch == '!' || ch == '$' || ch == '%'
                        || ch == '&' || ch == '*' || ch == '+'
                        || ch == '-' || ch == '.' || ch == '/'
                        || ch == ':' || ch == '<' || ch == '='
                        || ch == '>' || ch == '?' || ch == '@'
                        || ch == '^' || ch == '_' || ch == '~'
                        || (ch >= '0' && ch <= '9')) {
                    buf[bufIndex++] = (byte) bite;
                } else {
                    foundIdentEnd = true;
                    try {
                        in.unread(bite);
                    } catch (IOException e) {
                        System.err.println("Failed to push back extra non-interger byte: " + e.getMessage());
                    }
                }
            }
            return new IdentToken(new String(buf).substring(0, bufIndex));
        } // Illegal character
        else {
            System.err.println("Illegal input character '" + (char) ch + '\'');
            return getNextToken();
        }
    }
}
