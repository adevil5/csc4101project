
class Nil extends Node {

    public Nil() {
    }

    @Override
    public void print(int n) {
        print(n, false);
    }

    @Override
    public void print(int n, boolean p) {
        Printer.printNil(n, p);
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
