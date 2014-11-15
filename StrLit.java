
class StrLit extends Node {

    private String strVal;

    public StrLit(String s) {
        strVal = s;
    }

    @Override
    public void print(int n) {
        Printer.printStrLit(n, strVal);
    }

    public String getStrVal() {
        return strVal;
    }

    @Override
    public boolean isString() {
        return true;
    }
    
    @Override
    public Node eval(Node p, Environment env){
        return this;
    }
    
    @Override
    Node eval(Environment env){
        return this;
    }
    
    @Override
    public Node apply(Node args){
        return this;
    }
}
