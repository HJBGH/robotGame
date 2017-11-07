package robotGame.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import robotGame.model.*;
import robotGame.model.node.Node;

import org.junit.Test;

public class ModelTests {
	
	@Test
	public void test1()
	{
		//test adding one neighbour and getting the neighbour array
		Node node = new Node(1, 1);
		Node neighbour = new Node(1, 2);
		node.addNeighbour(neighbour);
		ArrayList<Node> neighbours = node.getNeighbours();
		if(!neighbours.contains(neighbour))
		{
			fail("Fugg :-D");
		}
	}
	
	@Test
	public void test2()
	{
		//test removing one neighbour
		Node node = new Node(1, 1);
		Node neighbour = new Node(1, 2);
		node.addNeighbour(neighbour);
		node.deleteNeighbour(neighbour);
		ArrayList<Node> neighbours = node.getNeighbours();
		if(neighbours.contains(neighbour))
		{
			fail("Not yet implemented");
		}
	}
	
	@Test
	public void test3()
	{
		//test adding more than 4 neighbours, adding more than 4 is correct behaviour
		Node node = new Node(0, 0);
		Node node1 = new Node(1, 1);
		Node node2 = new Node(2, 1);
		Node node3 = new Node(1, 2);
		Node node4 = new Node(3, 2);
		node.addNeighbour(node1);
		node.addNeighbour(node4);
		node.addNeighbour(node2);
		node.addNeighbour(node3);
		if(!node.getNeighbours().contains(node1))
		{
			fail("Neighbour not added");
		}
		if(!node.getNeighbours().contains(node2))
		{
			fail("Neighbour not added");
		}
		if(!node.getNeighbours().contains(node3))
		{
			fail("Neighbour not added");
		}
		if(!node.getNeighbours().contains(node4))
		{
			fail("Neighbour not added");
		}
	}
	
	@Test
	public void test4()
	{
		//test removing neighbours on node with no neighbours
		//the underlying structure for storing neighbours is
		//an arraylist, so this test isn't really for anything but sanity/exception checking.
		Node node = new Node(1, 1);
		Node neighbour = new Node(1, 2);
		node.deleteNeighbour(neighbour);
		ArrayList<Node> neighbours = node.getNeighbours();
		if(neighbours.contains(neighbour))
		{
			fail("Something went wrong trying to remove from an empty neighbour list");
		}		
	}
	

}
