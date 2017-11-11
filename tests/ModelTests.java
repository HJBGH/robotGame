package robotGame.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import robotGame.model.*;
import robotGame.model.node.Node;

import org.junit.Test;

public class ModelTests {
	
	//I'm going to need tests for observerObservable related methods
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
	
	@Test
	public void test5()
	{
		//test adding and removing a prize from a node
		Node node = new Node(1, 1);
		Prize prize = new Prize();
		node.addPrize(prize);
		if(node.hasPrize() == false)
			fail("oh god this is so simple how could it fail");
		Prize gotPrize = node.removePrize();
		if(!prize.equals(gotPrize))
			fail("Something went wrong getting the prize");
	}
	
	@Test
	public void testBadModelConstructorInput()
	{
		try{
			Model model = new Model(0, 0, null);
			fail("The model constructor should've failed.");
		} catch (ModelException e) {
			
		}
	}
	
	@Test
	public void test_model_nodes()
	{
		//test adding and removing nodes in a model.
		Model testModel = null;
		try {
			testModel = new Model(10, 10, null);
		} catch (ModelException e) {
			fail("Model constructor failed");
			e.printStackTrace();
		}
		testModel.addNode(0,0);
		testModel.addNode(9, 9);
		testModel.addNode(9, 0);
		testModel.addNode(0, 9);
	}
	
	public void testAddPrize() throws ModelException
	{
		Model testModel = null;
		try {
			testModel = new Model(10, 10, null);
		} catch (ModelException e)
		{
			throw (e);
		}
		testModel.addPrize(5, 5);
	}
	
	public void testRemovePrize() throws Exception
	{
		Model testModel = null;
		try
		{
			testModel = new Model(10, 10, null);
		} catch (Exception e)
		{
			throw (e);
		}
		testModel.addPrize(9, 9);
		Prize testPrize = testModel.removePrize(0, 0);
		if(testPrize != null)
		{
			fail("Prize should be null");//you're trying to remove a prize where there is none.
		}
		testPrize = testModel.removePrize(9, 9);
		if(testPrize == null)
		{
			fail("Prize shouldn't be null");
		}
	}
	
	public void test8()
	{
		//test adding and removing destinations in a model.
	}
	
	public void test9()
	{
		//test solver. TODO: think up tests for the solver
	}

}
