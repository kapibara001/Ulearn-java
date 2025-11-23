import java.util.ArrayList;

public class Program {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }

    public class School {
        private ArrayList<Pupil> PupilList = new ArrayList<>();
        private ArrayList<Teacher> TeacherList = new ArrayList<>();

        public void add(Pupil pupil) {
            if (!pupil.isInSchool) {
                PupilList.add(pupil);
                pupil.isInSchool = true;
            }
        }

        public void add(Teacher teacher) {
            if (!teacher.isInSchool) {
                TeacherList.add(teacher);
                teacher.isInSchool = true;
            }
        }

        public void leave(Pupil pupil) {
            if (pupil.isInSchool) {
                PupilList.remove(pupil);
                pupil.isInSchool = false;
            }
        }

        public void leave(Teacher teacher) {
            if (teacher.isInSchool) {
                TeacherList.remove(teacher);
                teacher.isInSchool = false;
            }
        } 

        public String getPeoplesInSchool() {
            StringBuilder sb = new StringBuilder();
            sb.append("В школе:\n");

            for (Pupil p : PupilList) {
                sb.append(p.toString()).append("\n");
            }

            for (Teacher t : TeacherList) {
                sb.append(t.toString()).append("\n");
            }

            return sb.toString().trim();
        }
    }

    public abstract class Human {
        protected final String name;
        protected final String surname;
        protected boolean isInSchool = false;

        public Human(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public void goInSchool() {
            isInSchool = true;
        }

        public void outFromSchool() {
            isInSchool = false;
        }

        public boolean isInSchool() {
            return isInSchool;
        }
    }

    public class Pupil extends Human {
        private final int entryIntoSchool;

        public Pupil(String name, String surname, int entryIntoSchool) {
            super(name, surname);
            this.entryIntoSchool = entryIntoSchool;
        }

        public int getYear() {
            return entryIntoSchool; // чтобы тест getYear() работал
        }

        @Override
        public String toString() {
            return name + " " + surname + " " + entryIntoSchool;
        }
    }

    public class Teacher extends Human {
        /*напишите класс Teacher
        формат вывода toString(): имя фамилия предмет стаж работы
        */
        private String subject;
        private int workedYear;

        public Teacher(String name, String surname, String subject, int workedYear) {
            super(name, surname);
            this.subject = subject;
            this.workedYear = workedYear;
        }

        @Override
        public String toString() {
            return name + " " + surname + " " + subject + " " + workedYear; 
        }
    }
}