package DS;

public class Trie {

    private TrieNode root;

    public Trie(){
        this.root = new TrieNode();
    }

    public boolean hasWord (String str){
        str = str.toLowerCase();
        return root.containsWord(str);
    }

    public void addWord(String str){
        str = str.toLowerCase();
        TrieNode cur = this.root;
        while (str.length()>0){
            char nxt = str.charAt(0);
            if (cur.containsNode(nxt)){
                cur = cur.getNode(nxt);
            }else{
                cur = cur.createNode(nxt);
            }
            str = str.substring(1);
        }
        cur.isEnd = true;
    }

    public static void main(String[] args){
        Trie tr = new Trie();
        tr.addWord("dkwikd");
        tr.addWord("dk");
        System.out.println(tr.hasWord("dkwikd"));
        System.out.println(tr.hasWord("dkw"));
    }

    private class TrieNode{

        private boolean isEnd;
        private TrieNode[] nxt;

        public TrieNode(){
            this.isEnd = false;
            this.nxt = new TrieNode[26];
        }

        public TrieNode getNode (char c){
            return nxt[c-'a'];
        }

        public TrieNode createNode(char c){
            this.nxt[c-'a'] = new TrieNode();
            return this.nxt[c-'a'];
        }

        public boolean containsNode (char c){
            return getNode(c) != null;
        }

        public boolean containsWord(String str){
            if (str.length()==0){
                return this.isEnd;
            }else if (this.containsNode(str.charAt(0))){
                return this.getNode(str.charAt(0)).containsWord(str.substring(1));
            }else{
                return false;
            }
        }
    }
}
