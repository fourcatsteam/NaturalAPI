package FourCats.DataStructure;

public class Dependency {
    private String gov;
    private String dep;
    private String relation;

    public Dependency(String g, String d, String r){
        this.gov=g;
        this.dep=d;
        this.relation=r;
    }

    public String getGov() { return this.gov;}
    public String getDep() { return this.dep;}
    public String getRelation() { return this.relation;}
}
