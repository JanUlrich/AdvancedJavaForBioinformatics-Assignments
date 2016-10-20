package DNA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Sequence implements Iterable<Nucleotide>{
    public final String description;
    private final List<Nucleotide> sequence = new ArrayList<>();

    public Sequence(String description){
        this.description = description;
    }

    @Override
    public Iterator<Nucleotide> iterator() {
        return sequence.iterator();
    }
}
