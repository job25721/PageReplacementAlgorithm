package os.cmu;

import java.util.*;

public class Algorithms implements algorithmMethod {
    public List<String> frame(int n){
        List <String> frame = new ArrayList<>(n);
        for(int i=0;i<n;i++){
            frame.add("");
        }
        return frame;
    }

    @Override
    public int BeladyAnomaly(int frameSize,String Algorithm,String[] referenceString){
        int pageFault = 0;
        if(Algorithm == "FIFO")
            pageFault = FIFO(referenceString,frameSize,false);
        else if(Algorithm== "LRU")
            pageFault = LRU(referenceString,frameSize,false);
        else if(Algorithm== "Optimal")
            pageFault = Optimal(referenceString,frameSize,false);
        else
            System.out.println("Please select FIFO,LRU or Optimal");
        System.out.println("Pagefault = " +pageFault);
        return pageFault;
    }

    @Override
    public int FIFO(String[] referenceString,int n,boolean printProcess){
        int pageFaultCount = 0;
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
                pageFaultCount++;
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
        return pageFaultCount;
    }

    @Override
    public int LRU(String[] referenceString,int n,boolean printProcess) {
        int pageFaultCount = 0;
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
                pageFaultCount++;
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
        return pageFaultCount;
    }

    @Override
    public int Optimal(String[] referenceString,int n,boolean printProcess) {
        int pageFaultCount = 0;
        List<String> frame = frame(n);
        int[] framePeriod = new int[n];
        //count init
        int c =0;
        for(int i=n;i>0;i--){
            framePeriod[c] += i;
            c++;
        }

        int count = 0;
        while (count < referenceString.length){
            String current = referenceString[count];
            if (frame.contains(current)){
                if (printProcess)
                    System.out.print(current+"->Page hit : ");
                for(int i=0;i<framePeriod.length;i++) framePeriod[i] = framePeriod[i]++;
            }else{
                if (printProcess)
                    System.out.print(current+"->Page fault : ");
                pageFaultCount++;
                int targetIndex = -1;
                if(frame.contains("")){
                    targetIndex = frame.indexOf("");
                }else{
                    for (int i=0;i<frame.size();i++){
                        String ref = frame.get(i);
                        int countStamp = 0;
                        for (int j=count+1;j<referenceString.length;j++){
                            framePeriod[i]++;
                            if (ref == referenceString[j]){
                                countStamp = framePeriod[i];
                            }
                        }
                        if(countStamp != 0) framePeriod[i] -= (framePeriod[i] - countStamp);
                    }
                    int largestIndex = 0;
                    for (int i=0;i<framePeriod.length;i++){
                        if(framePeriod[i] >= framePeriod[largestIndex])
                            largestIndex = i;
                    }
                    targetIndex = largestIndex;
                    //set
                    framePeriod[targetIndex] = 0;
                }
                frame.set(targetIndex,current);
            }
            if (printProcess)
                System.out.println(frame);
            count++;
        }
        return pageFaultCount;
    }


}
