package DNA;

import java.util.ArrayList;
import java.util.List;

public abstract class Nucleotide {
    protected String stringRepresentation;

    public static final List<Nucleotide> existingNucleotides = new ArrayList<>();
    static{
        existingNucleotides.add(new Adenine());
        existingNucleotides.add(new Cytosine());
        existingNucleotides.add(new Guanine());
        existingNucleotides.add(new Thymine());
        existingNucleotides.add(new Uracil());
    }

    /**
     *
     * @param stringRepresentation - must be the FASTA code for a Nucleotide @url{https://en.wikipedia.org/wiki/FASTA_format#Sequence_representation}
     * @return null, if strinRepresentation is not the char code for a Nucleotide
     */
    public static Nucleotide fromChar(char stringRepresentation){
        for(Nucleotide ncl : existingNucleotides){
            if(ncl.toString().equals(""+stringRepresentation))return ncl;
        }
        return null;
    }

    public String toString(){
        return stringRepresentation;
    }
}

class Adenine extends Nucleotide{
    public Adenine(){
        stringRepresentation = "A";
    }
}

class Cytosine extends Nucleotide{
    public Cytosine(){
        stringRepresentation = "C";
    }
}

class Guanine extends Nucleotide{
    public Guanine(){
        stringRepresentation = "G";
    }
}

class Thymine extends Nucleotide{
    public Thymine(){
        stringRepresentation = "T";
    }
}

class Uracil extends Nucleotide{
    public Uracil(){
        stringRepresentation = "U";
    }
}
