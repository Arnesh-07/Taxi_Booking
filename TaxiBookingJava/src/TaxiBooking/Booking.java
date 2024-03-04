package TaxiBooking;

import java.util.*;

public class Booking {

    public static List<Taxi> getFreeTaxis(List<Taxi> taxis,int pickupTime,char pickupPoint)
    {
        List<Taxi> freeTaxis = new ArrayList<Taxi>();

        for(Taxi t:taxis)
        {
            if(t.freeTime <= pickupTime && (Math.abs((t.currentSpot - '0') - (pickupPoint - '0')) <= pickupTime - t.freeTime))
                freeTaxis.add(t);
        }
        return freeTaxis;
    }

    public static List<Taxi> createTaxis(int n)
    {
        List<Taxi> taxis=new ArrayList<Taxi>();
        for(int i=1;i<=n;i++)
        {
            Taxi t=new Taxi();
            taxis.add(t);
        }
        return taxis;
    }


    public static void main(String[] args) {

        //create 4 taxis
        List<Taxi> taxis = createTaxis(4);

        Scanner s = new Scanner(System.in);
        int id = 1;
        boolean looper=true;


        while (looper) {
            System.out.println("Welcome to Zeus Taxi Agency");
            System.out.println("1 - > Book Taxi");
            System.out.println("2 - > Print Taxi details");
            System.out.println("3 - > Exit");
            int choice = s.nextInt();
            switch (choice)
            {
                case 1:
                {
                    int customerID=id;

                    System.out.println("Enter Pickup point");
                    char pickupPoint = s.next().charAt(0);
                    System.out.println("Enter Drop point");
                    char dropPoint = s.next().charAt(0);
                    System.out.println("Enter Pickup time");
                    int pickupTime = s.nextInt();

                    if(pickupPoint < 'A' || dropPoint > 'F' || pickupPoint > 'F' || dropPoint < 'A')
                    {
                        System.out.println("Valid pickup and drop are A, B, C, D, E, F. Exitting");
                        return;
                    }

                    List<Taxi> freeTaxis=getFreeTaxis(taxis,pickupTime,pickupPoint);

                    if(freeTaxis.size() == 0)
                    {
                        System.out.println("No Taxi can be alloted. Exitting");
                        return;
                    }

                    bookTaxi(id,pickupPoint,dropPoint,pickupTime,freeTaxis);
                    id++;
                    break;
                }
                case 2:
                {
                    for(Taxi t : taxis)
                        t.printTaxiDetails();
                    for(Taxi t : taxis)
                        t.printDetails();
                    break;
                }
                case 3:
                    looper=false;
            }
        }
    }

    public static void bookTaxi(int customerID,char pickupPoint,char dropPoint,int pickupTime,List<Taxi> freeTaxis)
    {
        int min=999;
        int distanceBetweenpickUpandDrop = 0;
        int earning = 0;
        int nextfreeTime = 0;
        char nextSpot = 'Z';
        Taxi bookedTaxi = null;
        String tripDetail = "";

        for(Taxi t:freeTaxis)
        {
            int distanceBetweenCustomerAndTaxi= Math.abs((t.currentSpot-'0')-(pickupPoint-'0'));
            if(distanceBetweenCustomerAndTaxi<min)
            {
                bookedTaxi=t;
                distanceBetweenpickUpandDrop=Math.abs((dropPoint - '0') - (pickupPoint - '0')) * 15;
                earning=100+(distanceBetweenpickUpandDrop-5)*10;
                int dropTime=pickupTime+distanceBetweenpickUpandDrop/15;
                nextfreeTime=dropTime;
                nextSpot=dropPoint;
                tripDetail = customerID + "               " + customerID + "          " + pickupPoint +  "      " + dropPoint + "       " + pickupTime + "          " +dropTime + "           " + earning;

            }
        }
        bookedTaxi.setDetails(true,nextSpot,nextfreeTime,bookedTaxi.totalEarnings + earning,tripDetail);
        System.out.println("Taxi " + bookedTaxi.id + " booked");
    }
}
