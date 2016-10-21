package util;

import DNA.Nucleotide;
import DNA.Sequence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FastaFileReader {
    /**
     *
     */
    public static final String emptyString = "-";
    public static final char descriptionLine = '>';

    public static List<Sequence> readFile(File fastaFile) throws IOException {
        List<Sequence> ret = new ArrayList<>();
        FileReader fr = new FileReader(fastaFile);
        BufferedReader fbuffer = new BufferedReader(fr);
        Sequence currentSequence = null;
        String line = fbuffer.readLine();
        while(line != null){
            if(line.charAt(0) == descriptionLine){
                currentSequence = new Sequence(line.substring(1));
                ret.add(currentSequence);
            }else{
                for(char c : line.toCharArray()){
                    if(c == emptyString.charAt(0)){
                        currentSequence.add(new None());
                    }else {
                        Nucleotide toAdd = Nucleotide.fromChar(c);
                        if(toAdd == null)throw new NullPointerException();
                        currentSequence.add(toAdd);
                    }
                }
            }
            line = fbuffer.readLine();
        }
        return ret;
    }

}

class None extends Nucleotide{
    public None() {
        this.stringRepresentation = "-";
    }
}