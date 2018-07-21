package org.quetzaco.archives.util.boot;

public enum FlowsType {
    LENDING("lending"),DESTROY("destroy"),DELIVER("deliver"),ASSIST("assist");

    private String name;

    FlowsType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static FlowsType val(String type){
        for(FlowsType t : FlowsType.values()){
            if(t.getName().equals(type)){
                return t;
            }
        }
        return null;
    }
}
