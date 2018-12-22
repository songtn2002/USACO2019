import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class TreenetworkCore {

    private static int N;
    private static int S;

    private static LinkedList<Edge>[] al;
    private static DFSRec[][] dp;
    private static int diaLen = 0;
    private static LinkedList<DFSRec> diameters = new LinkedList<>();

    private static int calcMinEcc(DFSRec dr){
        Edge[] edges = new Edge[dr.record.size()];
        dr.record.toArray(edges);
        //System.out.println(Arrays.toString(edges));
        double hLen = dr.len/2.0;
        //System.out.println("hLen: "+hLen);
        int accL = 0;
        int midIndex;
        for (midIndex = 0; accL<hLen; midIndex++){
            accL+=edges[midIndex].w;
        }
        midIndex--;
        //System.out.println("accL: "+accL);
        //System.out.println("midIndex: "+midIndex);
        if (accL==hLen&&edges[midIndex].w>edges[midIndex+1].w){
            midIndex++;
            accL+=edges[midIndex].w;
        }
        LinkedList<Edge> core = new LinkedList<>();
        int left = accL;
        int right = dr.len - accL;
        //System.out.println("left: "+left);
        //System.out.println("right: "+right);
        int cur = midIndex;
        int leftIndex = cur;
        int rightIndex = cur;
        int len = 0;
        boolean isRight = false;
        int otherMax = 0;
        while (true){
            if (edges[cur].w+len>S){
                break;
            }else{
                if (isRight){
                    core.add(edges[cur]);
                    right-=edges[cur].w;
                    rightIndex = cur;
                }else{
                    core.addFirst(edges[cur]);
                    left-=edges[cur].w;
                    leftIndex = cur;
                }
                len+=edges[cur].w;
                Edge prev;
                Edge nxt;
                if (otherMax>=left&&otherMax>=right) break;
                if (right>left) {
                    cur = rightIndex+1;
                    isRight = true;
                    prev = edges[rightIndex];
                    nxt = edges[cur];
                    //System.out.println("Prev: "+prev.toString());
                    //System.out.println("Nxt: "+nxt.toString());
                }else{
                    cur = leftIndex-1;
                    isRight = false;
                    prev = edges[leftIndex];
                    nxt = edges[cur];
                }
                if (!isRight){
                    nxt = nxt.clone().switchDir();
                    prev = prev.clone().switchDir();
                }
                otherMax = Math.max(otherMax, examineEdge(nxt, prev));
            }
        }
        //System.out.println("left: "+left);
        //System.out.println("right: "+right);
        //System.out.println("otherMax: "+ otherMax);
        //System.out.println("Len: "+len);
        return Math.max(Math.max(left, right), otherMax);
    }

    private static int examineEdge(Edge nxt, Edge prev){
        int otherMax = 0;
        for (Edge e: al[nxt.u] ){
            if ( (!e.equals(nxt)) && (!e.equals(prev)) &&dp[e.u][e.v].len>otherMax){
                otherMax = dp[e.u][e.v].len;
                //System.out.println("Other: "+e.toString());
            }
        }
        int thisNxt = dp[nxt.u][nxt.v].len - nxt.w;
        if (otherMax>=thisNxt){
            return otherMax;
        }else{
            return -1;
        }
    }

    private static DFSRec dfs (LinkedList<Edge> record){
        Edge last = record.getLast();
        if (dp[last.u][last.v]!=null) return dp[last.u][last.v];
        LinkedList<Edge> expEdges = al[last.v];
        DFSRec res = new DFSRec(new LinkedList<>(), last.w);
        res.record.add(last);
        for (Edge e: expEdges){
            if (e.equals(last)) continue;
            record.add(e);
            DFSRec nxt = dfs(record);
            if (nxt.len+last.w>res.len){
                res.len = nxt.len+last.w;
                res.record.clear();
                res.record.add(last);
                res.record.addAll(nxt.record);
            }
            record.removeLast();
        }
        dp[last.u][last.v] = res;
        if (res.len>diaLen) {
            diaLen = res.len;
            diameters.clear();
            diameters.add(res);
        }else if (res.len == diaLen){
            Edge e = res.record.getLast();
            if (dp[e.v][e.u] == null) diameters.add(res);
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        al = new LinkedList[N+1];
        for (int i = 1; i<=N; i++){
            al[i] = new LinkedList<>();
        }
        for (int i = 1; i < N; i++){
            Edge[] edges = Edge.parseEdges(in.readLine());
            al[edges[0].u].add(edges[0]);
            al[edges[1].u].add(edges[1]);
            //System.out.println(edges[0].toString());
        }
        dp = new DFSRec[N+1][N+1];
        for (int i = 1; i<=N; i++){
            LinkedList<Edge> edges = al[i];
            if (edges.size() == 1){
                LinkedList<Edge> dIn = new LinkedList<>();
                dIn.add(edges.getFirst());
                dfs(dIn);
            }
        }
        //System.out.println(diaLen+" : "+diameters.size());
        //System.out.println(Arrays.deepToString(dp));
        int minEcc = Integer.MAX_VALUE;
        /*
        for (DFSRec dr: diameters){
            System.out.println(dr.toString());
        }*/

        for (DFSRec dr: diameters){
            //System.out.println(dr.toString());
            minEcc = Math.min(calcMinEcc(dr), minEcc);
        }
        System.out.println(minEcc);
    }

    private static class Edge{

        int u;
        int v;
        int w;

        public Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }

        public static Edge[] parseEdges(String line){
            String[] values = line.split(" ");
            Edge[] res = new Edge[2];
            int u = Integer.parseInt(values[0]);
            int v = Integer.parseInt(values[1]);
            int w = Integer.parseInt(values[2]);
            res[0] = new Edge(u, v, w);
            res[1] = new Edge(v, u, w);
            return res;
        }

        @Override
        public Edge clone(){
            return new Edge (this.u, this.v, this.w);
        }

        public Edge switchDir(){
            int temp = this.u;
            this.u = this.v;
            this.v = temp;
            return this;
        }

        @Override
        public boolean equals(Object obj) {
            Edge e = (Edge) obj;
            if ( (this. u == e.u && this.v == e.v) || (this.v == e.u&&this.u==e.v) ){
                return true;
            }else{
                return false;
            }
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "u=" + u +
                    ", v=" + v +
                    ", w=" + w +
                    '}';
        }
    }

    private static class DFSRec{

        LinkedList<Edge> record;
        int len;

        public DFSRec(LinkedList<Edge> record, int len) {
            this.record = record;
            this.len = len;
        }

        @Override
        public String toString() {
            String str = "";
            for (Edge rec: record){
                str+="->"+rec.toString();
            }
            return str;
        }
    }


    /*
    private static class Diameter{

        Edge[] edges;
        int len;

        public Diameter(LinkedList<Edge> list) {
            this.edges = new Edge[list.size()];
            this.len = 0;
            int counter = 0;
            for (Edge e: list){
                edges[counter] = e;
                len+=e.w;
                counter++;
            }
        }

    }
    */
}
