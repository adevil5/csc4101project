
class Define extends Special {

    public Define(Node t) {
    }

    @Override
    void print(Node t, int n, boolean p) {
        Printer.printDefine(t, n, p);
    }
    
    @Override
    public Node eval(Node p, Environment env) {
    Node id, val;
 
    id = p.getCdr().getCar();
    val = p.getCdr().getCdr().getCar();

    if(id.isSymbol()) { //defining a variable
	env.define(id, val);
    }
    else { //defining a function
	Closure func = new Closure(new Cons(p.getCdr().getCar().getCdr(), p.getCdr().getCdr()), env);
	env.define(id.getCar(), func);
    }

    return new StrLit("No values returned\n");
}
}
