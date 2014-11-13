
class Set extends Special {

    public Set(Node t) {
    }

    @Override
    void print(Node t, int n, boolean p) {
        Printer.printSet(t, n, p);
    }
    
    @Override
    public Node eval(Node p, Environment env) {
    Node id, exp;
    id = p.getCdr().getCar();
    exp = p.getCdr().getCdr().getCar();
    env.define(id,exp.eval(env));
    return new StrLit("");
}
}
