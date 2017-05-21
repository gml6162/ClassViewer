package viewerPackage;

import java.util.ArrayList;

public class ClassInfo {

	//FIELDS
	private String name;
	private ArrayList<String> methodNames;
	private ArrayList<String> methodTypes;
	private String methodAccess;
	private ArrayList<String> valueNames;
	private ArrayList<String> valueTypes;
	private String valueAccess;

	//CONSTRUCTOR
	ClassInfo() {
		methodNames = new ArrayList<>();
		methodTypes = new ArrayList<>();

		valueNames = new ArrayList<>();
		valueTypes = new ArrayList<>();
	}

	//METHODS
	public void setName(String name) { this.name = name; }
	public void setMethodAccess(String access) {
		methodAccess = access;
	}

	public void setValueAccess(String access) {
		valueAccess = access;
	}

	public void setMethodInfo(String name, String type) {
		methodNames.add(name);
		methodTypes.add(type);
	}

	public void setValueInfo(String name, String type) {
		valueNames.add(name);
		valueTypes.add(type);
	}

	public String getName() { return name; }
	public String getMethodAccess() { return methodAccess; }
	public String getValueAccess() { return valueAccess; }
	public ArrayList<String> getMethodNames() { return methodNames; }
	public ArrayList<String> getMethodTypes() { return methodTypes; }
	public ArrayList<String> getValueNames() { return valueNames; }
	public ArrayList<String> getValueTypes() { return valueTypes; }
	
}