package os.cmu;

public interface algorithmMethod {
    int FIFO(String[] referenceString,int n,boolean printProcess);
    int LRU(String[] referenceString,int n,boolean printProcess);
    int Optimal(String[] referenceString,int n,boolean printProcess);
    int BeladyAnomaly(int frameSize,String Algorithm,String[] referenceString);
}
