import java.util.*;
import java.util.regex.*;

class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;

    public Employee(int id, int importance, List<Integer> subordinates) {
        this.id = id;
        this.importance = importance;
        this.subordinates = subordinates;
    }
}

public class EmpImportant {

    static int getImportance(List<Employee> employees, int id) {
        int sum = 0;
        Map<Integer, Employee> map = new HashMap<>();
        for (Employee employee : employees) {
            map.put(employee.id, employee);
        }
        Queue<Employee> queue = new LinkedList<>();
        queue.offer(map.get(id));
        while (!queue.isEmpty()) {
            Employee current = queue.poll();
            sum += current.importance;
            for (int subordinate : current.subordinates) {
                queue.offer(map.get(subordinate));
            }
        }
        return sum;
    }

    public static Employee loadData(String text) {
        Pattern digits = Pattern.compile("\\d+");
        Matcher matcher = digits.matcher(text);
        List<Integer> data = new ArrayList<>();
        while (matcher.find()) {
            data.add(Integer.parseInt(text.substring(matcher.start(), matcher.end())));
        }
        return new Employee(data.get(0), data.get(1), data.subList(2, data.size()));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine().trim();
        Pattern pattern = Pattern.compile("\\[[^\\[\\]]*\\[[^\\[\\]]*\\]\\]");
        Matcher matcher = pattern.matcher(str);
        List<Employee> employees = new LinkedList<Employee>();
        while (matcher.find()) {
            String text = str.substring(matcher.start(), matcher.end());
            Employee employee = loadData(text);
            employees.add(employee);
        }
        int id = Integer.parseInt(in.nextLine().trim());
        int res;
        res = getImportance(employees, id);
        System.out.println(String.valueOf(res));
        in.close();
    }
}
