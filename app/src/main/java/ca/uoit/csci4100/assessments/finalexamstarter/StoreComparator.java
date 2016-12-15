package ca.uoit.csci4100.assessments.finalexamstarter;

import java.util.Comparator;

public class StoreComparator implements Comparator<Store> {
	@Override
	public int compare( Store a, Store b){
		if( a.distance > b.distance)
			return 1;
		else if( a.distance < b.distance)
			return -1;
		else return 0;}
}
