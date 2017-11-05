class Citizen {

   // Properties of the class...
   private String name;
   private int    salary;
   private int    savings;
   private int    loan;

   // Constructor of the class...
   public Citizen(String aName, int aSalary, int aSavings, int aLoan) {
      name    = aName;
      salary  = aSalary;
      savings = aSavings;
      loan    = aLoan;
   }
   public Citizen(String name) {
      name    = name;
      salary  = 0;
      savings = 0;
      loan    = 0;
   }

   // Methods of the class...
   public int getSalary() {
      return salary;
   }
   public void setSalary(int aSalary) {
      salary = aSalary;
   }
   public void salaryRise(int aRise) {
      salary += aRise;
   }
   public int netWorth() {
      return savings - loan;
   }
   public String toString(){
    return name + " " + salary + " " + savings + " " + loan;
   }
}

class CitizenTest {

   // The main method is the point of entry into the program...
   public static void main(String[] args) {

      Citizen e = new Citizen("Ernie", 50000, 2000,   0);
      Citizen b = new Citizen("Bert", 100000,10000,5000);

      // Q2 Raise salary of Ernie by 10.000
      e.salaryRise(10000);
      // Q3 Return Ernie's netWorth
      System.out.println("Ernie's netWorth is: " + e.netWorth());
      // Q4
      /* it is allowed to compile so that other values and for example additions
      are stil done in the function. For example the change of salary in salaryRise()
      */
      e.getSalary();
      // Q5
      System.out.println("Ernie's info is: " + e.toString());
      // Q6
      Citizen f = new Citizen("Fred");
   }
}
