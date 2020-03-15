package os.cmu;

public class Main {
    public static void main(String[] args) {
        String[] referenceString1 = {"7","0","1","2","0","3","0","4","2","3","0","3","1","2","0"};
        String[] referenceString2 = {"7","0","1","2","0","3","0","4","2","3","0","3","2","3"};
        String[] referenceString3 ={"1", "3", "0", "3", "5", "6"};
        String[] Beladyanomaly  = {"3","2","1","0","3","2","4","3","2","1","0","4"};

        Algorithms a = new Algorithms();
        String[] Algorithm = {"FIFO","Optimal","LRU"};
        for(int frameSize=3;frameSize<=6;frameSize++){
            System.out.println("Frame size = "+frameSize);
            for (int i=0;i<3;i++){
                System.out.print(Algorithm[i] + ": ");
                a.BeladyAnomaly(frameSize,Algorithm[i],Beladyanomaly);
            }
            System.out.println("---------------------------------------");
        }
//        int frameSize = 6;

//        for (int i=0;i<3;i++){
//            double avg =0;
//            System.out.println(Algorithm[i] + ":::::::::::::::::::::::");
//            avg += a.BeladyAnomaly(frameSize,Algorithm[i],referenceString1);
//            avg += a.BeladyAnomaly(frameSize,Algorithm[i],referenceString2);
//            avg += a.BeladyAnomaly(frameSize,Algorithm[i],referenceString3);
//            System.out.println("Avg page falut = "+ avg/3);
//            System.out.println("---------------------------------------");
//        }

    }
}
