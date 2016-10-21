package DNA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sequence {
    private final String description;
    private final List<Nucleotide> sequence = new ArrayList<>();

    public Sequence(String description){
        this.description = description;
    }

    public void add(Nucleotide toAdd){
        this.sequence.add(toAdd);
    }

    public List<Nucleotide> getNucleotides() {
        return sequence;
    }

    public String getDescription() {
        return description;
    }

    public String getNucleotidesAsString() {
        String ret = "";
        for(Nucleotide n : this.getNucleotides()){
            ret += n.toString();
        }
        return ret;
    }
}
