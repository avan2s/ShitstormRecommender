package shitstorm.enums;

public enum NodeFocus {
	DECISION("decision"),
	GOAL ("goal"),
	TASK ("task"),
	VARIABLE("variable"),
	UNKNOWN("unknown");
	
	private final String name;       

    private NodeFocus(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}
