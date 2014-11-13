
class Quote extends Special {

    // TODO: Add any fields needed.
    public Quote(Node t) {
    }

    @Override
    void print(Node t, int n, boolean p) {
        Printer.printQuote(t, n, p);
    }
    
    @Override
    public Node eval(Node p, Environment env) {
        return p.getCdr().getCar();
    }
}
