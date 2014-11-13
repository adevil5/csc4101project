
class Begin extends Special {

    public Begin(Node t) {
    }

    @Override
    void print(Node t, int n, boolean p) {
        Printer.printBegin(t, n, p);
    }
    @Override
    public Node eval(Node p, Environment env) {
        return null;
    }

}
