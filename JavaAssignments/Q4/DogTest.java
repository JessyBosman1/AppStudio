class Flea {

   // Properties of the class...
   public String name;

   // Constructor of the class...
   public Flea(String aName) {
      name = aName;
   }

   // Methods of the class...
   public String toString() {
      return "I am a flea called " + name;
   }

}

class Dog {

   // Properties of the class...
   public String name;
   private int    age;
   public Flea   dogsFlea;

   // Constructor of the class...
   public Dog(String aName, int anAge, Flea aFlea) {
      name     = aName;
      age      = anAge;
      dogsFlea = aFlea;
   }
   public String toString() {
      return "I am a dog called " + name + ", I am " + age +
      " years old, and have a Flea called " + dogsFlea.name;
   }

}

class DogOwner {

   // Properties of the class...
   private String name;
   private int    salary;
   public Dog    ownersDog;

   // Constructor of the class...
   public DogOwner(String aName, int aSalary, Dog aOwnersDog) {
      name      = aName;
      salary    = aSalary;
      ownersDog = aOwnersDog;
   }

   public String toString() {
      return "I am called " + name + ", my salary is " + salary +
      " and i have a dog called " + ownersDog.name;
   }

}

class DogTest {

   // The main method is the point of entry into the program...
   public static void main(String[] args) {
     Flea Pop = new Flea("Pop");
     Flea Squeak = new Flea("Squeak");
     Flea Zip = new Flea("Zip");

     Dog Rex = new Dog("Rex", 8, Pop);
     Dog Jimbo = new Dog("Jimbo", 4, Squeak);
     Dog Fido = new Dog("Fido", 6, Zip);

     DogOwner Angus = new DogOwner("Angus", 100, Rex);
     DogOwner Brian = new DogOwner("Brian", 200, Jimbo);
     DogOwner Charles = new DogOwner("Charles", 300, Fido);

     System.out.println(Rex.toString());
     System.out.println(Jimbo.toString());
     System.out.println(Fido.toString());
     System.out.println(" ");
     System.out.println(Angus.toString());
     System.out.println(Brian.toString());
     System.out.println(Charles.toString());
     System.out.println(" ");
     System.out.println(Angus.ownersDog.dogsFlea.toString());
   }

}
