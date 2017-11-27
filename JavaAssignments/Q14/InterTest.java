interface IsVehicle {
   public String getName();
   public void   setName(String aName);
   public int    getMaxPassengers();
   public void   setMaxPassengers(int aMaxPassengers);
   public int    getMaxSpeed();
   public void   setMaxSpeed(int aMaxSpeed);
}

interface IsLandVehicle extends IsVehicle {
   public int  getNumWheels();
   public void setNumWheels(int aNumWheels);
   public void drive();
}

interface IsSeaVessel extends IsVehicle {
   public int  getDisplacement();
   public void setDisplacement(int aDisplacement);
   public void launch();
}

interface IsEmergency extends IsVehicle{
  public void soundSiren();
}


class Hovercraft implements IsLandVehicle, IsSeaVessel {

   // Properties...
   private String name;
   private int    maxPassengers;
   private int    maxSpeed;
   private int    numWheels;
   private int    displacement;

   // Constructor...
   public Hovercraft(String aName) {
      name          = aName;
      maxPassengers = 40;
      maxSpeed      = 30;
      numWheels     = 0;
      displacement  = 0;
   }

   // IsVehicle methods...
   public String getName() {
      return name;
   }
   public void setName(String aName) {
      name = aName;
   }
   public int getMaxPassengers() {
      return maxPassengers;
   }
   public void setMaxPassengers(int aMaxPassengers) {
      maxPassengers = aMaxPassengers;
   }
   public int getMaxSpeed() {
      return maxSpeed;
   }
   public void setMaxSpeed(int aMaxSpeed) {
      maxSpeed = aMaxSpeed;
   }

   // IsLandVehicle methods...
   public int getNumWheels() {
      return numWheels;
   }
   public void setNumWheels(int aNumWheels) {
      numWheels = aNumWheels;
   }
   public void drive() {
      System.out.println("Brmmm Brmmm!");
   }

   // IsSeaVessel methods...
   public int getDisplacement() {
      return displacement;
   }
   public void setDisplacement(int aDisplacement) {
      displacement = aDisplacement;
   }
   public void launch() {
      System.out.println("Hover-craft launched!");
   }

   // Hovercraft methods...
   public void enterLand() {
      System.out.println("Hover-craft now travelling on land!");
   }
   public void enterSea() {
      System.out.println("Hover-craft now travelling on the sea!");
   }
}

class Jeep implements IsLandVehicle {

   // Properties...
   private String name;
   private int    maxPassengers;
   private int    maxSpeed;
   private int    numWheels;

   // Constructor...
   public Jeep(String aName) {
      name = aName;
      maxPassengers = 5;
      maxSpeed      = 180;
      numWheels     = 4;
   }

   // IsVehicle methods...
   public String getName() {
      return name;
   }
   public void setName(String aName) {
      name = aName;
   }
   public int getMaxPassengers() {
      return maxPassengers;
   }
   public void setMaxPassengers(int aMaxPassengers) {
      maxPassengers = aMaxPassengers;
   }
   public int getMaxSpeed() {
      return maxSpeed;
   }
   public void setMaxSpeed(int aMaxSpeed) {
      maxSpeed = aMaxSpeed;
   }

   // IsLandVehicle methods...
   public int getNumWheels() {
      return numWheels;
   }
   public void setNumWheels(int aNumWheels) {
      numWheels = aNumWheels;
   }
   public void drive() {
      System.out.println("Jeep driving!");
   }

   // Jeep methods...
   public void soundHorn() {
      System.out.println("Honk Honk!");
   }
}

class Frigate implements IsSeaVessel {

   // Properties...
   private String name;
   private int    maxPassengers;
   private int    maxSpeed;
   private int    displacement;

   // Constructor...
   public Frigate(String aName) {
      name          = aName;
      maxPassengers = 100;
      maxSpeed      = 12;
      displacement  = 10000;
   }

   // IsVehicle methods...
   public String getName() {
      return name;
   }
   public void setName(String aName) {
      name = aName;
   }
   public int getMaxPassengers() {
      return maxPassengers;
   }
   public void setMaxPassengers(int aMaxPassengers) {
      maxPassengers = aMaxPassengers;
   }
   public int getMaxSpeed() {
      return maxSpeed;
   }
   public void setMaxSpeed(int aMaxSpeed) {
      maxSpeed = aMaxSpeed;
   }

   // IsSeaVessel methods...
   public int getDisplacement() {
      return displacement;
   }
   public void setDisplacement(int aDisplacement) {
      displacement = aDisplacement;
   }
   public void launch() {
      System.out.println("Frigate launched!");
   }

   // Frigate methods...
   public void fireGun() {
      System.out.println("Boom!");
   }
}

class PoliceCar implements IsEmergency, IsLandVehicle {

  private String name;
  private int maxPassengers;
  private int maxSpeed;
  private int numWheels;

  public PoliceCar(String aName) {
    name = aName;
    maxPassengers = 4;
    maxSpeed = 250;
    numWheels = 4;
    }
// IsVehicle methods...
  public String getName() {
    return name;
  }
  public void setName(String aName) {
    name = aName;
  }
  public int getMaxPassengers() {
    return maxPassengers;
  }
  public void setMaxPassengers(int aMaxPassengers) {
    maxPassengers = aMaxPassengers;
  }
  public int getMaxSpeed() {
    return maxSpeed;
  }
  public void setMaxSpeed(int aMaxSpeed) {
    maxSpeed = aMaxSpeed;
  }

// IsLandVehicle methods...
  public int getNumWheels() {
      return numWheels;
    }
    public void setNumWheels(int aNumWheels) {
      numWheels = aNumWheels;
    }
    public void drive() {
      System.out.println("Police car driving!");
    }
    public void soundSiren() {
      System.out.println("whoop whoop!");
    }
}

class InterTest {

   public static void main(String[] args) {

      IsVehicle[] myArray = new IsVehicle[5];

      myArray[0] = new Jeep("Fred's Jeep");
      myArray[1] = new Jeep("Frank's Jeep");
      myArray[2] = new Hovercraft("Sue's Hover-craft");
      myArray[3] = new Frigate("Money Waster");
      myArray[4] = new PoliceCar("SquadCar");

      for (int i=0; i<myArray.length; i++) {

         System.out.println(myArray[i].getName());

         if (myArray[i] instanceof IsLandVehicle) {
            IsLandVehicle lv = (IsLandVehicle)myArray[i];
            lv.drive();
            }
         if (myArray[i] instanceof IsEmergency) {
            IsEmergency em = (IsEmergency)myArray[i];
            em.soundSiren();
            }


      }
   }
}
