package GUI;

import DNA.Nucleotide;
import DNA.Sequence;
import util.FastaFileReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by janulrich on 20.10.16.
 */
public class CommandLine {

    public static final int rowLength = 85;

    public static void printOut(List<Sequence> sequenceList){

        int maxDescriptionLength = max(sequenceList.parallelStream().map((Sequence s)->s.getDescription().length()).collect(Collectors.toList()));
        maxDescriptionLength += 3; //add extra space between description and Sequence output

        int maxSequenceStringLength = max(sequenceList.parallelStream().map((Sequence s)->s.getNucleotidesAsString().length()).collect(Collectors.toList()));

        int currentSequencChar = 0;
        while(currentSequencChar < maxSequenceStringLength){
            List<String> lines = new ArrayList<>();
            lines.add(""); //Die erste Zeile wird am Ende angepasst
            List<Integer> columnNumbers = new ArrayList<>(); // Saves all the numbers that need to be displayed on top of the sequences
            for(Sequence s : sequenceList){
                String sequenceDescription = s.getDescription();
                for(;sequenceDescription.length()<maxDescriptionLength;)
                    sequenceDescription += " "; //Fill gap with spaces

                int seqLength = rowLength - maxDescriptionLength;
                if(seqLength + currentSequencChar > s.getNucleotidesAsString().length())seqLength = s.getNucleotidesAsString().length() - currentSequencChar;
                String line = sequenceDescription + s.getNucleotidesAsString()
                        .substring(currentSequencChar, currentSequencChar + seqLength);

                int columnNumber = seqLength + currentSequencChar;
                if(! columnNumbers.contains(columnNumber)){
                    columnNumbers.add(columnNumber);
                }
                lines.add(line);
            }

            String lineDesc = ""; //Die Zeile mit den Nummerierungen
            for(;lineDesc.length()<maxDescriptionLength;)
                lineDesc += " "; //Fill gap with spaces
            lineDesc += (currentSequencChar + 1); //erste Nummer
            for(int n : columnNumbers){
                String numberToAdd = "" + (n);
                for(;lineDesc.length()<n - numberToAdd.length() + maxDescriptionLength - currentSequencChar;)
                    lineDesc += " "; //Fill gap with spaces
                lineDesc += numberToAdd;
            }
            lines.set(0, lineDesc);
            currentSequencChar = currentSequencChar + rowLength - maxDescriptionLength;

            //Print the lines:
            lines.forEach(CommandLine::printLn);
            CommandLine.printLn(""); //Empty line after a block
        }

        CommandLine.printLn(""); // New line

        //Generate statistics:
        CommandLine.printLn("Number of Sequences: "+sequenceList.size());
        int shortestLength =  min(sequenceList.parallelStream().map((Sequence s)->s.getNucleotidesAsString().length()).collect(Collectors.toList()));
        int longestLength = maxSequenceStringLength;

        //Generate list of connected Nucleotides without "-":
        List<List<Integer>> connectedNLists = sequenceList.parallelStream().map((Sequence seq) -> {
            List<Integer> connectedN = new ArrayList<Integer>();
            int current = 0;
            Iterator<Nucleotide> it = seq.getNucleotides().iterator();
            while (it.hasNext()) {
                Nucleotide n = it.next();
                if (n.toString().equals(FastaFileReader.emptyString)) {
                    connectedN.add(current);
                    current = 0;
                } else {
                    current++;
                }
                if (!it.hasNext()) connectedN.add(current);
            }
            return connectedN;
        }).collect(Collectors.toList());
        int shortestLengthExcluding = min(connectedNLists.parallelStream().map((l)->min(l.parallelStream().filter((Integer i)-> i > 0).collect(Collectors.toList()))).collect(Collectors.toList()));
        int longestLengthExcluding =  max(connectedNLists.parallelStream().map((l)->max(l)).collect(Collectors.toList()));

        //List<Integer> connectedNListAll = connectedNLists.stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        List<Integer> connectedNListAll = new ArrayList<>();
        for(List<Integer> iList : connectedNLists){
            connectedNListAll.addAll(iList);
        }
        connectedNListAll = connectedNListAll.stream().filter((Integer i) -> i > 0).collect(Collectors.toList()); //Sort out "0"
        int averageExcluding = average(connectedNListAll);

        CommandLine.printLn("Shortest Length: "+shortestLength + " (excluding '-'s: "+shortestLengthExcluding + ")");
        CommandLine.printLn("Average Length: "+0 + " (excluding '-'s: "+averageExcluding + ")");
        CommandLine.printLn("Longest Length: "+longestLength + " (excluding '-'s: "+longestLengthExcluding + ")");
    }

    private static void printLn(String line){
        System.out.println(line);
    }

    private static int longestSequence(Sequence inSequence){
        int ret = 0;
        int current = 0;
        for(Nucleotide nuc : inSequence.getNucleotides()){
            if (nuc.toString().equals(FastaFileReader.emptyString)) {
                current = 0;
            } else current++;
            if(current > ret)ret = current;
        }
        return ret;
    }


    private static int max(List<Integer> ints){
        int ret = ints.get(0);
        for(int i : ints){
            ret = Integer.max(i, ret);
        }
        return ret;
    }

    private static int min(List<Integer> ints){
        int ret = ints.get(0);
        for(int i : ints){
            ret = Integer.min(i, ret);
        }
        return ret;
    }

    private static int average(List<Integer> ints){
        Integer commulated = ints.stream().reduce((i0, i)-> i+i0).get();
        return commulated/ints.size();
    }

}

