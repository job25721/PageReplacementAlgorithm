package os.cmu;

import java.sql.Array;
import java.util.*;

public class Algorithms implements algorithmMethod {
    //test cases
    public String[] referenceString1 = {"7","0","1","2","0","3","0","4","2","3","0","3","1","2","0"};
    public String[] referenceString2 = {"7","0","1","2","0","3","0","4","2","3","0","3","2","1","2","0","1","7","0","1"};
    public String[] referenceString3 = {"7","0","1","2","0","3","0","4","2","3","0","3","2","1","2","0","1","7"};
    public String[] BeladyAnomaly = {"1", "2", "3", "4", "1", "2", "5", "1", "2", "3", "4", "5" };


    public List<String> frame(int n){
        List <String> frame = new ArrayList<>(n);
        for(int i=0;i<n;i++){
            frame.add("");
        }
        return frame;
    }

    @Override
    public void BeladyAnomaly(int frameSize,String Algorithm,String[] referenceString){
        int pageFault = 0;
        String cas = Algorithm;
        if(Algorithm == "FIFO")
            pageFault = FIFO(referenceString,frameSize,false);
        else if(Algorithm== "LRU")
            pageFault = LRU(referenceString,frameSize,false);
        else if(Algorithm== "Optimal")
            pageFault = Optimal(referenceString,frameSize,false);
        else
            System.out.println("Please select FIFO,LRU or Optimal");

        System.out.println("Frame size = " +frameSize+" Pagefault = " +pageFault);
    }

    @Override
    public int FIFO(String[] referenceString,int n,boolean printProcess){
        int pageFaultcount = 0;
        List <String> frame = frame(n);
        List <String> FIFO = new ArrayList<>();
        int fifoCount = 0;
        int count = 0;
        while(count < referenceString.length){
            String current = referenceString[count];
            if(frame.contains(current)){
                if (printProcess)
                    System.out.print(current+"->Page hit : ");
            }else{
                if (printProcess)
                    System.out.print(current+"->Page fault : ");
                pageFaultcount++;
                int targetIndex = -1;
                if(frame.contains("")){
                    targetIndex = frame.indexOf("");
                }else{
                    targetIndex = frame.indexOf(FIFO.get(fifoCount));
                    fifoCount++;
                }
                FIFO.add(referenceString[count]);
                frame.set(targetIndex,current);
            }
            if (printProcess)
                System.out.println(frame);
            count++;
        }
        return pageFaultcount;
    }

    @Override
    public int LRU(String[] referenceString,int n,boolean printProcess) {
        int pageFaultcount = 0;
        List<String> frame = frame(n);
        LinkedList<String> LRU = new LinkedList<>();

        int count = 0;
        while(count < referenceString.length){
            String current = referenceString[count];
            LRU.add(current);
            if(frame.contains(current)){
                if (printProcess)
                    System.out.print(current+"->Page hit : ");
                LRU.remove(current);
            }else{
                if (printProcess)
                    System.out.print(current+"->Page fault : ");
                pageFaultcount++;
                int targetIndex = -1;
                if(frame.contains("")){
                    targetIndex = frame.indexOf("");
                }else{
                    targetIndex = frame.indexOf(LRU.peekFirst());
                    LRU.remove(frame.get(targetIndex));
                }
                frame.set(targetIndex,current);
            }
            if (printProcess)
                System.out.println(frame);
            count++;
        }
        return pageFaultcount;
    }

    @Override
    public int Optimal(String[] referenceString,int n,boolean printProcess) {
        int pageFaultcount = 0;
        List<String> frame = frame(n);
        int[] frameCount = new int[n];
        //count init
        for(int i=0;i<n;i++){
            frameCount[i] = 0;
        }
        int count = 0;
        while (count < referenceString.length){
            String current = referenceString[count];
            if (frame.contains(current)){
                if (printProcess)
                    System.out.print(current+"->Page hit : ");
            }else{
                if (printProcess)
                    System.out.print(current+"->Page fault : ");
                pageFaultcount++;
                int targetIndex = -1;
                if(frame.contains("")){
                    targetIndex = frame.indexOf("");
                }else{
                    for (int i=0;i<frame.size();i++){
                        String ref = frame.get(i);
                        int countStamp = 0;
                        for (int j=count+1;j<referenceString.length;j++){
                            frameCount[i]++;
                            if (ref == referenceString[j]){
                                countStamp = frameCount[i];
                            }
                        }
                        if(countStamp != 0) frameCount[i] -= (frameCount[i] - countStamp);
                    }
                    int largestIndex = 0;
                    for (int i=0;i<frameCount.length;i++){
                        if(frameCount[i] > frameCount[largestIndex])
                            largestIndex = i;
                    }
                    targetIndex = largestIndex;
                    //setallcount = 0
                    for(int i=0;i<n;i++){
                        frameCount[i] = 0;
                    }
                }
                frame.set(targetIndex,current);
            }
            if (printProcess)
                System.out.println(frame);
            count++;
        }
        return pageFaultcount;
    }


}
