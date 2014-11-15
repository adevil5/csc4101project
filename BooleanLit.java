
class BooleanLit extends Node {

    private boolean booleanVal;

    public BooleanLit(boolean b) {
        booleanVal = b;
    }

    @Override
    public void print(int n) {
        // There got to be a more efficient way to print n spaces.
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }

        if (booleanVal) {
            Printer.printBooleanLit(n, 0);
        } else {
            Printer.printBooleanLit(n, 0);
        }
    }

    @Override
    public boolean isBoolean() {
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
    
    public boolean getBooleanVal() {
        return booleanVal;
    }
}
