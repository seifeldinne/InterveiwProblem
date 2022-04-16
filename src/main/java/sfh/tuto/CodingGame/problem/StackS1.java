package sfh.tuto.CodingGame.problem;

import java.util.EmptyStackException;

public class StackS1 {
	private Object stack[];
	private int top;

	public StackS1(int len) {
		stack = new Object[len];
		top = 0;
	}

	public synchronized Object pop() {
		if (0 < top) {
			top--;
			return stack[top];
		}
		throw new EmptyStackException();
	}

	public synchronized void push(Object o) {
		if (top < stack.length) {
			stack[top] = o;
			top++;
			return;
		}
		throw new EmptyStackException();
	}

	public synchronized void print() {
		for (int i = 0; i < top; i++) {
			System.out.println(stack[i]);
		}
	}
}
