/**
 * 
 */
package com.demo.arraythings;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Given an array of integers, find the length of the longest sub-array with sum equals to 0.
 * 
 * @author marklapointe
 *
 */
public class Main {

	/**
	 * 
	 */
	public Main() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Start");
		
		// Ok, it was supposed to be an array,
		// I've been on a tree kick.
		
		Node root = new Node(10);
		root.ordered_insert(1);
		root.ordered_insert(-1);
		root.ordered_insert(11);
		root.ordered_insert(77);
		
		// Result should be [-1,1]
		Permutation permutation = root.findLongestPermutation(0);
		System.out.println(permutation.toString());
		
		// Result should be [-1,1,10,11]
		Permutation permutation2 = root.findLongestPermutation(21);
		System.out.println(permutation2.toString());
		
		// Result should be all of the numbers [-1,1,10,11,77]
		Permutation permutation3 = root.findLongestPermutation(98);
		System.out.println(permutation3.toString());
		
		// This will return a null, because there will not be any result found
		Permutation permutation4 = root.findLongestPermutation(1000000);
		if (permutation4 != null) {
			System.out.println("BAD: " + permutation4.toString());
		} else {
			System.out.println("OK for test 4");
		}
		
		System.out.println("End");
	}

}

class Permutation {
	Integer[] perm;
	Integer value;
	
	public Permutation(List<Integer> perm) {
		this.perm = new Integer[perm.size()];
		for ( int i = 0; i < perm.size(); i++) {
			this.perm[i] = perm.get(i);
		}
		this.sum();
	}
	
	@Override
	public String toString() {
		String result = "[";
		for (int i = 0; i < this.length(); i++) {
			result += this.perm[i];
			if (i < (this.length() -1)) {
				result += ",";
			}
		}
		result += "]";
		return result;
	}
	
	public Permutation(Integer[] perm) {
		this.perm = perm;
		this.sum();
	}
	
	private void sum() {
		if (this.perm != null) {
			Integer tmp = 0;
			for ( Integer i: perm) {
				tmp += i;
			}
			this.value = tmp;
		}
	}
	
	public int length() {
		if (perm != null) return perm.length;
		return 0;
	}
	
}


class Node {
	Integer data;
	Node left;
	Node right;

	public Node(int data) {
		this.data = data;
	}

	public Permutation findLongestPermutation (Integer target) {
		List<Permutation> permutations = new ArrayList<Permutation>();
		// An ARRAYlist gets formed here. -- so consider transforming the basic array to an arraylist is done here.
		this.getPermutations(permutations,new ArrayList<Integer>(),this.getInOrderInteger());
		Permutation result = this.findLongest(permutations, target);
		return result;
		
	}
	
	private Permutation findLongest (List<Permutation> permutations, Integer target) {
		Permutation result = null;
		for (Permutation p: permutations) {
			if (p.value == target) {
				if (result == null) result = p;
				if (p.length() > result.length()) {
					result = p;
				}
			}
		}
		return result;
	}
	
	public void getPermutations(List<Permutation> permutations, List<Integer> lead, List<Integer> remainder ) {
		for (int i = 0; i < remainder.size(); i++) {
			// Pull a lead
			Integer leadv = remainder.get(i);
			List<Integer> nLead = new ArrayList<>();
			List<Integer> nRemainder = new ArrayList<>();
			nLead.addAll(lead);
			nRemainder.addAll(remainder);
			nLead.add(leadv);
			nRemainder.remove(i);
			permutations.add(new Permutation(nLead));
			if (nRemainder.size() > 0) this.getPermutations(permutations, nLead, nRemainder);
		}
		
	
	}
	
	public Boolean hasValue(Integer value) {
		if (this.data == value) {
			return true;
		}
		if (this.data < value && this.right != null) return this.right.hasValue(value);
		if (this.data > value && this.left != null) return this.left.hasValue(value);
		return false;
	}
	
	public Boolean findTarget(Integer target) {
		Boolean retVal = false;
		List<Node> nodes = this.getInOrder();
		for (int i = 0; i <= (nodes.size()-1) && !retVal; i++) {
			Integer targetValue = target - nodes.get(i).data;
			retVal = this.hasValue(targetValue);
		}
		
		return retVal;
	}
	
	private List<Node> getInOrder() {
		List<Node> retVal = new ArrayList<Node>();
		if (this.left!= null) retVal.addAll(this.left.getInOrder());
		retVal.add(this);
		if (this.right!= null) retVal.addAll(this.right.getInOrder());
		return retVal;
	}

	private List<Integer> getInOrderInteger() {
		List<Integer> retVal = new ArrayList<Integer>();
		if (this.left!= null) retVal.addAll(this.left.getInOrderInteger());
		retVal.add(this.data);
		if (this.right!= null) retVal.addAll(this.right.getInOrderInteger());
		return retVal;
	}
	
	public void ordered_insert(Integer data) {
		if (this.data == null) {
			this.data = data;
			return;
		} else if (data > this.data) {
			this.ordered_insert_right(data);
			return;
		} else if (data < this.data) {
			this.ordered_insert_left(data);
			return;
		}
		return;
	}

	private void ordered_insert_right (Integer data) {
		if (this.right == null) {
			this.right = new Node(data);
		} else {
			this.right.ordered_insert(data);
		}
	}
	
	private void ordered_insert_left (Integer data) {
		if (this.left == null) {
			this.left = new Node(data);
		} else {
			this.left.ordered_insert(data);
		}
	}
	
}
