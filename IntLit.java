
class IntLit extends Node {

    private int intVal;
    
    public IntLit(int i) {
        intVal = i;
    }
    
    @Override
    public void print(int n) {
        Printer.printIntLit(n, intVal);
    }
    
    @Override
    public boolean isNumber(){
        return true;
    }
    
    public int getIntVal() {
        return intVal;
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
