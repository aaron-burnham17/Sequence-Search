package edu.gcccd.csis.p1;

public class SequenceSearchImpl extends SequenceSearch {
    private String longest = null; //cache of the longest.
    public SequenceSearchImpl(final String content, final String start, final String end) {
        super(content, start, end);
    }

    @Override
    public String[] getAllTaggedSequences() {
        String[] sa = new String[0];
        int curInd = 0,nextInd,endInd = content.indexOf(endTag); //The three tags necessary for the algorithm to work.
        boolean check = true;
        if(endInd != -1) { //checks to see if it reaches the end of the tags.
            do {
                curInd = content.indexOf(startTag, curInd) + startTag.length();
                nextInd = content.indexOf(startTag, curInd) + startTag.length();
                endInd = content.indexOf(endTag, curInd);
                if (nextInd > content.indexOf(endTag) && check) { //Algorithm checks to see if it the next index of the start tag is past the end tag signifying content.
                    sa = this.adds(sa, content.substring(curInd, endInd));

                }
                check = startTag.equals(endTag) ? !check : check; //Given that the tags are the same, only every other tag set is valid.
            } while (nextInd - startTag.length() != -1);
            if (endInd != -1) { //Covers the edge case.
                sa = this.adds(sa, content.substring(curInd, endInd));
            }
        }
        return sa;

    }

    @Override
    public String getLongestTaggedSequence() {  //Establishes which one is longest first, then caches in order to save compiling time.
        String[] sa = this.getAllTaggedSequences();
        if (longest == null && sa.length > 0){
            longest = sa[0];
            for(int i = 1; i < sa.length;i++){
                longest = sa[i].length() >= longest.length() ? sa[i] : longest;
            }
        }
        return longest; //returns longest regardless, so it returns either null, the cached version, or the initial calculated longest.
    }

    @Override
    public String displayStringArray() {
       String[] sa = this.getAllTaggedSequences();
       String a = "";
       for(String s: sa) { //Formats the code as the tests suggest to by iterating through for loop.
           a += (s + " : " + s.length() + "\n");

       }
       return a;
    }

    @Override
    public String toString() {
        String a = "";
        for(int i = 0; i < content.length();i++){
            if (i == content.indexOf(startTag,i)){ //increments the index based on the startTag length
                i += startTag.length()-1;
            }
            else if(i == content.indexOf(endTag,i)){ //increments the index based on the endTag length when it reaches it.
                i += endTag.length()-1;
            }
            else{
                a += content.charAt(i);
            }
        }
        return a;
    }

}