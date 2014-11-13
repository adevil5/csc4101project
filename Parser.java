// Parser.java -- the implementation of class Parser
//
// Defines
//
//   class Parser;
//
// Parses the language
//
//   exp  ->  ( rest
//         |  #f
//         |  #t
//         |  ' exp
//         |  integer_constant
//         |  string_constant
//         |  identifier
//    rest -> )
//         |  exp+ [. exp] )
//
// and builds a parse tree.  Lists of the form (rest) are further
// `parsed' into regular lists and special forms in the constructor
// for the parse tree node class Cons.  See Cons.parseList() for
// more information.
//
// The parser is implemented as an LL(0) recursive descent parser.
// I.e., parseExp() expects that the first token of an exp has not
// been read yet.  If parseRest() reads the first token of an exp
// before calling parseExp(), that token must be put back so that
// it can be reread by parseExp() or an alternative version of
// parseExp() must be called.
//
// If EOF is reached (i.e., if the scanner returns a NULL) token,
// the parser returns a NULL tree.  In case of a parse error, the
// parser discards the offending token (which probably was a DOT
// or an RPAREN) and attempts to continue parsing with the next token.

class Parser {

    private Scanner scanner;

    public Parser(Scanner s) {
        scanner = s;
    }

    public Node parseExp() {
        Token tok = scanner.getNextToken();
        return parseExp(tok);
    }

    private Node parseExp(Token tok) {
        if (tok == null) {
            return null;
        }
        if (tok.getType() == TokenType.INT) {
            return new IntLit(tok.getIntVal());
        }
        if (tok.getType() == TokenType.STRING) {
            return new StrLit(tok.getStrVal());
        }
        if (tok.getType() == TokenType.TRUE) {
            return new BooleanLit(true);
        }
        if (tok.getType() == TokenType.FALSE) {
            return new BooleanLit(false);
        }
        if (tok.getType() == TokenType.IDENT) {
            return new Ident(tok.getName());
        }
        if (tok.getType() == TokenType.QUOTE) {
            Node t = parseExp();
            return new Cons(new Ident("QUOTE"), new Cons(t, new Nil()));
        }
        if (tok.getType() == TokenType.LPAREN) {
            return parseRest();
        }
        if (tok.getType() == TokenType.DOT) {
            System.out.println("Error: illegal dot");
            return parseExp();
        }
        System.out.println("Error: illegal right paren");
        return parseExp();
    }

    protected Node parseRest() {
        Token tok = scanner.getNextToken();
        return parseRest(tok);
    }

    private Node parseRest(Token tok) {
        if (tok == null) {
            System.err.println("Error: list contains end of file");
            return null;
        }
        if (tok.getType() == TokenType.RPAREN) {
            return new Nil();
        }
        Node car = parseExp(tok);

        tok = scanner.getNextToken();
        if (tok == null) {
            System.err.println("Error: list contains end of file");
            return null;
        }
        Node cdr;
        if (tok.getType() == TokenType.DOT) {
            cdr = parseExp();
            if (cdr == null) {
                System.err.println("Error: list contains end of file");
                return null;
            }
            tok = scanner.getNextToken();
            if (tok == null) {
                System.err.println("Error: list contains end of file");
                return null;
            }
            if (tok.getType() != TokenType.RPAREN) {
                System.err.println("RPAREN not found");
                cdr.print(2);
                System.err.println("drop input until RPAREN");
            }

            while ((tok != null) && (tok.getType() != TokenType.RPAREN)) {
                Node n = parseExp(tok);
                if (n == null) {
                    return null;
                }
                tok = scanner.getNextToken();
            }
            if (tok == null) {
                return null;
            }
        } else {
            cdr = parseRest(tok);
            if (cdr == null) {
                return null;
            }
        }
        return new Cons(car, cdr);
    }
    // TODO: Add any additional methods you might need.
}
