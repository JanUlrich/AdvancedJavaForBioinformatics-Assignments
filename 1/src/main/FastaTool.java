package main;

import DNA.Sequence;
import GUI.CommandLine;
import util.FastaFileReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FastaTool {

    public static void main(String[] args) throws IOException {
        if(args.length < 1){
            System.out.println("No FASTA file given!");
            return;
        }
        File fastaFile = new File(args[0]);
        List<Sequence> sequences = FastaFileReader.readFile(fastaFile);
        CommandLine.printOut(sequences);
    }
}
