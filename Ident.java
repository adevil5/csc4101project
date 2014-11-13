
class Ident extends Node {

    private String name;

    public Ident(String n) {
        name = n;
    }

    @Override
    public void print(int n) {
        Printer.printIdent(n, name);
    }

    @Override
    public boolean isSymbol() {
        return true;
    }

    public String getSymbol() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public Node eval(Node p, Environment env) {
        return env.lookup(p);
    }
            
}
