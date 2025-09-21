import java.util.*;

public class DnaCompare {

    // Codon to amino acid map
    private static final Map<String,String> CODON_MAP = new HashMap<>();
    static {
        String[][] m = {
            {"TTT","F"},{"TTC","F"},{"TTA","L"},{"TTG","L"},
            {"CTT","L"},{"CTC","L"},{"CTA","L"},{"CTG","L"},
            {"ATT","I"},{"ATC","I"},{"ATA","I"},{"ATG","M"},
            {"GTT","V"},{"GTC","V"},{"GTA","V"},{"GTG","V"},
            {"TCT","S"},{"TCC","S"},{"TCA","S"},{"TCG","S"},
            {"CCT","P"},{"CCC","P"},{"CCA","P"},{"CCG","P"},
            {"ACT","T"},{"ACC","T"},{"ACA","T"},{"ACG","T"},
            {"GCT","A"},{"GCC","A"},{"GCA","A"},{"GCG","A"},
            {"TAT","Y"},{"TAC","Y"},{"TAA","*"},{"TAG","*"},
            {"TGA","*"},{"CAT","H"},{"CAC","H"},{"CAA","Q"},
            {"CAG","Q"},{"AAT","N"},{"AAC","N"},{"AAA","K"},
            {"AAG","K"},{"GAT","D"},{"GAC","D"},{"GAA","E"},
            {"GAG","E"},{"TGT","C"},{"TGC","C"},{"TGG","W"},
            {"CGT","R"},{"CGC","R"},{"CGA","R"},{"CGG","R"},
            {"AGA","R"},{"AGG","R"},{"AGT","S"},{"AGC","S"},
            {"GGT","G"},{"GGC","G"},{"GGA","G"},{"GGG","G"}
        };
        for (String[] kv : m) CODON_MAP.put(kv[0], kv[1]);
    }

    public static ArrayList<String> DNAToCodons(String dna) {
        dna = dna.trim().toUpperCase();
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i + 2 < dna.length(); i += 3) {
            out.add(dna.substring(i, i+3));
        }
        return out;
    }

    public static String CodonToAminoAcid(String codon) {
        return CODON_MAP.getOrDefault(codon.toUpperCase(), "?");
    }

    public static ArrayList<String> dna_to_amino_acid(String dna) {
        ArrayList<String> codons = DNAToCodons(dna);
        ArrayList<String> aa = new ArrayList<>();
        for (String c : codons) {
            aa.add(CodonToAminoAcid(c));
        }
        return aa;
    }

    public static boolean is_match(ArrayList<String> a1, ArrayList<String> a2) {
        return a1.equals(a2);
    }

    public static void main(String[] args) {
        String DNA1 = "CTGATATTGTATCCGGCCGAA";
        String DNA2 = "CTAGCCGGTGGTTATTAATAGTAAACTATTCCA";
        String DNA3 = "TTAATCCTCTACCCCGCAGAG";

        ArrayList<String> aa1 = dna_to_amino_acid(DNA1);
        ArrayList<String> aa2 = dna_to_amino_acid(DNA2);
        ArrayList<String> aa3 = dna_to_amino_acid(DNA3);

        System.out.println("DNA1 AA: " + aa1);
        System.out.println("DNA2 AA: " + aa2);
        System.out.println("DNA3 AA: " + aa3);

        System.out.println("DNA1 vs DNA2: " + (is_match(aa1, aa2) ? "MATCH" : "NO MATCH"));
        System.out.println("DNA1 vs DNA3: " + (is_match(aa1, aa3) ? "MATCH" : "NO MATCH"));
        System.out.println("DNA2 vs DNA3: " + (is_match(aa2, aa3) ? "MATCH" : "NO MATCH"));
    }
}
