import java.io.*;
import java.util.*;

// ===== MAIN CLASS =====
public class StudentRecordProcessor {

    private final List<Student> students = new ArrayList<>();
    private double averageScore;
    private Student highestStudent;

    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        String path = "data/students.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);

                    // проверка через custom exception
                    if (score < 0 || score > 100) {
                        throw new InvalidScoreException("Score out of range: " + score);
                    }

                    Student student = new Student(name, score);
                    students.add(student);

                    System.out.println("Valid: " + line);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);
                } catch (InvalidScoreException e) {
                    System.out.println("Invalid score: " + line);
                } catch (Exception e) {
                    System.out.println("Error: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("IO error!");
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        if (students.isEmpty()) return;

        double sum = 0;
        highestStudent = students.get(0);

        for (Student s : students) {
            sum += s.getScore();

            if (s.getScore() > highestStudent.getScore()) {
                highestStudent = s;
            }
        }

        averageScore = sum / students.size();

        // сортировка по убыванию
        students.sort((a, b) -> b.getScore() - a.getScore());
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        String path = "output/report.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

            bw.write("Average: " + averageScore);
            bw.newLine();

            bw.write("Highest: " + highestStudent.getName() + " - " + highestStudent.getScore());
            bw.newLine();
            bw.newLine();

            bw.write("All students (sorted):");
            bw.newLine();

            for (Student s : students) {
                bw.write(s.toString());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing file!");
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}


// ===== CUSTOM EXCEPTION =====
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}


// ===== STUDENT CLASS =====
class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return name + " - " + score;
    }
}
