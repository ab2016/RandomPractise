
public class TowerOfHanoi {

	public static void main(String[] args) {
		
		moveRecursively(4, "A", "B", "C");
		moveIteratively(4, "A", "C", "B");
		
		
		moveRecursively(3, "A", "B", "C");
		moveIteratively(3, "A", "B", "C");
	}

	// using recursion
	static void moveRecursively(int n, String source, String dest, String helper) {

		if (n > 0) {
			moveRecursively(n - 1, source, helper, dest);
			System.out.println("Moving disk from " + source + " to " + dest);
			moveRecursively(n - 1, helper, dest, source);
		}
	}

	// using iteration 
	static void moveIteratively(int n, String source, String dest, String helper) {

		DiscStack sourceStack = new DiscStack(source);
		DiscStack destStack = new DiscStack(dest);
		DiscStack helpStack = new DiscStack(helper);

		for (int i = n; i > 0; i--) {
			sourceStack.push(i);
		}

		// rule: allowable move between A B, A C, B C respectively
		while (!(sourceStack.isEmpty() && (destStack.size() == n || helpStack.size() == n))) {

			move(sourceStack, destStack);
			if (destStack.size() == n)
				break;
			move(sourceStack, helpStack);
			move(destStack, helpStack);
		}

	}

	private static void move(DiscStack r, DiscStack t) {
		if (r.isEmpty() && t.isEmpty())
			return;
		else if (r.isEmpty()) {
			int disc = t.pop();
			System.out.println("Moving disc" + disc + " from " + t.getName() + " to " + r.getName());
			r.push(disc);
		} else if (t.isEmpty()) {
			int disc = r.pop();
			System.out.println("Moving disc" + disc + " from " + r.getName() + " to " + t.getName());
			t.push(disc);

		} else if (t.peek() < r.peek()) {
			int disc = t.pop();
			System.out.println("Moving disc" + disc + " from " + t.getName() + " to " + r.getName());
			r.push(disc);
		} else {
			int disc = r.pop();
			System.out.println("Moving disc" + disc + " from " + r.getName() + " to " + t.getName());
			t.push(disc);
		}
	}

}

// Needed for iteration only
class DiscStack {

	private String nameOfStack;
	private Node top;
	private int length;

	DiscStack(String name) {
		this.nameOfStack = name;
		this.length = 0;
		this.top = null;
	}

	public String getName() {
		return nameOfStack;
	}

	public boolean isEmpty() {
		return (length == 0);

	}

	void push(int i) {
		Node n = new Node(i);
		n.next = top;
		top = n;
		length++;
	}

	Integer pop() {
		if (top == null) {
			return null;
		}
		Node temp = top;
		top = top.next;
		length--;
		return temp.data;
	}

	int peek() {
		return top.data;

	}

	int size() {
		return length;
	}
}

class Node {

	Integer data;
	Node next;

	public Node(Integer i) {
		this.data = i;
		this.next = null;
	}

}