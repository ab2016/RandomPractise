import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

// https://leetcode.com/problems/course-schedule/
public class CycleDetection {

	public static void main(String[] args) {
		int[][] prerequisites = { { 0, 1 }, { 1, 2 } };

		System.out.println(canFinish(3, prerequisites));
		System.out.println(cycleDetection(3, prerequisites));
	}

	// Slow using general graph algorithm for cycle detection
	public static boolean cycleDetection(int numCourses, int[][] prerequisites) {

		if (numCourses == 0 || prerequisites.length == 0)
			return true;

		HashMap<Integer, HashSet<Integer>> courseToPrerequisites = new HashMap<Integer, HashSet<Integer>>();

		HashSet<Integer> all = new HashSet<Integer>();
		Queue<Integer> ready = new LinkedList<Integer>();

		// Building graph (outdegree) and find out the independent course
		for (int[] prerequisite : prerequisites) {

			int dependentCourse = prerequisite[0];
			Integer probableReadyCourse = new Integer(prerequisite[1]);
			HashSet<Integer> list = courseToPrerequisites.get(dependentCourse);
			if (list == null) {
				list = new HashSet<Integer>();
			}
			list.add(probableReadyCourse);
			courseToPrerequisites.put(dependentCourse, list);
			all.add(prerequisite[0]);
			all.add(prerequisite[1]);
		}

		for (int[] prerequisite : prerequisites) {

			if (!courseToPrerequisites.containsKey(prerequisite[1])) {
				if (!ready.contains(prerequisite[1]))
					ready.add(prerequisite[1]);
			}

		}

		int totalSize = all.size();

		if (ready.isEmpty())
			return false;
		int cnt = 0;
		while (!ready.isEmpty()) {
			cnt++;
			Integer c = ready.poll();

			HashSet<Integer> nullKeys = new HashSet<Integer>();
			for (Entry<Integer, HashSet<Integer>> entry : courseToPrerequisites.entrySet()) {
				if (entry.getValue().contains(c))
					entry.getValue().remove(c);
				if (entry.getValue().isEmpty()) {
					ready.add(entry.getKey());
					nullKeys.add(entry.getKey());
				}
			}
			for (Integer k : nullKeys)
				courseToPrerequisites.remove(k);
		}

		return cnt == totalSize;
	}

	// performance improvement specifically for the question
	// Use the array instead of HashMap as course is an integer.
	// Just track indegree calculation instead of edges.
	public static boolean canFinish(int numCourses, int[][] prerequisites) {
		if (numCourses == 0 || prerequisites.length == 0)
			return true;

		// Convert graph presentation from edges to indegree of adjacent list.
		int countOfDependentCourses[] = new int[numCourses];
		for (int i = 0; i < prerequisites.length; i++)
			countOfDependentCourses[prerequisites[i][0]]++;

		Queue<Integer> queue = new LinkedList<Integer>();
		for (int i = 0; i < numCourses; i++)
			if (countOfDependentCourses[i] == 0)
				queue.add(i);

		int cnt = 0;
		while (!queue.isEmpty()) {
			int prerequisite = queue.remove();
			cnt++;
			for (int i = 0; i < prerequisites.length; i++) {
				if (prerequisites[i][1] == prerequisite) {
					countOfDependentCourses[prerequisites[i][0]]--;
					// checks all the dependent courses have already been taken
					if (countOfDependentCourses[prerequisites[i][0]] == 0) {
						queue.add(prerequisites[i][0]);
					}
				}
			}
		}

		return (cnt == numCourses);
	}

}
