package ca.ziddia.transmutr;

public class TransmutrTransmuteCase {
	int material;
	int tool;
	int result;
	double chance;
	public TransmutrTransmuteCase(int toTrans, int toUse, int res, double c) {
		material = toTrans;
		tool = toUse;
		result = res;
		chance = c;		
	}
	
	public int getMaterial() {
		return material;
	}
	
	public int getTool() {
		return tool;
	}
	
	public int getResult() {
		return result;
	}
	
	public double getChance() {
		return chance;
	}

}
