package Assign2;

/*

NAME: DIVYANSH RASTOGI
ROLL NO: 2019464

TASK:
   Connect diners and restaurants facilitated by food delivery exec.

CLASSES & INTERFACE:
   . App
   . Cart
   . Order
   . Customer
        > Special Customer
        > Elite Customer
   . Restaurant Owner
   . Restaurant
        > Fast Food
        > Authentic
   . Diners
   . Food Item
   . User (Interface) implements
       . Restaurant Owner
       . Customer

MAJOR RELATIONSHIPS B/W CLASSES:
    . App
        Composes Diners
        Composes ResOwner
        Composes Customer
    . ResOwner
        Composes Restaurant
        Dependency with Diners
        Dependency with foodItem
    . Restaurant
        Associates with ResOwner
        Associates with foodItem
    . Diners
        Composes Restaurant
    . Customer
        Composes Order
        Associates with App
        Associates with Diners
    . Cart
        Composes Order
        Associates with Customer
        Associates with Restaurant
    . Order
        Dependency with Cart
*/

public class Main_Assign2 {
    public static void main(String[] args) {
        new App("ZOTATO");
        System.out.println("BYE!");
    }
}





