package ca.ziddia.transmutr;

public class TransmutrTransmuteCase {
	int material;//stores the key/ID of material that is transmute-able
	int tool;//the item used when right clicking
	int result;//the ID of the resulting block
	double chance;//the chance of success
	public TransmutrTransmuteCase(int toTrans, int toUse, int res, double c) {
		material = toTrans;
		tool = toUse;
		result = res;
		chance = c;		
	}
	
	//returns key, probably never used.
	public int getMaterial() {
		return material;
	}
	
	//returns tool
	public int getTool() {
		return tool;
	}
	
	//returns resulting block
	public int getResult() {
		return result;
	}
	
	//returns probability
	public double getChance() {
		return chance;
	}

}
